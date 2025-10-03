package org.example.translator.config;

import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource({"classpath:application.properties"})
@Configuration
public class OpenAIConfig {
    @Value("${open.ai.key}")
    private String whisperKey;

    @Generated
    public String getWhisperKey() {
        return this.whisperKey;
    }
}
