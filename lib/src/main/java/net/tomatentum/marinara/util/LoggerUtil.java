package net.tomatentum.marinara.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;

public class LoggerUtil {
    public static Logger getLogger(String name) {
        if (LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory)
            return new SimpleLogger(name);
        return LoggerFactory.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
