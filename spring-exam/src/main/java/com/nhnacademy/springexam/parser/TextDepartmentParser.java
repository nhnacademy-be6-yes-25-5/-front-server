package com.nhnacademy.springexam.parser;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextDepartmentParser implements DepartmentParser{
    @Override
    public String getFileType() {
        return "txt";
    }

    @Override
    public List parsing(File file) throws IOException {
        return Files.readAllLines(Paths.get(file.toURI()));
    }
}
