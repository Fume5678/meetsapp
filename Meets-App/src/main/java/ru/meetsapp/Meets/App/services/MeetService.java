package ru.meetsapp.Meets.App.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.meetsapp.Meets.App.dto.MeetDTO;
import ru.meetsapp.Meets.App.entity.Meet;
import ru.meetsapp.Meets.App.entity.User;
import ru.meetsapp.Meets.App.repositories.MeetRepository;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeetService {
    public static final Logger LOG = LoggerFactory.getLogger(MeetService.class);
    private final MeetRepository repository;

    @Autowired
    public MeetService(MeetRepository repository){
        this.repository = repository;
    }

    public Meet createMeet(MeetDTO dto){
        Meet newMeet = new Meet();
        newMeet.setTitle(dto.title);
        StringBuilder fullTime = new StringBuilder();
        fullTime.append(dto.meetDate).append(" ").append(dto.meetTime).append(":00");
        LocalDateTime dateTime = LocalDateTime.parse(fullTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        newMeet.setMeetDateTime(dateTime);
        newMeet.getMeetUsers().add(dto.creator.getId());
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
            return null;
        }

        meet.get().getMeetUsers().add(user.getId());
        return repository.save(meet.get());
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
        List<Meet> meets = repository.findAllByMeetUsersContainingOrderByCreatedDateDesc(user.getId());
        List<Meet> result = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            result.add(meets.get(i));
        }

        return result;
    }

    public void deleteMeetById(Long id){
        Optional<Meet> meet = repository.findMeetById(id);
        meet.ifPresent(repository::delete);
    }
}
