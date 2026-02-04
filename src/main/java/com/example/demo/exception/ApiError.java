package com.example.demo.exception;

import java.time.Instant;

/*
{
  "timestamp": "2026-01-29T01:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "equity is required",
  "path": "/market/1.0/equities"
}


 */

public class ApiError {
    public Instant timestamp = Instant.now();
    public int status;
    public String error;
    public String message;
    public String path;

    public ApiError(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
