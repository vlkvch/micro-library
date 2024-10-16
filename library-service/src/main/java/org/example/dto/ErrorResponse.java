package org.example.dto;

import lombok.Data;

@Data
public class ErrorResponse {

    private int status;
    private String message;

}
