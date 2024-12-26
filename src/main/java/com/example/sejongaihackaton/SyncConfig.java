package com.example.sejongaihackaton;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SyncConfig {

    @Value("${smartsync.api.key}")
    private String apiKey; // static 제거
}
