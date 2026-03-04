package com.arturo.releaseradarapi.dto;

import lombok.Data;

@Data
public class VerificarOtpRequest {
    private String email;
    private String codigoOtp;
}