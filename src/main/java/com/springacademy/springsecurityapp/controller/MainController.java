package com.springacademy.springsecurityapp.controller;

import com.springacademy.springsecurityapp.model.AppUser;
import com.springacademy.springsecurityapp.repo.VerificationTokenRepo;
import com.springacademy.springsecurityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private UserService userService;
    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    public MainController(UserService userService, VerificationTokenRepo verificationTokenRepo) {
        this.userService = userService;
        this.verificationTokenRepo = verificationTokenRepo;
    }

    @RequestMapping("/")
    public String start(){
        return "welcome";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/failure")
    public String failure(){
        return "failure";
    }

    @RequestMapping("/oauth_login")
    public String oauthLogin(){
        return "/oauth_login";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "success";
    }

    @RequestMapping("/singup")
    public ModelAndView singup(){
        return new ModelAndView("registration", "user", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView register(@Validated AppUser appUser, BindingResult bindingResult, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return new ModelAndView("redirect:/singup");
        }
        userService.addNewUser(appUser, request);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-token")
    public ModelAndView verify(@RequestParam String token){
        userService.verifyTokenUser(token);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-token-admin")
    public ModelAndView verifyAdmin(@RequestParam String token){
        userService.verifyTokenAdmin(token);
        return new ModelAndView("redirect:/login");
    }
}
