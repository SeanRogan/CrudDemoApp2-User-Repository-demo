package com.seanrogandev.userdemo.controllers;
import com.seanrogandev.userdemo.entities.User;
import com.seanrogandev.userdemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;

    //get all users in DB
    @GetMapping({"/showAllUsers", "/list","/"})
    public ModelAndView getAllUsers() {
        ModelAndView mav = new ModelAndView("list-users");
        List<User> userList = userRepo.findAll();
        mav.addObject("user_list", userList);
        return mav;
    }
    //get the registration form view for a new user
    @GetMapping("/register")
    public ModelAndView registerUserView(){
        ModelAndView mav = new ModelAndView("registration-form");
        mav.addObject("user", new User());
        return mav;
    }
    //post the input info to the DB as a new user.
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userRepo.save(user);
        return "redirect:/list";
    }

    @GetMapping("/editProfile")
    public ModelAndView editProfileView(@RequestParam Long userId){
        ModelAndView mav = new ModelAndView("registration-form");
        if(userRepo.existsById(userId)) {
            mav.addObject(userRepo.findById(userId).get());
        }
        return mav;
    }

    @PostMapping("/editProfile")
    public String editProfile(@ModelAttribute User user) {
        userRepo.save(user);
        return "redirect:/list";
    }

    @GetMapping("/deleteProfile")
    public String deleteProfile(@RequestParam Long userId) {
        userRepo.deleteById(userId);
        return "redirect:/list";
    }
}
