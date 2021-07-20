package com.hongkun.model.excel.apply;

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
public class HxSignTrackRecordSummaryExcel {

//    @ColumnWidth(15)
//    @ExcelProperty(value = "账号")
//    private String userid;

    @ExcelProperty(value = "公司名称" )
    private String cityCompanyName;

    @ExcelProperty(value = "项目名称" )
    private String projectName;

    @ExcelProperty(value = "监理公司" )
    private String jlCompanyName;

    @ExcelProperty(value = "账号" )
    private String account;

    @ApiModelProperty(value = "姓名" )
    private String name;

    @ApiModelProperty(value = "岗位" )
    private String jobTitle;

    @ApiModelProperty(value = "是否在职" )
    private String status;

    @ApiModelProperty(value = "最后修改时间" )
    private String lastUpdateTime;

    @ExcelProperty(value = "打卡天数" )
    private String dkCount;

}
