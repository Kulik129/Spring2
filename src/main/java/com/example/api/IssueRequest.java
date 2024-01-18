package com.example.api;

import lombok.Data;

@Data
public class IssueRequest {
    private long bookId;
    private long readerId;
}
