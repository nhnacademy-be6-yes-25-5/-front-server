package com.nhnacademy.springexam.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.springexam.JsonObject;
import org.apache.tomcat.util.json.ParseException;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonDepartmentParser implements DepartmentParser{
    private ObjectMapper objectMapper;

    public JsonDepartmentParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String getFileType() {
        return "json";
    }

    @Override
    public List parsing(File file) throws IOException, ParseException {
        List<Object> list = new ArrayList<>();
        JSONParser parser = new JSONParser("{\n" +
                "  \"1\": 1,\n" +
                "  \"2\": 2,\n" +
                "  \"3\": 3,\n" +
                "  \"4\": 4,\n" +
                "  \"5\": 5,\n" +
                "  \"6\": 6,\n" +
                "  \"7\": 7,\n" +
                "  \"8\": 8,\n" +
                "  \"9\": 9,\n" +
                "  \"10\": 10\n" +
                "}");
        for (int i  = 0; i < 10; i++) {
            Object jsonObject = parser.parse();
            list.add(jsonObject);
        }


        return list;
    }
}
