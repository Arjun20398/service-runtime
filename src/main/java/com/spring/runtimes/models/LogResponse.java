package com.spring.runtimes.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogResponse {

    private LogType logEnum;
    private String className;
    private Object data;
}
