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
import com.tds_trial_work.dto.DeviceDto;
import com.tds_trial_work.service.DeviceService;

class DeviceControllerTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;
    @BeforeEach
    void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(deviceController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
}

@Test
void getAllDevices_ShouldReturnPaginatedDevices() throws Exception {
   
    DeviceDto device1 = new DeviceDto(131L, "id131", "apple", "ios", "aa", 1L);
    DeviceDto device2 = new DeviceDto(132L, "id132", "samsung", "android", "bb", 2L);
    Page<DeviceDto> page = new PageImpl<>(List.of(device1, device2), PageRequest.of(0, 10), 2);
    
    when(deviceService.getDevices(any(Pageable.class))).thenReturn(page);

    mockMvc.perform(get("/api/devices")
            .param("page", "0")  
            .param("size", "10") 
            .param("sort", "id,asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.length()").value(2))
            .andExpect(jsonPath("$.content[0].deviceId").value("id131"))
            .andExpect(jsonPath("$.content[1].deviceId").value("id132"));
}



    @Test
    void getDeviceById_WhenExists_ShouldReturnDevice() throws Exception {
        DeviceDto device = new DeviceDto(1L, "id1", "type1", "os1", "meta1", 1L);
        when(deviceService.getDeviceById(1L)).thenReturn(device);

        mockMvc.perform(get("/api/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deviceId").value("id1"));
    }

    @Test
    void getDeviceById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        when(deviceService.getDeviceById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/devices/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDevice_ShouldReturnCreatedDevice() throws Exception {
        DeviceDto input = new DeviceDto(null, "newId", "newType", "newOS", "newMeta", 1L);
        DeviceDto output = new DeviceDto(1L, "newId", "newType", "newOS", "newMeta", 1L);
        
        when(deviceService.createDevice(any(DeviceDto.class))).thenReturn(output);

        mockMvc.perform(post("/api/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deviceId").value("newId"));
    }

    @Test
    void updateDevice_WhenExists_ShouldReturnUpdatedDevice() throws Exception {
        DeviceDto input = new DeviceDto(null, "updatedId", "updatedType", "updatedOS", "updatedMeta", 1L);
        DeviceDto output = new DeviceDto(1L, "updatedId", "updatedType", "updatedOS", "updatedMeta", 1L);
        
        when(deviceService.updateDevice(eq(1L), any(DeviceDto.class))).thenReturn(output);

        mockMvc.perform(put("/api/devices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deviceId").value("updatedId"));
    }

    @Test
    void updateDevice_WhenNotExists_ShouldReturnNotFound() throws Exception {
        DeviceDto input = new DeviceDto(null, "noneId", "noneType", "noneOS", "noneMeta", 1L);
        when(deviceService.updateDevice(eq(999L), any(DeviceDto.class))).thenReturn(null);

        mockMvc.perform(put("/api/devices/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteDevice_ShouldReturnNoContent() throws Exception {
        doNothing().when(deviceService).deleteDevice(1L);

        mockMvc.perform(delete("/api/devices/1"))
                .andExpect(status().isNoContent());
        
        verify(deviceService, times(1)).deleteDevice(1L);
    }
}