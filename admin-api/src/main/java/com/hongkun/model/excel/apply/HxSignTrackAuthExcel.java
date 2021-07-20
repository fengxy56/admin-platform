package com.hongkun.model.excel.apply;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName HxAppAuthExcelVM
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/4 14:03
 */
@Data
public class HxSignTrackAuthExcel {

    @ExcelProperty(value = "账号", index = 0)
    private String authId;

    @ExcelProperty(value = "姓名", index = 1)
    private String authName;

    @ExcelProperty(value = "类型", index = 2)
    private Integer authType;

}
