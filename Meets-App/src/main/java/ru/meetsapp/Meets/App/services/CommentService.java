package ru.meetsapp.Meets.App.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.meetsapp.Meets.App.dto.CommentDTO;
import ru.meetsapp.Meets.App.entity.Comment;
import ru.meetsapp.Meets.App.entity.Meet;
import ru.meetsapp.Meets.App.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository repository;

    @Autowired
    public CommentService(CommentRepository repository){
        this.repository = repository;
    }

    public Comment addComment(CommentDTO dto){
        Comment newComment = new Comment();
        newComment.setMeet(dto.meet);
        newComment.setUserId(dto.userId);
        newComment.setUsername(dto.username);
        newComment.setMessage(dto.message);

        try {
            LOG.info("Saving comment in meet {}", newComment.getMeet().getTitle());
            return repository.save(newComment);
        } catch (Exception e){
            LOG.error("Error during creating comment: {}", e.getMessage());
            throw new RuntimeException("The comment cannot create");
        }
    }

    public List<Comment> getCommentsByMeet(Meet meet, int amount){
        List<Comment> comments = repository.findAllByMeetOrderByCreatedDate(meet);
        List<Comment> result = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            result.add(comments.get(i));
        }
        return result;
    }

    public void deleteComment(Long id){
        Optional<Comment> comment = repository.findCommentsById(id);
        comment.ifPresent(repository::delete);
    }

}
