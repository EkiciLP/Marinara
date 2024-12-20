package net.tomatentum.marinara.util;

import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ProviderUtil;

public class LoggerUtil {
    public static Logger getLogger(String name) {
        if (ProviderUtil.hasProviders()) {
            return LogManager.getLogger(name);
        }else
            return new SimpleLogger(name, Level.DEBUG, true, false, true, true, "yyyy-MM-dd HH:mm:ss.SSSZ", null,
                                                 new PropertiesUtil(new Properties()), System.out);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
