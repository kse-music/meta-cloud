package com.hiekn.metaboot.exception;


import com.hiekn.metaboot.bean.result.ErrorCodes;

public class RestException extends BaseException{
	
    private RestException(ErrorCodes code) {
		super(code);
	}

	public static RestException newInstance(){
		return newInstance(ErrorCodes.PARAM_PARSE_ERROR);
	}
	
	public static RestException newInstance(ErrorCodes code){
		return new RestException(code);
	}

}
