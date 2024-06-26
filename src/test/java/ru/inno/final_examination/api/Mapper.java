package ru.inno.final_examination.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private final ObjectMapper mapper;

    public Mapper() {
        mapper = new ObjectMapper();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}