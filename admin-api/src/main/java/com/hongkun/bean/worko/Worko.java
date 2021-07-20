package com.hongkun.bean.worko;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2020-11-19 16:16:51
 */
@Data
@ApiModel("null")
@TableName("worko")
public class Worko implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	@TableId
	private Integer id;

  	@ApiModelProperty(value = "null" )
	private String articleContent;

  	@ApiModelProperty(value = "null" )
	private String articleIconUrl;

  	@ApiModelProperty(value = "null" )
	private String articleTitle;

  	@ApiModelProperty(value = "null" )
	private String articleUrl;

  	@ApiModelProperty(value = "创建时间" )
	private Date createTime;

  	@ApiModelProperty(value = "数据key" )
	private String dataKey;

  	@ApiModelProperty(value = "是否匿名：0否，1是" )
	private Integer isAnonymous;

  	@ApiModelProperty(value = "null" )
	private Integer isArticle;

  	@ApiModelProperty(value = "是否删除：0否，1是" )
	private Integer isDelete;

  	@ApiModelProperty(value = "null" )
	private Integer isZan;

  	@ApiModelProperty(value = "评论数量" )
	private Integer pinglunTotal;

  	@ApiModelProperty(value = "发布的图片" )
	private String pubImages;

  	@ApiModelProperty(value = "发布时间" )
	private String pubTime;

  	@ApiModelProperty(value = "发布人头像" )
	private String pubUserIcon;

  	@ApiModelProperty(value = "发布人岗位" )
	private String pubUserJobs;

  	@ApiModelProperty(value = "发布人账号" )
	private String pubUserName;

  	@ApiModelProperty(value = "发布人姓名" )
	private String pubUserTrueName;

  	@ApiModelProperty(value = "发布内容" )
	private String pubWordsContent;

  	@ApiModelProperty(value = "修改时间" )
	private Date updateTime;

  	@ApiModelProperty(value = "null" )
	private String workoFrom;

  	@ApiModelProperty(value = "null" )
	private String workoId;

  	@ApiModelProperty(value = "点赞数量" )
	private Integer zanTotal;

  	@ApiModelProperty(value = "null" )
	private Integer ranges;

  	@ApiModelProperty(value = "null" )
	private String rangeName;

  	@ApiModelProperty(value = "null" )
	private Integer oaClick;

  	@ApiModelProperty(value = "null" )
	private String forumCtype;

  	@ApiModelProperty(value = "null" )
	private String forumCtypeId;

  	@ApiModelProperty(value = "null" )
	private String forumFtype;

  	@ApiModelProperty(value = "是否置顶：0否，1是" )
	private Integer isTop;

  	@ApiModelProperty(value = "点问对象" )
	private String dwObject;

  	@ApiModelProperty(value = "点问类型：-1否，1是" )
	private Integer dwType;

  	@ApiModelProperty(value = "null" )
	private Integer isHd;

  	@ApiModelProperty(value = "null" )
	private String urlList;

  	@ApiModelProperty(value = "null" )
	private String userDept;

  	@ApiModelProperty(value = "null" )
	private String ifDelete;

  	@ApiModelProperty(value = "null" )
	private Integer isDwFinish;

  	@ApiModelProperty(value = "定位位置" )
	private String locationAddress;

  	@ApiModelProperty(value = "是否分享：0否，1是" )
	private Integer shareType;

  	@ApiModelProperty(value = "null" )
	private String authName;


}
