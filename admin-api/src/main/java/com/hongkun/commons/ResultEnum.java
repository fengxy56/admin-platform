package com.hongkun.commons;

import lombok.Getter;

@Getter
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(1, "success"),

    SIGN_DISTANCE_CLOSE(0, "该签到点与其他签到点距离太近，请重新选择！"),


    /**
     * 服务器异常
     */
    SERVER_ERROR(500, "服务器异常！"),
    /**
     * 参数错误
     */
    PARAM_ERROR(1002, "参数错误"),

    /**
     * 参数不能为空
     */
    PARAM_NOT_NULL(1003, "参数不能为空"),

    /**
     * token错误或缺失
     */
    TOKEN_ERROR(1000, "token错误或缺失"),
    COOKIE_MISSING(1000, "cookie缺失"),


    /**********业务状态码*********/
    MD5_ENCRY_FAIL(1004, "【系统配置】md5加密是吧"),

    SAVEORUPDATE_SYSINFO_FAIL(1005, "【系统配置】保存或者修改系统信息失败"),

    SAVEORUPDATE_SMSSYSINFO_FAIL(1006, "【短信系统配置】保存或者修改系统信息失败"),

    IMPORT_EXCEL(1007, "导入excel长度不能大于1000条！"),


    ZX_CAN_NOT_EDIT(2006, "【资讯管理】资讯已失效，无法修改"),

    DB_OPR_ERROR(2007, "数据库执行失败！"),
    MENU_DELETE_ERROR(2008, "菜单删除失败！"),
    USED_MENU_DELETE_ERROR(2009, "该菜单已被授权,不允许删除！"),

    CHILD_MENU_DELETE_ERROR(2010, "其下还有子菜单，不允许删除！"),

    ROLE_NOT__ERROR(2011, "该角色不存在编制中的授权数据！"),

    DICT_NOT_RESET_ERROR(2012, "同一字典组下不能有相同级别的字典编码！"),

    DICT_NOT_SAME_ERROR(2013, "不同有顶级相同字典组！"),

    MENU_AUTH_NOT_NULL(2014, "授权菜单不能为空！"),


    SAVE_UPDATE_APP_ERROR(3007, "保存或者修改轻应用信息失败！"),

    UPDATE_APPID_EXIST(3008, "轻应用id不存在,修改失败！"),

    UPDATE_APPNAME_EXIST(3009, "轻应用名称已经存在,修改失败！"),

    SAVE_APPID_EXIST(3010, "轻应用id已存在,保存失败！"),

    SAVE_APPNAME_EXIST(3011, "轻应用名称已存在,保存失败！"),

    SAVE_USERROLE_APP_NOTEXIST(3012, "轻应用id集合为空,保存失败！"),

    UPDATE_SYS_INFO(3013, "修改系统信息,系统编码重复！"),

    DELETE_SIGN_TRACK_AUTH(3014, "删除签到估计权限失败！"),;
    private int code;
    private String errMsg;

    ResultEnum(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }
}
