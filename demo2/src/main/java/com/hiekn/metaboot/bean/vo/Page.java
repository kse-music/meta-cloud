package com.hiekn.metaboot.bean.vo;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.Objects;

public class Page {

    @ApiParam("当前页，默认1")
    @DefaultValue("1")
    @QueryParam("pageNo")
    private Integer pageNo;
    @ApiParam("每页数，默认10")
    @DefaultValue("10")
    @QueryParam("pageSize")
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
