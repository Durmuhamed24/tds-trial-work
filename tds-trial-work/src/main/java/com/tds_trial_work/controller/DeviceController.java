package com.tds_trial_work.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tds_trial_work.dto.DeviceDto;
import com.tds_trial_work.service.DeviceService;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ResponseEntity<Page<DeviceDto>> getAllDevices(Pageable pageable) {
        return ResponseEntity.ok(deviceService.getDevices(pageable));
    }
}