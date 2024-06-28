package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.response.book.UploadImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {
    UploadImageResponse imageUpload(MultipartFile file);
}
