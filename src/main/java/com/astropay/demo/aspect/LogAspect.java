package com.astropay.demo.aspect;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LogAspect {

  private final String PARAMETER_FORMAT = "%s=%s";

  @Around("@annotation(AuditLog)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Logger log = LogManager.getLogger(joinPoint.getSignature().getDeclaringType());

    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    String[] parameterNames = codeSignature.getParameterNames();
    Object[] arguments = joinPoint.getArgs();
    List<String> logValues = createParameterList(parameterNames, arguments);
    log.debug("message= Started Method '{}' with parameters '{}'", joinPoint.getSignature().getName(), logValues);
    try{
      Object returnValue = joinPoint.proceed();
      log.debug("message= Finished Method '{}' with parameters '{}'", joinPoint.getSignature().getName(), logValues);
      return returnValue;
    }catch(Exception e){
      log.error("message= Error when executing Method '{}' with parameter '{}'",  joinPoint.getSignature().getName(), logValues, e);
      throw e;
    }
  }

  private List<String> createParameterList(String[] parameterNames, Object[] parameterValues){
    List<String> logParameters = new ArrayList<>();
    for(Integer i = 0; i < parameterNames.length; i++){
      logParameters.add(format(PARAMETER_FORMAT,parameterNames[i],parameterValues[i]));
    }
    return logParameters;
  }



}
