package com.spring.runtimes.models;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.spring.runtimes.constants.AppConstants;

import java.util.*;

public class RecentExecutionLogs {

    public static Queue<RuntimeDetails> logModels = new LinkedList<>();

    public static void addLog(RuntimeDetails logModel){
        if(Objects.isNull(logModel) || logModel.getInternalMethodCalls().isEmpty()){
            return;
        }
        if(logModels.size() >= AppConstants.LOG_SIZE){
            logModels.remove();
        }
        logModels.add(logModel);
    }

    @JsonRawValue
    public static List<RuntimeDetails> getLogModels(){
        return new ArrayList<>(logModels);
    }
}
