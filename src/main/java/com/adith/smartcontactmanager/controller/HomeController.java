package com.adith.smartcontactmanager.controller;

import com.adith.smartcontactmanager.entities.User;
import com.adith.smartcontactmanager.helper.Message;
import com.adith.smartcontactmanager.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {


    HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home-First Boot Application");
        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "SignUp-First Boot Application");
        model.addAttribute("user", new User());
        return "signup";
    }



    //handler for registering user
    @PostMapping("/do_register")
    public String registerUser(@Valid  @ModelAttribute("user") User user,BindingResult bindingResult, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession httpSession ) {

        try {
            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                throw new Exception("You have not agreed the terms and conditions");
            }

            User existingUser=userRepository.findUserByEmail(user.getEmail());
            //<------------------------------------------------------------------------------------------
            if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
                bindingResult.rejectValue("email", null,
                        "There is already an account registered with the same email");

                httpSession.setAttribute("message",new Message("Email Already Exists","alert-danger"));
                model.addAttribute("user",user);
                return "signup";
            }


            //<----------------------------------------------------------------------------------------

            if(bindingResult.hasErrors()){
                System.out.println("error"+bindingResult.toString());
                model.addAttribute("user",user);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

                User result = userRepository.save(user);

            model.addAttribute("user",new User());

            httpSession.setAttribute("message",new Message("Successfully Registered","alert-success"));
            return "signup";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            httpSession.setAttribute("message",new Message("Something went Wrong"+e.getMessage(),"alert-danger"));

            return "signup";
        }


    }

    @GetMapping ("/login")
    public String loginPost() {

        return "signIn";
    }

//    @PostMapping("/logout")
//    public String performLogout() {
//
//        return "redirect:/login";
//    }
//
//    @GetMapping("/logout")
//    public String logout() {
//
//        return "redirect:/login?logout";
//    }
//





}
