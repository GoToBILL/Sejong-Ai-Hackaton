package com.example.sejongaihackaton;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SyncConfig {
    private static String apiKey;

    @Value("${smartsync.api.key}")
    public void setApiKey(String key) {
        apiKey = key;
    }

}
