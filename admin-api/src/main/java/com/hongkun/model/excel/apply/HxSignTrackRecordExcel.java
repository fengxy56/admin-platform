package com.hongkun.model.excel.apply;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(20)
@ToString
public class HxSignTrackRecordExcel {

//    @ColumnWidth(15)
//    @ExcelProperty(value = "账号")
//    private String userid;

    @ExcelProperty(value = "公司名称" )
    private String companyName;

    @ExcelProperty(value = "项目名称" )
    private String projectName;

    @ExcelProperty(value = "主键id" )
    private String recordId;

    @ExcelProperty(value = "姓名" )
    private String userName;

    @ExcelProperty(value = "账号" )
    private String account;

    @ApiModelProperty(value = "岗位" )
    private String jobTitle;

    @ApiModelProperty(value = "是否在职" )
    private String status;

    @ApiModelProperty(value = "最后修改时间" )
    private String lastUpdateTime;

    @ExcelProperty(value = "签到id" )
    private String positionId;

    @ExcelProperty(value = "签到名称" )
    private String positionName;

    @ExcelProperty(value = "签到时间" )
    private Date signTime;

}
