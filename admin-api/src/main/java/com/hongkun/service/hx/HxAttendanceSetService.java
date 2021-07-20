package com.hongkun.service.hx;

import com.hongkun.model.hx.HxAttendanceSetModel;
import com.hongkun.query.hx.mongodb.HxAttendanceSetQuery;
import com.hongkun.query.hx.mongodb.HxAttendanceSetUpdateQuery;

import java.util.List;

/**
 * @ClassName HxAttendanceSetService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/25 14:03
 */

public interface HxAttendanceSetService {

    //获取签到点信息
    List<HxAttendanceSetModel> getHxAttendanceSetList(HxAttendanceSetQuery hxAttendanceSetQuery);


    //获取签到信息总数量
    Integer countHxAttendanceSet(HxAttendanceSetQuery hxAttendanceSetQuery);


    //根据主键id获取签到信息
    HxAttendanceSetModel getHxAttendanceSetById(String id);

    //根据主键id修改签到信息
    void updateHxAttendanceSetById(HxAttendanceSetUpdateQuery hxAttendanceSetUpdateQuery);


}
