package ru.meetsapp.Meets.App;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.meetsapp.Meets.App.dto.BioDTO;
import ru.meetsapp.Meets.App.dto.UserDTO;
import ru.meetsapp.Meets.App.entity.User;
import ru.meetsapp.Meets.App.services.UserService;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest{
	@Autowired
	UserService userService;

	String wordGenerate(int size){
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			int randomLimitedInt = leftLimit + (int)
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}

		return buffer.toString();
	}

	@Test
	void createUser(){

		UserDTO userDTO = new UserDTO();
		userDTO.username = wordGenerate(10);
		userDTO.name = "Vasya";
		userDTO.email = "nselyavin@inbox.ru";
		userDTO.lastname = "Grishin";
		userDTO.password = wordGenerate(10);

		assertDoesNotThrow(() -> {
			User user = userService.createUser(userDTO);
			assertEquals(user.getUsername(), userService.getUserById(user.getId()).getUsername());
		});
		// Second user with same username
		assertThrows(RuntimeException.class, ()->{
			User user = userService.createUser(userDTO);
		});
	}

	@Test
	void createUserWithBadMail(){

		UserDTO userDTO = new UserDTO();
		userDTO.username = wordGenerate(10);
		userDTO.name = "Vasya";
		userDTO.email = "badmail";
		userDTO.lastname = "Grishin";
		userDTO.password = wordGenerate(10);

		// Second user with same username
		assertThrows(RuntimeException.class, ()->{
			User user = userService.createUser(userDTO);
		});
	}

	@Test
	void bookmarkTest(){
		UserDTO userDTO = new UserDTO();
		userDTO.username = wordGenerate(10);
		userDTO.name = "Vasya";
		userDTO.email = "nselyavin@inbox.ru";
		userDTO.lastname = "Grishin";
		userDTO.password = wordGenerate(10);
		User bookmark = userService.createUser(userDTO);

		assertDoesNotThrow(() -> {
			User user = userService.getUserByUsername("Vasya228");
			userService.bookmarkUser(user.getId(), bookmark.getId());
			Set<Long> bookmarks = userService.getBookmarksIdById(user.getId());
			assertTrue(bookmarks.contains(bookmark.getId()));
		});
	}

	@Test
	void likeTest(){
		UserDTO userDTO = new UserDTO();
		userDTO.username = wordGenerate(10);
		userDTO.name = "Vasya";
		userDTO.email = "nselyavin@inbox.ru";
		userDTO.lastname = "Grishin";
		userDTO.password = wordGenerate(10);
		User likeUser = userService.createUser(userDTO);

		assertDoesNotThrow(() -> {
			User user = userService.getUserByUsername("Vasya228");
			userService.likeUser(user.getId(), likeUser.getId());
			Set<Long> bookmarks = userService.getLikedUsersIdById(user.getId());
			assertTrue(bookmarks.contains(likeUser.getId()));
		});
	}

	@Test
	void bioUpdateTest(){
		UserDTO userDTO = new UserDTO();
		userDTO.username = wordGenerate(10);
		userDTO.name = "Vasya";
		userDTO.email = "nselyavin@inbox.ru";
		userDTO.lastname = "Grishin";
		userDTO.password = wordGenerate(10);

		assertDoesNotThrow(() -> {
			User user = userService.createUser(userDTO);
			BioDTO bioDTO = new BioDTO();
			bioDTO.biography = "Poz";
			bioDTO.gender = "male";
			bioDTO.hairColor = "green";
			bioDTO.height = 172.0f;
			bioDTO.weight = 80.0f;
			bioDTO.specialSigns = "Monstr";
			user = userService.updateBio(user.getId(), bioDTO);
			assertEquals(user.getBio().getGender(), "male");
			assertEquals(user.getBio().getHeight(), bioDTO.height);
		});
	}
}

@SpringBootTest
class MeetServiceTest{

}
