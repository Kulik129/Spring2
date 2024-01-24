package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Reader {
    private static long sequence = 1L;
    private long id;
    private String name;

    public Reader(String name) {
        this(sequence++, name);
    }
}
