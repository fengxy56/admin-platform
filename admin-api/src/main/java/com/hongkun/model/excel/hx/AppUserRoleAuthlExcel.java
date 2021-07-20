package com.hongkun.model.excel.hx;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(20)
@ToString
public class AppUserRoleAuthlExcel {

    @ColumnWidth(15)
    @ExcelProperty(value = "权限类型")
    private String authTypeStr;

    @ColumnWidth(15)
    @ExcelProperty(value = "应用id")
    private String appId;

    @ExcelProperty(value = "应用名称")
    private String appName;

    @ColumnWidth(15)
    @ExcelProperty(value = "权限id")
    private String authId;

    @ExcelProperty(value = "权限名称")
    private String authName;

    @ExcelIgnore
    private Integer authType;

}
