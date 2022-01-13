package com.spring.runtimes.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


@Data
public class RequestLogQueue {

    public final Queue<LogResponse> logResponses = new LinkedList<>();

    public RuntimeDetails jsonify(){

        RuntimeDetails logModel = new RuntimeDetails();
        logModel.setInternalMethodCalls(new ArrayList<>());
        while (logResponses.size()>0 && logResponses.peek().getLogEnum().equals(LogType.START)){
            logModel.getInternalMethodCalls().add(jsonifyHelperSingle(logResponses));
        }
        return logModel;
    }

    public RuntimeDetails jsonifyHelperSingle(Queue<LogResponse> logResponses){
        RuntimeDetails logModel = new RuntimeDetails();

        LogResponse logResponse = logResponses.remove();
        logModel.setMethodName((String) logResponse.getData());
        logModel.setClassName(logResponse.getClassName());

        if(logResponses.size()>0 && logResponses.peek().getLogEnum().equals(LogType.START)){
            logModel.setInternalMethodCalls(jsonify().getInternalMethodCalls());
        }
        logModel.setRunTime((Long) logResponses.remove().getData());
        return logModel;
    }

}
