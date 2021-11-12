package ru.meetsapp.Meets.App.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meetsapp.Meets.App.dto.BioDTO;
import ru.meetsapp.Meets.App.dto.UserDTO;
import ru.meetsapp.Meets.App.entity.Bio;
import ru.meetsapp.Meets.App.entity.User;
import ru.meetsapp.Meets.App.entity.enums.ERole;
import ru.meetsapp.Meets.App.repositories.BioRepository;
import ru.meetsapp.Meets.App.repositories.UserRepository;

import java.util.*;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final BioRepository bioRepository;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder encoder, BioRepository bioRepository){
        this.repository = repository;
        this.encoder = encoder;
        this.bioRepository = bioRepository;
    }

    public User createUser(UserDTO dto){
        User newUser = new User();
        newUser.setUsername(dto.username);
        newUser.setName(dto.name);
        newUser.setLastname(dto.lastname);
        newUser.setEmail(dto.email);
        newUser.setPassword(encoder.encode(dto.password));
        newUser.setRole(ERole.ROLE_USER);

        try {
            LOG.info("Saving user {}", dto.username);
            return repository.save(newUser);
        } catch (Exception e){
            LOG.error("Error during creating user {}", e.getMessage());
            throw new RuntimeException("Failed create user");
        }
    }

    public User getUserById(Long id){
        Optional<User> user = repository.findUserById(id);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", id);
            throw new RuntimeException("User not found");
        }

        return user.get();
    }

    public User getUserByUsername(String username){
        Optional<User> user = repository.findUserByUsername(username);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", username);
            throw new RuntimeException("User not found");
        }

        return user.get();
    }

//    public List<User> getFriendsById(Long id){
//        Optional<User> user = repository.findUserById(id);
//        if(user.isEmpty()){
//            LOG.error("Error not found user, id {}", id);
//            throw new RuntimeException("User not found");
//        }
//        List<User> friends = new ArrayList<>();
//
//        for(Long i : user.get().getFriends()){
//            Optional<User> friend = repository.findUserById(i);
//            friend.ifPresent(friends::add);
//        }
//
//        return friends;
//    }

    public Set<Long> getBookmarksIdById(Long id){
        Optional<User> user = repository.findUserById(id);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", id);
            throw new RuntimeException("User not found");
        }

        return user.get().getBookmarkUsers();
    }

    public Set<User> getBookmarksUsersById(Long id){
        Optional<User> user = repository.findUserById(id);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", id);
            throw new RuntimeException("User not found");
        }
        Set<User> bookmarks = new HashSet<>();

        for(Long i : user.get().getBookmarkUsers()){
            Optional<User> bookmark = repository.findUserById(i);
            bookmark.ifPresent(bookmarks::add);
        }

        return bookmarks;
    }

    public Set<Long> getLikedUsersIdById(Long id) {
        Optional<User> user = repository.findUserById(id);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", id);
            throw new RuntimeException("User not found");
        }
        return user.get().getLikedUsers();
    }

    public Set<User> getLikedUsersById(Long id) {
        Optional<User> user = repository.findUserById(id);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", id);
            throw new RuntimeException("User not found");
        }
        Set<User> likedUsers = new HashSet<>();

        for(Long i : user.get().getBookmarkUsers()){
            Optional<User> bookmark = repository.findUserById(i);
            bookmark.ifPresent(likedUsers::add);
        }

        return likedUsers;
    }

    public User bookmarkUser(Long id, Long bookmarkId){
        Optional<User> user = repository.findUserById(id);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", id);
            throw new RuntimeException("User not found");
        }

        Set<Long>  bookmarkUsers = user.get().getBookmarkUsers();
        if(!bookmarkUsers.contains(bookmarkId)){
            bookmarkUsers.add(bookmarkId);
        }else{
            bookmarkUsers.remove(bookmarkId);
        }

        try {
            return repository.save(user.get());
        } catch (Exception e){
            LOG.error("Failed to bookmark user with {} id", bookmarkId);
            throw new RuntimeException("Failed to bookmark user");
        }
    }

    public User likeUser(Long id, Long likeId){
        Optional<User> user = repository.findUserById(id);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", id);
            throw new RuntimeException("User not found");
        }

        Set<Long>  likedUsers = user.get().getLikedUsers();
        if(!likedUsers.contains(likeId)){
            likedUsers.add(likeId);
        }else{
            likedUsers.remove(likeId);
        }

        try {
            return repository.save(user.get());
        } catch (Exception e){
            LOG.error("Failed to like user with {} id", likeId);
            throw new RuntimeException("Failed to like user");
        }
    }

    public User updateBio(Long id, BioDTO bioDto){
        Optional<User> user = repository.findUserById(id);
        if(user.isEmpty()){
            LOG.error("Error not found user, id {}", id);
            throw new RuntimeException("User not found");
        }

        Bio newBio = new Bio();
        newBio.setBiography(bioDto.biography);
        newBio.setGender(bioDto.gender);
        newBio.setHairColor(bioDto.hairColor);
        newBio.setWeight(bioDto.weight);
        newBio.setHeight(bioDto.height);
        newBio.setJob(bioDto.job);
        newBio.setSpecialSigns(bioDto.specialSigns);
        newBio.setUser(user.get());

        user.get().setBio(newBio);

        try {
            return repository.save(user.get());
        } catch (Exception e){
            LOG.error("Failed to update bio of user {} id", id);
            throw new RuntimeException("Failed to update bio");
        }
    }


}
