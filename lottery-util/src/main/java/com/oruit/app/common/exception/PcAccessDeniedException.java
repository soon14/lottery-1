package com.oruit.app.common.exception;

/**
 * 
 * @author lixiangjun
 *
 */
public class PcAccessDeniedException extends TnRuntimeException {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4049750973611234078L;

    public PcAccessDeniedException() {
	super("没有权限");

    }

    public PcAccessDeniedException(String key, Object... varargs) {
	super(key, varargs);

    }

    public PcAccessDeniedException(String msg, Throwable cause) {
	super(msg, cause);

    }

    public PcAccessDeniedException(Throwable cause, String key,
	    Object... varargs) {
	super(cause, key, varargs);

    }

    public PcAccessDeniedException(Throwable cause) {
	super(cause);

    }
}
