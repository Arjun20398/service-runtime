package com.spring.runtimes.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MethodDetails {

    private String className;
    private String methodName;
    private Long runTime;
    private List<MethodDetails> internalMethodCalls;
}
