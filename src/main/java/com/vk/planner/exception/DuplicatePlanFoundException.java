package com.vk.planner.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DuplicatePlanFoundException extends RuntimeException {

    private String message;
}
