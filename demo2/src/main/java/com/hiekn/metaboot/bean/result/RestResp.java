package com.hiekn.metaboot.bean.result;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResp<T> {
	
    public enum ActionStatusMethod {
        OK("OK"),
        FAIL("FAIL");
        private final String name;
        ActionStatusMethod(final String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }
    }
	
    @JsonProperty("ActionStatus")
	private String ActionStatus = ActionStatusMethod.OK.toString();
    @JsonProperty("ErrorCode")
	private Integer ErrorCode = 0;
    @JsonProperty("ErrorInfo")
	private String ErrorInfo = "";
	private T data;

	public RestResp() {	}
	
	public RestResp(Integer code,String msg){
		this.ActionStatus = ActionStatusMethod.FAIL.toString();
		this.ErrorCode = code;
		this.ErrorInfo = msg;
	}
	
	public RestResp(T data){
		this.data = data;
	}
	
	@JsonIgnore
	public String getActionStatus() {
		return ActionStatus;
	}

	public void setActionStatus(String actionStatus) {
		ActionStatus = actionStatus;
	}
	
	@JsonIgnore
	public Integer getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(Integer errorCode) {
		ErrorCode = errorCode;
	}
	
	@JsonIgnore
	public String getErrorInfo() {
		return ErrorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
