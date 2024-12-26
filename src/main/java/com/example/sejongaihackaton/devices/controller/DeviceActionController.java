package com.example.sejongaihackaton.devices.controller;

import com.example.sejongaihackaton.devices.dto.CancelRequestDto;
import com.example.sejongaihackaton.devices.dto.ScheduleRequestDto;
import com.example.sejongaihackaton.devices.service.DeviceActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceActionController {

    private final DeviceActionService deviceActionService;

    /**
     * Schedule a device action
     */
    @PostMapping("/schedule-action")
    public String scheduleAction(@RequestBody ScheduleRequestDto request) {
        return deviceActionService.scheduleAction(request.getAction(), request.getMinute(), request.getDeviceId());
    }

    /**
     * Cancel a scheduled action
     */
    @PostMapping("/cancel-action")
    public String cancelAction(@RequestBody CancelRequestDto request) {
        return deviceActionService.cancelAction(request.getDeviceId());
    }
}