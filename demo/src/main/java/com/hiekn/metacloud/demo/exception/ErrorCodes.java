package com.hiekn.metacloud.demo.exception;

import cn.hiboot.mcn.core.exception.ErrorMsg;

/*
    3xxxx:通用错误码定义
    5xxxx:业务相关错误码定义
    7xxxx:未知错误码
    8xxxx:Http相关错误码定义
    9xxxx:统一错误码及第三方服务错误码定义
 */
public class ErrorCodes extends ErrorMsg {
    public static final int USER_EXIST_ERROR = 50001;
    public static final int USER_NOT_FOUND_ERROR = 50002;
}
