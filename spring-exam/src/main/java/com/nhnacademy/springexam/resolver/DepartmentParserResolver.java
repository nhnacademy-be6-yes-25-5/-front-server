package com.nhnacademy.springexam.resolver;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.nhnacademy.springexam.parser.CsvDepartmentParser;
import com.nhnacademy.springexam.parser.DepartmentParser;
import com.nhnacademy.springexam.parser.JsonDepartmentParser;
import com.nhnacademy.springexam.parser.TextDepartmentParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentParserResolver {


    public <E> DepartmentParserResolver(List<E> textDepartmentParser) {

    }

    public DepartmentParser getDepartmentParser(String fileName) {
        if (fileName.endsWith(".csv")) {
            return new CsvDepartmentParser();
        } else if (fileName.endsWith(".json")) {
            return new JsonDepartmentParser(new JsonMapper());
        } else if (fileName.endsWith(".txt")) {
            return new TextDepartmentParser();
        } else {
            return null;
        }
    }
}
