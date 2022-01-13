package com.spring.runtimes.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.runtimes.models.RuntimeDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

@Slf4j
public class RuntimeUtils {

    public static String prettyPrintJson(List<RuntimeDetails> logModelList) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(logModelList);
        } catch (JsonProcessingException e){
            log.error("Exception: JsonProcessingException processing {}",logModelList);
            return Strings.EMPTY;
        }
    }

    public static String prettyPrintJson(RuntimeDetails runtimeDetails) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(runtimeDetails);
        } catch (JsonProcessingException e){
            log.error("Exception: JsonProcessingException processing {}",runtimeDetails);
            return Strings.EMPTY;
        }
    }
}
