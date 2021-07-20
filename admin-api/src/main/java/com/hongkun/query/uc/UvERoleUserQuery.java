package com.hongkun.query.uc;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName UvERoleUserQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/3/31 18:31
 */
@Data
@ApiModel("用户标准角色请求参数")
public class UvERoleUserQuery {

    @ApiModelProperty(value = "账户", example = "zhangquan")
    private String accountName;

    @ApiModelProperty(value = "姓名", example = "张全")
    private String chsName;

    @ApiModelProperty(value = "标准角色id", example = "CFDQ0")
    @JsonProperty("bRoleId")
    private String bRoleId;

    @ApiModelProperty(value = "标准角色名称", example = "地产")
    @JsonProperty("bRoleName")
    private String bRoleName;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 10;


}
