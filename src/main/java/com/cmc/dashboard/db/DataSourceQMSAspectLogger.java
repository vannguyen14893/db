package com.cmc.dashboard.db;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnExpression("${spring.datasource.pool.logging}")
public class DataSourceQMSAspectLogger {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("qmsDataSource")
    private DataSource qmsDataSource;

    @Before("execution(* com.cmc.dashboard.qms.repository.*.*(..))")
    public void logBeforeConnection(JoinPoint jp) throws Throwable {
        logDataSourceInfos("Before", jp);
    }

    @After("execution(* com.cmc.dashboard.qms.repository.*.*(..)) ")
    public void logAfterConnection(JoinPoint jp) throws Throwable {
        logDataSourceInfos("After", jp);
    }

    public void logDataSourceInfos(final String time, final JoinPoint jp) {
        final String method = String.format("%s:%s", jp.getTarget().getClass().getName(), jp.getSignature().getName());
        logger.info(String.format("%s %s: number of connections [QMS]: active=%d idel=%d wait=%d abandoned=%d.", time, method, qmsDataSource.getNumActive(), 
        		qmsDataSource.getNumIdle(), qmsDataSource.getWaitCount(), qmsDataSource.getRemoveAbandonedCount()));        
    }
}
