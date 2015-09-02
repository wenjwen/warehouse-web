package com.warehouse.util;

public class AjaxResult
{
	private Boolean isError = false;  // 是否错误
	
	private String code; // 状态码
	
	private String msg;  // 返回信息

	public Boolean getIsError()
	{
		return isError;
	}

	public void setIsError(Boolean isError)
	{
		this.isError = isError;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
	
	
}
