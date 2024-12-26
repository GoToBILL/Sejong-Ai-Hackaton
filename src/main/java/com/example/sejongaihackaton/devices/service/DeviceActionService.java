package com.example.sejongaihackaton.devices.service;

import com.example.sejongaihackaton.devices.dto.DeviceCommandRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class DeviceActionService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private final DeviceService deviceService;
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public String scheduleAction(String action, int minute, String deviceId) {
        cancelAction(deviceId);

        ScheduledFuture<?> future = scheduler.schedule(() -> {
            executeAction(action, deviceId);
        }, minute, TimeUnit.MINUTES);

        scheduledTasks.put(deviceId, future);

        String actionKor = action.equals("on") ? "켜기" : "끄기";
        return String.format("%d분 후에 디바이스(%s)를 %s로 설정했습니다.", minute, deviceId, actionKor);
    }

    public String cancelAction(String deviceId) {
        ScheduledFuture<?> future = scheduledTasks.remove(deviceId);

        if (future != null) {
            future.cancel(false);
            return String.format("디바이스(%s)의 예약된 동작을 취소했습니다.", deviceId);
        }

        return String.format("디바이스(%s)에 예약된 동작이 없습니다.", deviceId);
    }

    private void executeAction(String action, String deviceId) {
        try {
            DeviceCommandRequest commandRequest = new DeviceCommandRequest(deviceId, action);
            deviceService.sendCommandToDevice(commandRequest);

            if (action.equals("on")) {
                System.out.println(String.format("디바이스(%s)를 켰습니다.", deviceId));
            } else if (action.equals("off")) {
                System.out.println(String.format("디바이스(%s)를 껐습니다.", deviceId));
            }

        } catch (Exception e) {
            System.err.println("동작 실행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}