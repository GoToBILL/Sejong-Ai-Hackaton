package com.example.sejongaihackaton.devices.service;

import com.example.sejongaihackaton.common.config.SyncConfig;
import com.example.sejongaihackaton.devices.dto.DeviceCommandRequest;
import com.example.sejongaihackaton.devices.dto.DeviceResponseDto;
import com.example.sejongaihackaton.devices.entity.Device;
import com.example.sejongaihackaton.devices.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final RestTemplate restTemplate;
    private final SyncConfig syncConfig;

    private final String SMARTTHINGS_API_URL = "https://api.smartthings.com/v1/devices/";

    public List<DeviceResponseDto> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(device -> new DeviceResponseDto(
                        device.getId(),
                        device.getLabel(),
                        device.getDeviceId(),
                        device.getName(),
                        device.getStatus(),
                        device.getLabelType()
                ))
                .collect(Collectors.toList());
    }

    public DeviceResponseDto getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 디바이스를 찾을 수 없습니다: " + id));

        return new DeviceResponseDto(
                device.getId(),
                device.getLabel(),
                device.getDeviceId(),
                device.getName(),
                device.getStatus(),
                device.getLabelType()
        );
    }

    @Transactional
    public String sendCommandToDevice(DeviceCommandRequest commandRequest) {
        String apiUrl = SMARTTHINGS_API_URL + commandRequest.getDeviceId() + "/commands";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + syncConfig.getApiKey());

        Map<String, Object> commandMap = new HashMap<>();
        commandMap.put("component", "main");
        commandMap.put("capability", "switch");
        commandMap.put("command", commandRequest.getCommand());

        Map<String, Object> body = Collections.singletonMap("commands", Collections.singletonList(commandMap));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            updateDeviceStatus(commandRequest.getDeviceId(), commandRequest.getCommand());
            return "명령 전송 성공";
        } else {
            throw new RuntimeException("명령 전송 실패: " + response.getBody());
        }
    }

    // 상태 업데이트 로직 추가
    private void updateDeviceStatus(String deviceId, String command) {
        // 디바이스를 데이터베이스에서 검색
        Device device = deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new NoSuchElementException("해당 deviceId를 가진 디바이스를 찾을 수 없습니다: " + deviceId));

        // 상태 업데이트
        device.setStatus(command.equalsIgnoreCase("on") ? "on" : "off");

        // 변경 사항 저장
        deviceRepository.save(device);
    }
}