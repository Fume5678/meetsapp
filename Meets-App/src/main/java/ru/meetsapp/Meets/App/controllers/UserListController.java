package ru.meetsapp.Meets.App.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meetsapp.Meets.App.entity.User;
import ru.meetsapp.Meets.App.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserListController {
    @Autowired
    UserService userService;


    @GetMapping("")
    public String usersPage(Model model){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = userService.getLastUsers(20);
        model.addAttribute("users", users);
        model.addAttribute("userDetails", userDetails);

        return "users";
    }
}
