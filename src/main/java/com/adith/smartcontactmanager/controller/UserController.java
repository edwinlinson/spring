package com.adith.smartcontactmanager.controller;

import com.adith.smartcontactmanager.entities.User;
import com.adith.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/dashboard")
    public String  userDashboard(Model model, Principal principal){
        String name = principal.getName();
        System.out.println(name);
        //get the user using username from repository
        User user= userRepository.findUserByEmail(name);
        System.out.println("User"+user);
        model.addAttribute("user",user);



        return"user/userDashboard";
    }

}
