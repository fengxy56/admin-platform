package com.hongkun.model.excel.site;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.ToString;

@Data
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(20)
@ToString
public class LogSiteUserlExcel {

    @ColumnWidth(15)
    @ExcelProperty(value = "账号")
    private String userid;

    @ColumnWidth(15)
    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "组织")
    private String department;

    @ColumnWidth(15)
    @ExcelProperty(value = "岗位")
    private String jobTitle;

    @ExcelProperty(value = "时间")
    private String createTime;


}
