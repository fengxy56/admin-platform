package com.hongkun.bean.apply;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 文件
 *
 * @author fengxiaoyang
 * @date 2020-7-27 17:04:23
 */
@Data
@ApiModel("文件")
@TableName("hx_file")
public class HxFile implements Serializable {

    @ApiModelProperty(value = "文件id,主键")
    @TableId
    private String fileId;

    @ApiModelProperty(value = "null")
    private String fileType;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "图片缩略图路径")
    private String imgThumPath;

    @ApiModelProperty(value = "系统类型")
    private String sysType;

    @ApiModelProperty(value = "上传用户")
    private String userName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改 时间")
    private Date updateTime;


}
