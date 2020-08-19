package com.vk.planner.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanNotFoundException extends RuntimeException {

    private String message;
}
