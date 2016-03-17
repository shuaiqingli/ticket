package com.hengyu.ticket.exception;

import com.hengyu.ticket.util.APIStatus;

/**
 * 
 * @author LGF
 * 2015-12-16
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//提示消息
	private String msg = "服务器忙，请稍后再试...";
	//错误码
	private Integer code = 500;
	
	public BaseException() {
		
	}
	
	public BaseException(String msg,Integer code) {
		this.msg = msg;
		this.code = code;
	}
	
	public BaseException(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		if(msg==null&&code!=null){
			msg = APIStatus.codes.get(code);
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "BaseException [msg=" + msg + ", code=" + code + "]";
	}
	
	

}
