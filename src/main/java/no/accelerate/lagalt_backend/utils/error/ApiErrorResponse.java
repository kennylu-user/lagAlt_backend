package no.accelerate.lagalt_backend.utils.error;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private String timestamp;
    private Integer status;
    private String error;
    private String trace;
    private String message;
    private String path;
}

