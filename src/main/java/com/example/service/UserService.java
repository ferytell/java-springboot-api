package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User updateUser(Long id, User userDetails) {
    return userRepository.findById(id)
        .map(user -> {
          user.setName(userDetails.getName());
          user.setEmail(userDetails.getEmail());
          user.setPassword(userDetails.getPassword());
          return userRepository.save(user);
        })
        .orElseThrow(() -> new RuntimeException("User not found with id " + id));
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
