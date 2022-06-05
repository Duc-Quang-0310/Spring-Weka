package com.spring.springwekabackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.springwekabackend.entity.UserLoginResponse;
import com.spring.springwekabackend.repository.WekaTrainer.Trainer;
import com.spring.springwekabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController("")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/users/new-user")
    public String createNewUser(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObject = null;
        try {
            actualObject = mapper.readTree(body);
        } catch (IOException exception) {
            response.setStatus(500);
            exception.printStackTrace();
        }

        String email = actualObject.get("email").asText();
        String password = actualObject.get("password").asText();
        String message = service.createNewUserService(email, password);

        if (message.contains("successfully")) {
            response.setStatus(200);
            return message;
        }

        response.setStatus(400);
        return message;
    }

    @PostMapping("/users/login")
    public UserLoginResponse login(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObject = null;
        try {
            actualObject = mapper.readTree(body);
        } catch (IOException exception) {
            response.setStatus(500);
            exception.printStackTrace();
        }

        String email = actualObject.get("email").asText();
        String password = actualObject.get("password").asText();

        String message = service.accountLoginService(email, password);

        if (message.contains("successfully")) {
            response.setStatus(200);
            UserLoginResponse myRes = new UserLoginResponse(service.getOneUser(email), true, message);
            return myRes;
        }
        response.setStatus(400);
        return new UserLoginResponse(null, false, message);
    }

    @PostMapping("/users/password-recover")
    public String passwordRecover(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObject = null;
        try {
            actualObject = mapper.readTree(body);
        } catch (IOException exception) {
            response.setStatus(500);
            exception.printStackTrace();
        }

        String email = actualObject.get("email").asText();
        String oldPassword = actualObject.get("oldPassword").asText();
        String newPassword = actualObject.get("newPassword").asText();
        String message = service.recoverService(email, newPassword, oldPassword);

        if (message.contains("successfully")) {
            response.setStatus(200);
            return message;
        }
        response.setStatus(400);
        return message;
    }

    @PostMapping("/users/diabettes-diagnose")
    public String diagnose(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObject = null;
        try {
            actualObject = mapper.readTree(body);
        } catch (IOException exception) {
            response.setStatus(500);
            return "Something went wrong please try again";
        }

        String pregnant = actualObject.get("Preg").asText();
        String plasma = actualObject.get("Plas").asText();
        String pres = actualObject.get("Pres").asText();
        String skin = actualObject.get("Skin").asText();
        String insulin = actualObject.get("Insu").asText();
        String mass = actualObject.get("Mass").asText();
        String pedigree = actualObject.get("Pedi").asText();
        String age = actualObject.get("Age").asText();

        String concatDiagnose = pregnant + "," + plasma + "," + pres + "," + skin + "," + insulin + "," + mass + "," + pedigree + "," + age + ",?";
        Trainer trainer = new Trainer();
        return trainer.trainDataSet(concatDiagnose);
    }


//    @GetMapping("/users/{id}")
//    public Optional<User> getOneById(@PathVariable Integer id){
//        return service.getOneById(id);
//    }

}
