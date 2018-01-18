package com.hiekn.metaboot.exception;


import com.hiekn.metaboot.bean.result.ErrorCodes;

public class ThirdPartyException extends BaseException {

    private ThirdPartyException(ErrorCodes code) {
        super(code);
    }
    private ThirdPartyException(ErrorCodes code, String msg) {
        super(code,msg);
    }

    public static ThirdPartyException newInstance(){
        return newInstance(ErrorCodes.SERVICE_ERROR);
    }

    public static ThirdPartyException newInstance(ErrorCodes code){
        return new ThirdPartyException(code);
    }

    public static ThirdPartyException newInstance(ErrorCodes code, String msg){
        return new ThirdPartyException(code,msg);
    }
}
