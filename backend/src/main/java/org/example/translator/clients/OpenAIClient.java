package org.example.translator.clients;

import org.example.translator.helpers.MultipartInputStreamFileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.translator.helpers.Endpoints.WHISPER_POST_ENDPOINT;
import static org.example.translator.helpers.StringConstants.*;

@PropertySource(APPLICATION_PROPERTIES_PATH)
@Component
public class OpenAIClient {

    private final RestTemplate restTemplate;

    @Value(WHISPER_KEY)
    private String whisperKey;

    @Autowired
    public OpenAIClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // sendToWhisper creates the request body and header and gets the response back as a String
    public String sendToWhisper(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(whisperKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Wrap the file in its own HttpEntity with content type
        HttpHeaders filePartHeaders = new HttpHeaders();
        filePartHeaders.setContentType(MediaType.parseMediaType(file.getContentType())); // e.g., audio/mp4

        HttpEntity<MultipartInputStreamFileResource> filePart =
                new HttpEntity<>(new MultipartInputStreamFileResource(
                        file.getInputStream(),
                        file.getOriginalFilename(),
                        file.getContentType()
                ), filePartHeaders);

        body.add("file", filePart);
        body.add("model", AI_MODEL);
        body.add("language", LANG_PARAM);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    WHISPER_POST_ENDPOINT,
                    requestEntity,
                    String.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            throw new IOException(WHISPER_CALL_FAIL_ERROR, e);
        }
    }

}
