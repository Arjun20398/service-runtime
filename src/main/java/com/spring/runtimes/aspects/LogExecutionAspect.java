package com.spring.runtimes.aspects;

import com.spring.runtimes.constants.AppConstants;
import com.spring.runtimes.models.*;
import com.spring.runtimes.utils.RuntimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@Aspect
@Slf4j
@Component
@ConditionalOnProperty(name = "service.method.runtimes.log",havingValue = "true")
public class LogExecutionAspect {

    @Around("@within(org.springframework.stereotype.Service)")
    public Object logMethodsInsideExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        addResponse(LogType.START,methodName,className);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        addResponse(LogType.END,System.currentTimeMillis() - start,className);
        return result;
    }

    @Before("@within(org.springframework.web.bind.annotation.RestController)")
    public void addRequestContextHolder(){
        if(!Objects.isNull(RequestContextHolder.getRequestAttributes())){
            RequestContextHolder.getRequestAttributes()
                    .setAttribute(AppConstants.LOG_EXECUTION_MODEL,new RequestLogQueue(),RequestAttributes.SCOPE_REQUEST);
        }
    }

    @After("@within(org.springframework.web.bind.annotation.RestController)")
    public void printExecutionData() {
        if(Objects.isNull(RequestContextHolder.getRequestAttributes()) || Objects.isNull(RequestContextHolder
                .getRequestAttributes().getAttribute(AppConstants.LOG_EXECUTION_MODEL, RequestAttributes.SCOPE_REQUEST))){
            return;
        }
        RuntimeDetails runtimeDetails = ((RequestLogQueue) RequestContextHolder.getRequestAttributes()
                .getAttribute(AppConstants.LOG_EXECUTION_MODEL, RequestAttributes.SCOPE_REQUEST)).jsonify();
        if(!runtimeDetails.getInternalMethodCalls().isEmpty()) {
            RecentExecutionLogs.addLog(runtimeDetails);
            log.info("\n{}", RuntimeUtils.prettyPrintJson(runtimeDetails));
        }
    }

    private void addResponse(LogType logEnum, Object data, String className){
        if(Objects.isNull(RequestContextHolder.getRequestAttributes()) || Objects.isNull(RequestContextHolder
                .getRequestAttributes().getAttribute(AppConstants.LOG_EXECUTION_MODEL, RequestAttributes.SCOPE_REQUEST))){
            return;
        }
        ((RequestLogQueue) Objects.requireNonNull(Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .getAttribute(AppConstants.LOG_EXECUTION_MODEL, RequestAttributes.SCOPE_REQUEST))).getLogResponses()
                .add(LogResponse.builder()
                        .data(data)
                        .logEnum(logEnum)
                        .className(className)
                        .build());
    }

}
