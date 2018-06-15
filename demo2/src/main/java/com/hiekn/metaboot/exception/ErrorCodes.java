package com.hiekn.metaboot.exception;

import com.hiekn.boot.autoconfigure.base.exception.ExceptionKeys;
/*
    3xxxx:通用错误码定义
    5xxxx:业务相关错误码定义
    7xxxx:未知错误码
    8xxxx:Http相关错误码定义
    9xxxx:统一错误码及第三方服务错误码定义
 */
public interface ErrorCodes extends ExceptionKeys {

    Integer INIT_INTENT_ERROR = 50003;
}
