package com.example.healthhelpapp.exception;

import com.example.healthhelpapp.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ResponseDto> handle(Exception ex) {
        ResponseDto responseDto = ResponseDto.builder()
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
