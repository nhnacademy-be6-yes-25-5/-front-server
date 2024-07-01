package com.nhnacademy.frontserver1.application.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.frontserver1.application.service.UploadImageService;
import com.nhnacademy.frontserver1.common.exception.ApplicationException;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import com.nhnacademy.frontserver1.presentation.dto.response.book.UploadImageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UploadImageServiceImpl implements UploadImageService {

    @Value("${nhn.appkey}")
    private String appKey;

    @Value("${nhn.secret}")
    private String secretKey;

    private final String imagePath = "/yes25-5-images";

    @Override
    public UploadImageResponse imageUpload(MultipartFile file) {

        try {
            String originalFilename = file.getOriginalFilename();
            String imagePathWithFileName = imagePath + "/" + originalFilename;

            String apiURL = "https://api-image.nhncloudservice.com/image/v2.0/appkeys/" + appKey + "/images?path=" + imagePathWithFileName + "&overwrite=true";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Authorization", secretKey);
            con.setRequestProperty("Content-Type", "application/octet-stream; charset=UTF-8");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                os.write(file.getBytes());
                os.flush();
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return parseUploadResponse(response.toString());
            } else {
                throw new ApplicationException(ErrorStatus.toErrorStatus("파일 업로드 API 호출 중 오류가 발생했습니다.", responseCode, LocalDateTime.now()));
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("파일 업로드 중 알수없는 오류가 발생했습니다.", 500, LocalDateTime.now()));
        }
    }

    private UploadImageResponse parseUploadResponse(String jsonResponse) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode fileNode = rootNode.path("file");

        String url = fileNode.path("url").asText();
        String name = fileNode.path("name").asText();
        String path = fileNode.path("path").asText();

        return UploadImageResponse.builder()
                .imageUrl(url)
                .name(name)
                .path(path)
                .build();
    }
}
