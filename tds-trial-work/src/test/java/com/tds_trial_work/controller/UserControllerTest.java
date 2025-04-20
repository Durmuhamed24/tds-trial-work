package com.tds_trial_work.controller;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tds_trial_work.dto.UserDto;
import com.tds_trial_work.service.UserService;

class UserControllerTest {
    
 private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getAllUsers_ShouldReturnPaginatedUsers() throws Exception {
        UserDto user1 = new UserDto(1L, "User1", "user1@test.com", true);
        UserDto user2 = new UserDto(2L, "User2", "user2@test.com", false);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<UserDto> page = new PageImpl<>(List.of(user1, user2), pageRequest, 2);
        
        when(userService.getAllUsers(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/users")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("User1"))
                .andExpect(jsonPath("$.content[1].email").value("user2@test.com"));
    }
    

    @Test
    void getUserById_WhenExists_ShouldReturnUser() throws Exception {
       
        UserDto user = new UserDto(1L, "Test User", "test@example.com", true);
        when(userService.getUserById(1L)).thenReturn(user);

       
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void getUserById_WhenNotExists_ShouldReturnNotFound() throws Exception {
      
        when(userService.getUserById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
       
        UserDto input = new UserDto(null, "New User", "new@example.com", true);
        UserDto output = new UserDto(1L, "New User", "new@example.com", true);
        
        when(userService.createUser(any(UserDto.class))).thenReturn(output);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New User"));
    }

    @Test
    void updateUser_WhenExists_ShouldReturnUpdatedUser() throws Exception {
   
        UserDto input = new UserDto(null, "Updated User", "updated@example.com", true);
        UserDto output = new UserDto(1L, "Updated User", "updated@example.com", true);
        
        when(userService.updateUser(eq(1L), any(UserDto.class))).thenReturn(output);

       
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    @Test
    void updateUser_WhenNotExists_ShouldReturnNotFound() throws Exception {
      
        UserDto input = new UserDto(null, "Non-existent", "none@example.com", true);
        when(userService.updateUser(eq(999L), any(UserDto.class))).thenReturn(null);

        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
       
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
        
        verify(userService, times(1)).deleteUser(1L);
    }
}