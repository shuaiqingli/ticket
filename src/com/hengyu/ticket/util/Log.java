package com.hengyu.ticket.util;


import com.hengyu.ticket.common.Const;

//日志记录
public class Log {

    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Log.class);

    public static void debug(Object... msg) {
        debug(null, msg);
    }

    public static void debug(Class<?> cls, Object... msg) {
        if (Const.DEBUG) {
            log.debug(new StringBuilder().append(getClassInfo(cls)).append(" | ").append(getMsg(msg)));
        }
    }

    public static void info(Class<?> cls, Object... msg) {
        if (Const.DEBUG) {
            log.info(new StringBuilder().append(getClassInfo(cls)).append(" | ").append(getMsg(msg)));
        }
    }

    public static void info(Object... msg) {
        info(null, msg);

    }

    public static void warn(Object... msg) {
        log.warn(getMsg(msg));
    }

    public static void error(Object... msg) {
        log.error(getMsg(msg));
    }


    public static void exception(Exception e, Object... msg) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuffer sb = new StringBuffer();
        sb.append(DateHanlder.getCurrentDateTime()).append("\r\n");
        sb.append(e.getMessage()).append("\r\n");
        for (StackTraceElement ex : stackTrace) {
            sb.append(ex.getClassName()).append("(").append(ex.getLineNumber()).append(")").append("\r\n");
        }
        if (msg != null) {
            sb.append(getMsg(msg));
        }
        log.error(sb.toString());
    }

    public static StringBuilder getMsg(Object... msg) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < msg.length; i++) {
            sb.append(msg[i]);
        }
        return sb;
    }

    public static String getClassInfo(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        try {
            StackTraceElement[] ss = Thread.currentThread().getStackTrace();
            if (ss != null && cls != null) {
                for (int i = ss.length - 1; i >= 0; i--) {
                    StackTraceElement e = ss[i];
                    if (e.getClassName().equals(cls.getName())) {
                        sb.append(e.getClassName()).append(".").append(e.getMethodName()).append("(").append(e.getLineNumber()).append(")");
                        return sb.toString();
                    }
                }
            } else {
                if (ss.length >= 1) {
                    sb.append(Log.class.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
