/**
 * 
 */
package com.oruit.app.common.exception;

import com.oruit.app.common.i18n.I18nMsg;
import com.oruit.app.common.util.MessageHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 进行国际化包装的异常信息基类
 * 
 * @author fangjianping
 * 
 */
public class TnBaseException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7145903884754387413L;
    protected I18nMsg errorMsg = null;
    private List<I18nMsg> errorMsgs = null;

    /**
	 * 
	 */
    public TnBaseException() {
	super();
    }

    public TnBaseException(List<I18nMsg> errorMsgs) {
	super();
	this.errorMsgs = errorMsgs;
    }

    /**
     * @param key
     * @param varargs
     */
    public TnBaseException(String key, Object... varargs) {
	super(I18nMsg.createMsg(key, varargs).getShowMessage());
	errorMsg = new I18nMsg(key, varargs);
	this.addErrorMsg(errorMsg);
    }

    public TnBaseException(String msg, Throwable cause) {
	super(msg, cause);
    }

    /**
     * @param cause
     * @param key
     * @param varargs
     */
    public TnBaseException(Throwable cause, String key, Object... varargs) {
	super(I18nMsg.createMsg(key, varargs).getShowMessage(), cause);
	errorMsg = new I18nMsg(key, varargs);
	this.addErrorMsg(errorMsg);
    }

    /**
     * @param cause
     */
    public TnBaseException(Throwable cause) {
	super(cause);
    }

    public final void addErrorMsg(I18nMsg msg) {
	if (this.errorMsgs == null) {
	    this.errorMsgs = new ArrayList<I18nMsg>();
	}
	this.errorMsgs.add(msg);
    }

    public void addErrorMsg(String key, Object... varargs) {
	this.addErrorMsg(new I18nMsg(key, varargs));
    }

    /**
     * 指定Locale获取对应的错误本地化信息，主要用在页面展现异常信息是需要改版Locale与当前用户一致的情况下
     * 
     * @param locale
     * @return
     */
    public String getErrorMessage(Locale locale) {
	if (errorMsg == null)
	    return super.getMessage();

	return errorMsg.getShowMessage(locale);
    }

    public List<String> getErrorMessageList(Locale locale) {
	List<String> errorMessageList = new ArrayList<String>();

	for (I18nMsg msg : errorMsgs) {
	    errorMessageList.add(msg.getShowMessage(locale));
	}
	return errorMessageList;
    }

    public List<I18nMsg> getErrorI18nMsgs() {
	return errorMsgs;
    }

    /**
     * 用来显示在页面的错误信息，根据用户的当前Locale信息。
     * 
     * @return
     */
    public List<String> getUIShowMessageList() {

	return this.getErrorMessageList(MessageHolder.getLocale());
    }

    /**
     * 获取用户当前显示的LOCALE一致的错误信息
     * 
     * @param locale
     * @return
     */
    public String getUIShowMessage() {

	return this.getErrorMessage(MessageHolder.getLocale());
    }

    @Override
    public String getMessage() {
	return this.getUIShowMessage();
    }
}
