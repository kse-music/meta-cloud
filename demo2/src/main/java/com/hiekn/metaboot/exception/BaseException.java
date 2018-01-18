package com.hiekn.metaboot.exception;


import com.hiekn.metaboot.bean.result.ErrorCodes;

/**
 * 异常基类，各个模块的运行期异常均继承与该类 
 */
public class BaseException extends RuntimeException {

    private ErrorCodes code;
    private String msg;

    protected BaseException(ErrorCodes code) {
        this(code,code.toString());
    }

    protected BaseException(ErrorCodes code,String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ErrorCodes getCode() {
        return code;
    }

    public void setCode(ErrorCodes code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}