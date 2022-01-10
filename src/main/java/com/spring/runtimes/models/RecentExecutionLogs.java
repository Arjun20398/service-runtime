package com.spring.runtimes.models;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.spring.runtimes.constants.AppConstants;

import java.util.*;

public class RecentExecutionLogs {

    public static Queue<MethodDetails> logModels = new LinkedList<>();

    public static void addLog(MethodDetails logModel){
        if(Objects.isNull(logModel) || logModel.getInternalMethodCalls().isEmpty()){
            return;
        }
        if(logModels.size() >= AppConstants.LOG_SIZE){
            logModels.remove();
        }
        logModels.add(logModel);
    }

    @JsonRawValue
    public static List<MethodDetails> getLogModels(){
        return new ArrayList<>(logModels);
    }
}
