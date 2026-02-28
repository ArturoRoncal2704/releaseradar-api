package com.arturo.releaseradarapi.dto;

import lombok.Data;

@Data
public class RegistroRequest {
    private String email;
    private String password;
}