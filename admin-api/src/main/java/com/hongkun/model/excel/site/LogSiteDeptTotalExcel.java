package com.hongkun.model.excel.site;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(30)
@ToString
public class LogSiteDeptTotalExcel {

    @ColumnWidth(40)
    @ExcelProperty(value = "组织")
    private String department;

    @ColumnWidth(15)
    @ExcelProperty(value = "次数")
    private String visitCount;

}
