package com.tds_trial_work.service;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tds_trial_work.dto.DeviceDto;
import com.tds_trial_work.model.Device;
import com.tds_trial_work.model.User;
import com.tds_trial_work.repository.DeviceRepository;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void getAllDevices_ShouldReturnPageOfDevices() {
        Device device = new Device(1L, "device1", "type1", "os1", "meta1");
        Page<Device> page = new PageImpl<>(Collections.singletonList(device));
        
        when(deviceRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<DeviceDto> result = deviceService.getDevices(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("device1", result.getContent().get(0).getDeviceId());
        assertEquals("type1", result.getContent().get(0).getDeviceType());
        assertEquals("os1", result.getContent().get(0).getOS());
        assertEquals("meta1", result.getContent().get(0).getMetatag());
        verify(deviceRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getDeviceById_WhenExists_ShouldReturnDevice() {
       
        User user = new User(1L, "dur", "dur@email.com", "dur#123", true);
        Device device = new Device(1L, "device1", "type1", "os1", "meta1");
        device.setUser(user);
        
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        DeviceDto result = deviceService.getDeviceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("device1", result.getDeviceId());
        assertEquals(1L, result.getUserId()); 
        verify(deviceRepository, times(1)).findById(1L);
    }

    @Test
    void getDeviceById_WhenNotExists_ShouldReturnNull() {
        when(deviceRepository.findById(999L)).thenReturn(Optional.empty());

        DeviceDto result = deviceService.getDeviceById(999L);

        assertNull(result);
        verify(deviceRepository, times(1)).findById(999L);
    }

    @Test
    void createDevice_ShouldSaveAndReturnDeviceDto() {

        DeviceDto input = new DeviceDto(null, "newDevice", "type1", "os1", "meta1", null);
        Device savedDevice = new Device(1L, "newDevice", "type1", "os1", "meta1");
        
        when(deviceRepository.save(any(Device.class))).thenReturn(savedDevice);
        DeviceDto result = deviceService.createDevice(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("newDevice", result.getDeviceId());
        assertEquals("type1", result.getDeviceType());
        assertEquals("os1", result.getOS());
        assertEquals("meta1", result.getMetatag());
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void updateDevice_WhenExists_ShouldUpdateAndReturnDeviceDto() {
        Device existingDevice = new Device(1L, "oldDevice", "oldType", "oldOs", "oldMeta");
        DeviceDto updateDto = new DeviceDto(null, "newDevice", "newType", "newOs", "newMeta", null);
        Device updatedDevice = new Device(1L, "newDevice", "newType", "newOs", "newMeta");
        
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.save(any(Device.class))).thenReturn(updatedDevice);
        DeviceDto result = deviceService.updateDevice(1L, updateDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("newDevice", result.getDeviceId());
        assertEquals("newType", result.getDeviceType());
        assertEquals("newOs", result.getOS());
        assertEquals("newMeta", result.getMetatag());
        verify(deviceRepository, times(1)).findById(1L);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void updateDevice_WhenNotExists_ShouldReturnNull() {
        DeviceDto updateDto = new DeviceDto(null, "newDevice", "newType", "newOs", "newMeta", null);
        when(deviceRepository.findById(999L)).thenReturn(Optional.empty());
        DeviceDto result = deviceService.updateDevice(999L, updateDto);
        assertNull(result);
        verify(deviceRepository, times(1)).findById(999L);
        verify(deviceRepository, never()).save(any(Device.class));
    }

    @Test
    void deleteDevice_ShouldCallRepositoryDelete() {
        doNothing().when(deviceRepository).deleteById(1L);
        deviceService.deleteDevice(1L);
        verify(deviceRepository, times(1)).deleteById(1L);
    }
}