package com.tds_trial_work.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tds_trial_work.dto.DeviceDto;
import com.tds_trial_work.model.Device;
import com.tds_trial_work.repository.DeviceRepository;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public Page<DeviceDto> getDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable).map(this::convertToDTO);
    }

    private DeviceDto convertToDTO(Device device) {
        DeviceDto dto = new DeviceDto();
        dto.setId(device.getId());
        dto.setDeviceId(device.getDeviceId());
        dto.setDeviceType(device.getDeviceType());
        dto.setOS(device.getOS());
        dto.setMetatag(device.getMetatag());
        dto.setUserId(device.getUser() != null ? device.getUser().getId() : null);
        return dto;
    }
    public DeviceDto getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    public DeviceDto createDevice(DeviceDto deviceDto) {
        Device device = new Device();
        device.setDeviceId(deviceDto.getDeviceId());
        device.setDeviceType(deviceDto.getDeviceType());
        device.setOS(deviceDto.getOS());
        device.setMetatag(deviceDto.getMetatag());
        Device savedDevice = deviceRepository.save(device);
        return convertToDTO(savedDevice);
    }
    public DeviceDto updateDevice(Long id, DeviceDto deviceDto) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setDeviceId(deviceDto.getDeviceId());
                    device.setDeviceType(deviceDto.getDeviceType());
                    device.setOS(deviceDto.getOS());
                    device.setMetatag(deviceDto.getMetatag());
                    return convertToDTO(deviceRepository.save(device));
                })
                .orElse(null);
    }
    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}