package com.is208n21.is208.Service;



import com.is208n21.is208.Entity.Model.User;
import com.is208n21.is208.Entity.month_user;
import com.is208n21.is208.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void removeUserByUserId(String userId) {
        User user = userRepository.findUserByUserId(userId);
        userRepository.delete(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserName(String username) {
        return userRepository.findByNameUser(username);
    }

    public User findUserByUserId(String userId) {
        return userRepository.findUserByUserId(userId);
    }

    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Integer countUser() {
        return Math.toIntExact(userRepository.count());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void setPassword(String password, String email) {
        userRepository.setPassword(password, email);
    }

    public List<month_user> getUserAndMonnth() {
        return userRepository.getUserAndMonnth();
    }

    User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findUser(String keyword) {
        return userRepository.findUser(keyword);
    }
}
