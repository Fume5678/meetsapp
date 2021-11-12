package ru.meetsapp.Meets.App.services;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.meetsapp.Meets.App.dto.MeetDTO;
import ru.meetsapp.Meets.App.entity.Meet;
import ru.meetsapp.Meets.App.entity.User;
import ru.meetsapp.Meets.App.repositories.MeetRepository;
import ru.meetsapp.Meets.App.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeetService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final MeetRepository repository;

    @Autowired
    public MeetService(MeetRepository repository){
        this.repository = repository;
    }

    public Meet createMeet(MeetDTO dto){
        Meet newMeet = new Meet();
        newMeet.setTitle(dto.title);
        newMeet.setMeetDate(LocalDateTime.parse(dto.meetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        newMeet.setMeetTime(LocalDateTime.parse(dto.meetTime, DateTimeFormatter.ofPattern("HH:mm")));
        newMeet.getMeetUser().add(dto.creator.getId());
        newMeet.setCreator(dto.creator);
        newMeet.setLocation(dto.location);
        newMeet.setOpen(dto.open  ? 1 : 0);

        try {
            LOG.info("Saving meet {}", dto.title);
            return repository.save(newMeet);
        } catch (Exception e){
            LOG.error("Error during creating meet {}", e.getMessage());
            throw new RuntimeException("The meet cannot create");
        }
    }

    public Meet addUserToMeet(Long id, User user){

        Optional<Meet> meet = repository.findMeetById(id);

        if(meet.isEmpty()){
            LOG.error("Not found meet");
            throw new RuntimeException("User couldn't add");
        }

        meet.get().getMeetUser().add(user.getId());
        return meet.get();
    }

    public List<Meet> getMeetListByCreator(User creator, int amount){
        List<Meet> meets = repository.findAllByCreatorOrderByCreatedDateDesc(creator);
        List<Meet> result = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            result.add(meets.get(i));
        }

        return result;
    }

    public List<Meet> getMeetListByUser(User user, int amount){
        List<Meet> meets = repository.findAllByMeetUserContainingOrderByCreatedDateDesc(user.getId());
        List<Meet> result = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            result.add(meets.get(i));
        }

        return result;
    }

    public void deleteMeet(Long id){
        Optional<Meet> meet = repository.findMeetById(id);
        meet.ifPresent(repository::delete);
    }
}
