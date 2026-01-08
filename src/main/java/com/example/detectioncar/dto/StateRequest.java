package com.example.detectioncar.dto;

import jakarta.validation.constraints.NotNull;

public class StateRequest {

    @NotNull
    private Integer state; // 0/1

    public StateRequest() {}

    public Integer getState() { return state; }
    public void setState(Integer state) { this.state = state; }
}
