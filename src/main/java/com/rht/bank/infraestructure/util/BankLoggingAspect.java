package com.rht.bank.infraestructure.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BankLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(BankLoggingAspect.class);

    @Pointcut("execution(* com.rht.bank.infraestructure.adapter.inbound.BankController.getBankById(..)) && args(id,..)")
    public void getentidadbancariaByIdMethod(Long id) {}

    @Before("execution(* com.rht.bank.infraestructure.adapter.inbound.BankController.getBankById(..)) && args(id,..)")
    public void logNegativeId(JoinPoint joinPoint, Long id) {
        if (id < 0) {
            logger.warn("Se ha solicitado una nave con un ID negativo: {}", id);
        }
    }
}
