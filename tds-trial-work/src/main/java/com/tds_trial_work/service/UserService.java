package com.tds_trial_work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tds_trial_work.dto.UserDto;
import com.tds_trial_work.model.User;
import com.tds_trial_work.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::convertToDTO);
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    private UserDto convertToDTO(User user) {
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getActive()
        );
    }
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setActive(userDto.getActive());
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    public UserDto updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDto.getName());
                    user.setEmail(userDto.getEmail());
                    user.setActive(userDto.getActive());
                    return convertToDTO(userRepository.save(user));
                })
                .orElse(null);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
}