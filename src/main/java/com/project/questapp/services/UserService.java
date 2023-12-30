package com.project.questapp.services;
import java.util.List;
import java.util.Optional;

import com.project.questapp.entities.User;
import com.project.questapp.repos.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    //bussineslogiclerin tutulduğu servis katmanı. Servisler Repositorye bağlanacak
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //get All Users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    //create new user
    public User createNewUSer(User newUser) {
        return userRepository.save(newUser);
    }


    //find user by id
/*	public User getOneUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}*/
    public User getOneUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }


    //update user by id
    public User updateUserById(Long userId,User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }


    //delete user by id
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);

    }


}

