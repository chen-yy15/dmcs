package edu.tsinghua.dmcs;

public interface Constants {

	// public return code
	public static final int RC_SUCCESS_CODE = 0;
	
	public static final int RC_FAIL_USER_NO_EXIST_CODE = 1;
	
	public static final int RC_FAIL_DEVICE_NO_EXIST_CODE = 2;
	
	public static final int RC_FAIL_DEVICE_UPDATE_CODE = 3;
	
	public static final int RC_FAIL_GROUP_NO_EXIST_CODE = 4;
	
	public static final int RC_FAIL_GROUP_USER_NO_EXIST_CODE = 5;

	public static final int RC_FAIL_PASSWORD_INVALID_CODE= 6;

	public static final int RC_LOGIN_REQUIRED_CODE = 7;

	public static final int RC_AUTH_REQUIRED_CODE = 7;
	
	
	
	
	// public return message
	public static final String RC_SUCCESS_MSG = "成功";
	
	public static final String RC_FAIL_USER_NO_EXIST_MSG = "用户不存在";
	
	public static final String RC_FAIL_DEVICE_NO_EXIST_MSG = "设备不存在";
	
	public static final String RC_FAIL_GROUP_NO_EXIST_MSG = "群组不存在";
	
	public static final String RC_FAIL_DEVICE_UPDATE_MSG = "设备更新失败";
	
	public static final String RC_FAIL_GROUP_USER_NO_EXIST_MSG = "非该群组用户";

	public static final String RC_FAIL_PASSWORD_INVALID_MSG = "不允许该密码格式";

	public static final String RC_LOGIN_REQUIRED_MSG = "未登陆";

	public static final String RC_AUTH_REQUIRED_MSG = "无权限操作";



}
