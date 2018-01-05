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
 * 国际化包装的runtime异常信息
 * 
 * @author fangjianping
 *
 */
public class TnRuntimeException extends RuntimeException {

    /**
	 * 
	 */
    private static final long serialVersionUID = -442802263750159377L;
    private List<I18nMsg> errorMsgs = null;
    private I18nMsg errorMsg = null;

    /**
	 * 
	 */
    public TnRuntimeException() {
	super();
    }

    /**
     * @param key
     * @param varargs
     */
    public TnRuntimeException(String key, Object... varargs) {
	super(I18nMsg.createMsg(key, varargs).toString());
	errorMsg = new I18nMsg(key, varargs);
	this.addErrorMsg(errorMsg);
    }

    /**
     * @param cause
     * @param key
     * @param varargs
     */
    public TnRuntimeException(Throwable cause, String key, Object... varargs) {
	super(I18nMsg.createMsg(key, varargs).toString(), cause);

	errorMsg = new I18nMsg(key, varargs);
	this.addErrorMsg(errorMsg);
    }

    /**
     * @param cause
     */
    public TnRuntimeException(Throwable cause) {
	super(cause);
    }

    public void addErrorMsg(I18nMsg msg) {
	if (this.errorMsgs == null) {
	    this.errorMsgs = new ArrayList<I18nMsg>();
	}
	this.errorMsgs.add(msg);
    }

    public void addErrorMsgs(List<I18nMsg> msg) {
	if (this.errorMsgs == null) {
	    this.errorMsgs = new ArrayList<I18nMsg>();
	}
	this.errorMsgs.addAll(msg);
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
	    return super.getLocalizedMessage();

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
}
