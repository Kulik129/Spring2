package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Issue {
    private static long sequence = 1L;


    private long id;
    private long bookId;
    private long readerId;
    private LocalDateTime timestamp;

    public Issue(long bookId, long readerId) {
        this.id = sequence++;
        this.bookId = bookId;
        this.readerId = readerId;
        this.timestamp = LocalDateTime.now();
    }
}
