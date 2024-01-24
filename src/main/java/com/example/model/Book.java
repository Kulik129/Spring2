package com.example.model;

import lombok.*;

@Data
@AllArgsConstructor
public class Book {
    public static long sequence = 1L;
    private long id;
    private final String name;

    public Book(String name) {
        this.id = sequence++;
        this.name = name;
    }
}
