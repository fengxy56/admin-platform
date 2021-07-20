package com.hongkun.model.excel.hx;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.ToString;

@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(30)
@ToString
public class HxWorkReportDailySummaryExcel {

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

    @ColumnWidth(15)
    @ExcelProperty(value = "类型")
    private String type;

//    @ColumnWidth(15)
//    @ExcelProperty(value = "时间")
//    private String time;

    @ColumnWidth(10)
    @ExcelProperty(value = "次数")
    private String num;

}
