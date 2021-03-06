package com.spring.runtimes.controller;

import com.spring.runtimes.models.RecentExecutionLogs;
import com.spring.runtimes.utils.RuntimeUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(name = "service.method.runtimes.log",havingValue = "true")
public class RuntimeController {

    @GetMapping("/runtime-logs")
    public ResponseEntity<?> getRuntimeLogs(){
        return new ResponseEntity<>(RuntimeUtils.prettyPrintJson(RecentExecutionLogs.getLogModels()),
                HttpStatus.ACCEPTED);
    }
}
