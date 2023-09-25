package com.example.healthhelpapp.dto.mapper;

public interface ResponseMapper<R,D> {

    R  mapToResponse(D value);
}
