package com.spring.springwekabackend.service;

import com.spring.springwekabackend.entity.User;
import com.spring.springwekabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getOneUser(String email) {

        List<User> listUser = userRepository.findByEmail(email);
        if (listUser.size() == 0) {
            return null;
        }
        return listUser.get(0);
    }

    public String createNewUserService(String email, String password) {
        String message = "New account created successfully";
        List<User> currentUser = userRepository.findByEmail(email);
        if (currentUser.size() != 0) {
            message = "Sorry this email has already been taken, try need one instead";
            return message;
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRoles("USER");
        newUser.setStatus("ACTIVE");
        newUser.setCreatedAt(new Date());
        newUser.setRecentLoginTime(null);

        userRepository.save(newUser);
        return message;
    }

    public String accountLoginService(String email, String password) {
        System.out.println(email);
        System.out.println(password);

        String message = "Login successfully";
        List<User> currentUser = userRepository.findByEmail(email);

        if (currentUser.size() == 0) {
            message = "Sorry this email, doesn't exist in our Database. Signup instead";
            return message;
        }

        if (!password.trim().equals(currentUser.get(0).getPassword())) {
            message = "Wrong user name or password, please re-enter";
            return message;
        }

        User userDB = currentUser.get(0);
        userDB.setRecentLoginTime(new Date());
        userRepository.save(userDB);
        return message;
    }

    public String recoverService(String email, String newPW, String oldPW) {
        String message = "Recover password successfully";
        List<User> userList = userRepository.findByEmail(email);

        if (userList.size() == 0) {
            message = "Sorry this email, doesn't exist in our Database. Signup instead";
            return message;
        }

        User currentUser = userList.get(0);

        if (!oldPW.equals(currentUser.getPassword())) {
            message = "Wrong password please re-enter password";
            return message;
        }

        currentUser.setPassword(newPW);
        userRepository.save(currentUser);
        return message;
    }
}
