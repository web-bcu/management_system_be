package com.example.auth.exception;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
