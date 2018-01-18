package com.hiekn.metacloud.demo.bean.vo;

import io.swagger.annotations.ApiParam;

import java.util.Objects;

public class Page {

    @ApiParam(value = "当前页，默认1",defaultValue = "1")
    private Integer pageNo;
    @ApiParam(value = "每页数，默认10",defaultValue = "10")
    private Integer pageSize;

    public Integer getPageNo() {
        if(Objects.isNull(pageNo)){//此处不能用三目表达式，猜测利用反射获取的值不能带逻辑判断？
            return pageNo;
        }
        return (pageNo - 1) * pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
