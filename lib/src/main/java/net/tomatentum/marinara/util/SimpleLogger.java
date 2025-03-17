package net.tomatentum.marinara.util;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.LegacyAbstractLogger;
import org.slf4j.helpers.MessageFormatter;

public class SimpleLogger extends LegacyAbstractLogger {

    private String name;

    public SimpleLogger(String name) {
        this.name = name;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFullyQualifiedCallerName'");
    }

    @Override
    protected void handleNormalizedLoggingCall(Level level, Marker marker, String messagePattern, Object[] arguments,
            Throwable throwable) {
        String formatted = MessageFormatter.basicArrayFormat(messagePattern, arguments);
        System.out.println("[%s] %s => %s".formatted(level, this.name, formatted));
    }


    
}
