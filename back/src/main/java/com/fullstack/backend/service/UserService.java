package com.fullstack.backend.service;

import com.fullstack.backend.dao.UserRepository;
import com.fullstack.backend.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Inyección por constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public User create(User user) {
        // Más adelante aquí podremos validar username/email, etc.
        return userRepository.save(user);
    }

    public User update(Long id, User userData) {
        User existing = findById(id);
        existing.setUsername(userData.getUsername());
        existing.setEmail(userData.getEmail());
        existing.setPassword(userData.getPassword());
        return userRepository.save(existing);
    }

    public void delete(Long id) {
        User existing = findById(id);
        userRepository.delete(existing);
    }
}
