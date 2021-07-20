package com.hongkun.service.worko.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongkun.bean.worko.Worko;
import com.hongkun.query.worko.WorkoQuery;
import com.hongkun.query.worko.WorkoUpdateQuery;
import com.hongkun.consul.worko.WorkoConsul;
import com.hongkun.model.vo.page.PageInfoVO;
import com.hongkun.service.worko.WorkoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName WorkoServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/19 18:46
 */
@Service
public class WorkoServiceImpl implements WorkoService {


    @Resource
    private WorkoConsul workoConsul;

    //分页查询同事圈
    public PageInfoVO<Worko> getWorkoList(WorkoQuery workoQuery){
        PageInfoVO<Worko> workoIPageList = new PageInfoVO<>();
        String result = workoConsul.getWorkoList(workoQuery);

        JSONObject jsonObj = JSONObject.parseObject(result);
        if (jsonObj.getInteger("code") == 1) {
            JSONObject jsonObject = (JSONObject) jsonObj.get("data");
            JSONArray jsonArray = (JSONArray) jsonObject.get("records");
            List<Worko> workoList = JSONObject.parseArray(jsonArray.toString(), Worko.class);
            String current = jsonObject.getString("current").replaceAll("\"","");
            long longCurrent = Long.parseLong(current);
            workoIPageList.setCurrent(longCurrent);
            workoIPageList.setSize(Integer.parseInt(jsonObject.getString("size")));
            workoIPageList.setTotal(Integer.parseInt(jsonObject.getString("total")));
            workoIPageList.setPages(Integer.parseInt(jsonObject.getString("pages")));
            workoIPageList.setRecords(workoList);
        }

        return workoIPageList;
    }

    //分页查询同事圈
    public Integer updateWorko(WorkoUpdateQuery workoUpdateQuery){
        Integer count  = 0 ;

        String result = workoConsul.updateWorko(workoUpdateQuery);

        JSONObject jsonObj = JSONObject.parseObject(result);
        if (jsonObj.getInteger("code") == 1) {
            count = jsonObj.getInteger("data");
        }

        return count ;
    }



}
