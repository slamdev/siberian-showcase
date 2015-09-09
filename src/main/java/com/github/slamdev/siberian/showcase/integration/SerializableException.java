package com.github.slamdev.siberian.showcase.integration;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SerializableException {

    private SerializableException cause;
    private String className;
    private String message;
    private SerializableStackTraceElement[] stackTrace;

    public SerializableException() {
    }

    public SerializableException(Throwable toSerialize) {
        this(toSerialize, getHostName());
    }

    public SerializableException(Throwable toSerialize, String host) {
        message = toSerialize.getMessage();
        className = toSerialize.getClass().getName();
        if (toSerialize.getCause() != null) {
            cause = new SerializableException(toSerialize.getCause(), host);
        }
        StackTraceElement[] stackTrace = toSerialize.getStackTrace();
        this.stackTrace = new SerializableStackTraceElement[stackTrace.length];
        for (int i = 0; i < stackTrace.length; ++i) {
            this.stackTrace[i] = new SerializableStackTraceElement(stackTrace[i], host);
        }
    }

    SerializableException(String className, String message, SerializableException cause,
                          SerializableStackTraceElement[] stackTrace) {
        super();
        this.className = className;
        this.message = message;
        this.cause = cause;
        this.stackTrace = stackTrace;
    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("Failed to find name for local host", e);
        }
    }

    public synchronized SerializableException getCause() {
        return cause;
    }

    public void setCause(SerializableException cause) {
        this.cause = cause;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SerializableStackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(SerializableStackTraceElement[] serializableStackTrace) {
        stackTrace = serializableStackTrace;
    }

    @Override
    public String toString() {
        String message = getMessage();
        return className + (message == null ? "" : ": " + message);
    }

    public static class SerializableStackTraceElement {

        private String className;

        private String fileName;

        private String host;

        private int lineNumber;

        private String methodName;

        SerializableStackTraceElement() {
        }

        SerializableStackTraceElement(StackTraceElement e, String host) {
            className = e.getClassName();
            methodName = e.getMethodName();
            fileName = e.getFileName();
            lineNumber = e.getLineNumber();
            this.host = host;
        }

        SerializableStackTraceElement(String className, String methodName, String fileName, int lineNumber,
                                      String host) {
            super();
            this.className = className;
            this.methodName = methodName;
            this.fileName = fileName;
            this.lineNumber = lineNumber;
            this.host = host;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder(className + "." + methodName);
            if (host != null) {
                b.append("@" + host);
            }
            if (lineNumber == -2) {
                b.append("(Native Method");
            } else if (fileName != null && lineNumber >= 0) {
                b.append("(" + fileName + ":" + lineNumber + ")");
            } else {
                b.append("(Unknown Source)");
            }
            return b.toString();
        }
    }
}