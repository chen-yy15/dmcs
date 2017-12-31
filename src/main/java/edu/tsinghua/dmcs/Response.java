package edu.tsinghua.dmcs;

import com.alibaba.fastjson.JSONObject;

public class Response {

	private int errcode;

	private String status;

	private String msg;
	
	private Object data;
	
	
	public int getErrcode() {
		return errcode;
	}


	public Response setErrcode(int errcode) {
		this.errcode = errcode;
		return this;
	}

	public String getMsg() {
		return msg;
	}


	public Response setMsg(String msg) {
		this.msg = msg;
		return this;
	}




	public Object getData() {
		return data;
	}




	public Response setData(Object data) {
		this.data = data;
		return this;
	}


	public static Response NEW() {
		Response r = new Response();
		return r;
	}


	public static Response SUCCESS() {
		Response r = new Response();
		r.setErrcode(Constants.RC_SUCCESS_CODE);
		r.setMsg(Constants.RC_SUCCESS_MSG);
		return r;
	}

	public static Response SUCCESSOK() {
		Response r = new Response();
		r.setStatus("ok");
		return r;
	}
	
	public Response returnData(Object object) {
		Response r = SUCCESS();
		r.setData(object);
		return r;
	}
	
	public Response returnFail(int rc, String errmsg, Object object) {
		Response r = new Response();
		r.setErrcode(rc);
		r.setMsg(errmsg);
		if(object != null) {
			r.setData(object);
		}
		return r;
	}

	public Response loginRequired() {
		return returnFail(Constants.RC_LOGIN_REQUIRED_CODE, Constants.RC_LOGIN_REQUIRED_MSG, null);
	}

	public Response authorizationRequired() {
		return returnFail(Constants.RC_AUTH_REQUIRED_CODE, Constants.RC_AUTH_REQUIRED_MSG, null);
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
