package com.tds_trial_work.service;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tds_trial_work.dto.UserDto;
import com.tds_trial_work.model.User;
import com.tds_trial_work.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_ShouldReturnPageOfUsers() {
        User user = new User(1L, "dur", "dur@gmail.com", "password#123", true);
        Page<User> page = new PageImpl<>(List.of(user));
        
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<UserDto> result = userService.getAllUsers(PageRequest.of(0, 10));
        
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void createUser_ShouldSaveAndReturnUserDTO() {
        UserDto input = new UserDto(null, "qool", "qbool@gmail.com", true);
        User savedUser = new User(1L, "nade", "nade@gmail.com", "password#1234", true);
        
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.createUser(input);
        
        assertEquals(1L, result.getId());
        assertEquals("nade", result.getName());
    }
}