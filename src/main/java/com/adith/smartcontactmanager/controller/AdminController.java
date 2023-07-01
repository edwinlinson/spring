package com.adith.smartcontactmanager.controller;

import com.adith.smartcontactmanager.entities.User;
import com.adith.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    @GetMapping("/dashboard")
//    public List<User> adminPanel(){
//        List<User> list= userRepository.findAll();
//        return list;
//    }

    //-------------------------------Displaying all Users Read---------------------------------------->
    @GetMapping("/dashboard")
    public String adminPanel(Model model, Principal principal){

        String username= principal.getName();
        User admin=userRepository.findUserByEmail(username);
        model.addAttribute("admin",admin);
        List<User>users=userRepository.findAll();
        model.addAttribute("users",users);
        return "admin/adminPanel";
    }

    //-------------------------------creating a user Create---------------------------------------->

    @GetMapping("/addUser")
    public String adduserGET(Model model, Principal principal){
        String username=principal.getName();
        User admin=userRepository.findUserByEmail(username);
        model.addAttribute("admin",admin);
        model.addAttribute("user",new User());

        return "admin/addUser";
    }

    @PostMapping("/adduserPOST")
    public String adduserPOST(@ModelAttribute("user") User user,Model model){

        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User save = userRepository.save(user);
        System.out.println(user);
        model.addAttribute("admin",new User());
        model.addAttribute("user",new User());

        return "admin/addUser";
    }

    //-------------------------------Updating a user Update---------------------------------------->

    @GetMapping("/updateUser")
    public String updateUser(Model model){
        model.addAttribute("admin",new User());  //this is just to avoid error displaying admin details

        model.addAttribute("user",new User());
        return "admin/updateUser";
    }

    @PostMapping("/updateUserPUT")
    public String updateUserPUT(@ModelAttribute("user")User user,Model model){

        User exUser=userRepository.findUserByEmail( user.getEmail());
        user.setId(exUser.getId());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(userRepository.save(user));

        model.addAttribute("admin",new User());
        model.addAttribute("user",new User());

        return "admin/updateUser";
    }

/*    <--------------------------------------------------------------------------->*/


    @GetMapping("/updateUser/{id}")
    public String updateUserGET(Model model){
        model.addAttribute("admin",new User());  //this is just to avoid error displaying admin details

        model.addAttribute("user",new User());
        return "admin/updateUser";
    }



    @PostMapping("/updateUser{id}")
    public String updateUser(@ModelAttribute("user")User user,@PathVariable("id")Integer id,Model model){


        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(userRepository.save(user));

        model.addAttribute("admin",new User());
        model.addAttribute("user",new User());

        return "admin/updateUser";
    }



    //------------------------------ delete user   Delete---------------------------------------->

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id")Integer id,Model model){

        User user=userRepository.findUserById(id);
        System.out.println(user);
        userRepository.delete(user);

        model.addAttribute( "admin",new User());
        model.addAttribute("user",new User());

        return "redirect:/admin/dashboard";
    }



}
