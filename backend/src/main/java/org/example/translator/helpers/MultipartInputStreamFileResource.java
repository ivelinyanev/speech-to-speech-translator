package org.example.translator.helpers;

import lombok.Getter;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;

@Getter
public class MultipartInputStreamFileResource extends InputStreamResource {

    private final String fileName;
    private final String contentType;

    public MultipartInputStreamFileResource(InputStream inputStream, String fileName, String contentType) {
        super(inputStream);
        this.fileName = fileName;
        this.contentType = contentType;
    }

    @Override
    public String getFilename() {
        return this.fileName;
    }

    @Override
    public long contentLength() throws IOException {
        return -1;
    }
}
