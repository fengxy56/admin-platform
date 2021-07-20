package com.hongkun.service.hx.impl;


import com.hongkun.model.hx.HxWifiInfoModel;
import com.hongkun.query.hx.mongodb.HxAttendanceSetUpdateQuery;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.hongkun.model.hx.HxAttendanceSetModel;
import com.hongkun.mongodb.dao.HxAttendanceSetDao;
import com.hongkun.query.hx.mongodb.HxAttendanceSetQuery;
import com.hongkun.service.hx.HxAttendanceSetService;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName HxAttendanceSetServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/25 14:03
 */
@Slf4j
@Service
public class HxAttendanceSetServiceImpl implements HxAttendanceSetService {

    @Autowired
    private HxAttendanceSetDao hxAttendanceSetDao;


    //获取签到点信息
    public List<HxAttendanceSetModel> getHxAttendanceSetList(HxAttendanceSetQuery hxAttendanceSetQuery) {

        List<HxAttendanceSetModel> list = new ArrayList<>();

        MongoCursor<Document> cursor = hxAttendanceSetDao.getHxAttendanceSetList(hxAttendanceSetQuery);
        while (cursor.hasNext()) {
            list.add(getHxAttendanceSetModelByDocument(cursor.next()));
        }

        return list;
    }


    //获取签到信息总数量
    public Integer countHxAttendanceSet(HxAttendanceSetQuery hxAttendanceSetQuery) {
        return hxAttendanceSetDao.countHxAttendanceSet(hxAttendanceSetQuery);
    }

    //根据主键id获取签到信息
    public HxAttendanceSetModel getHxAttendanceSetById(String id) {

        HxAttendanceSetModel hxAttendanceSetModel = new HxAttendanceSetModel();
        MongoCursor<Document> cursor = hxAttendanceSetDao.getHxAttendanceSetById(id);
        while (cursor.hasNext()) {
            hxAttendanceSetModel = getHxAttendanceSetModelByDocument(cursor.next());
        }
        return hxAttendanceSetModel;
    }

    //根据主键id修改签到信息
    public void updateHxAttendanceSetById(HxAttendanceSetUpdateQuery hxAttendanceSetUpdateQuery) {
        hxAttendanceSetDao.updateHxAttendanceSetById(hxAttendanceSetUpdateQuery);
    }


    //处理签到信息转换对象
    private HxAttendanceSetModel getHxAttendanceSetModelByDocument(Document document) {

        HxAttendanceSetModel hxAttendanceSetModel = new HxAttendanceSetModel();
        hxAttendanceSetModel.setId(document.get("_id").toString());
        hxAttendanceSetModel.setUpdateTime((Date) document.get("updateTime"));

        JSONObject positionJson = JSONObject.fromObject(document.get("position"));

        //签到点名称
        if (positionJson != null && positionJson.containsKey("positionName")
                && !Objects.equals(null, positionJson.get("positionName"))) {
            hxAttendanceSetModel.setPositionName(positionJson.getString("positionName"));
        }

        //签到点地址
        if (positionJson != null && positionJson.containsKey("address")
                && !Objects.equals(null, positionJson.get("address"))) {
            hxAttendanceSetModel.setAddress(positionJson.getString("address"));
        }

        //签到点范围
        if (positionJson != null && positionJson.containsKey("offset")
                && !Objects.equals(null, positionJson.get("offset"))) {
            hxAttendanceSetModel.setOffset(positionJson.getString("offset"));
        }

        //签到点经度
        if (positionJson != null && positionJson.containsKey("lng")
                && !Objects.equals(null, positionJson.get("lng"))) {
            hxAttendanceSetModel.setLng(positionJson.getString("lng"));
        }


        //签到点纬度
        if (positionJson != null && positionJson.containsKey("lat")
                && !Objects.equals(null, positionJson.get("lat"))) {
            hxAttendanceSetModel.setLat(positionJson.getString("lat"));
        }

        JSONArray wifiJsonArray = JSONArray.fromObject(document.get("wifis"));
        log.info("wifiJsonArray:" + wifiJsonArray);
        if (wifiJsonArray != null && wifiJsonArray.size() > 0 && !wifiJsonArray.equals("null")) {

            List<HxWifiInfoModel> wifiInoList = new ArrayList<>();
            for (int i = 0; i < wifiJsonArray.size(); i++) {


                if (!Objects.isNull(wifiJsonArray.get(i)) && !"null".equals(wifiJsonArray.get(i).toString())) {

                    String str = wifiJsonArray.get(i).toString();
                    log.info("str:" + str);


                    JSONObject jsonObject = (JSONObject) wifiJsonArray.get(i);

                    HxWifiInfoModel hxWifiInfoModel = new HxWifiInfoModel();
                    hxWifiInfoModel.setSsid(jsonObject.getString("ssid"));
                    hxWifiInfoModel.setBssid(jsonObject.getString("bssid"));
                    if (jsonObject != null && jsonObject.containsKey("infoAddress")
                            && !Objects.equals(null, jsonObject.get("infoAddress"))) {
                        hxWifiInfoModel.setInfoAddress(jsonObject.getString("infoAddress"));
                    }


                    wifiInoList.add(hxWifiInfoModel);
                }

            }
            hxAttendanceSetModel.setWifiInoList(wifiInoList);

        }
        return hxAttendanceSetModel;
    }

}
