package com.hongkun.model.hx;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileModel {

    @ApiModelProperty(value = "文件id")
    private String fileId;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件上传源路径")
    private String original;

    @ApiModelProperty(value = "文件上传缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "文件类型")
    private Integer fileType = 3;//1 图片 2 视频 3 其他文件


    public FileModel(String fileId, String fileName, String original, String thumbnail, Integer fileType) {
        super();
        this.fileId = fileId;
        this.fileName = fileName;
        this.original = original;
        this.thumbnail = thumbnail;
        this.fileType = fileType;
    }


}