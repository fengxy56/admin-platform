package com.hongkun.service.worko.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongkun.bean.worko.OaApprovalUsers;
import com.hongkun.bean.worko.Users;
import com.hongkun.bean.worko.UsersQuery;
import com.hongkun.consul.worko.WorkoConsul;
import com.hongkun.model.vo.page.PageInfoVO;
import com.hongkun.query.worko.OaApprovalUsersUpdateQuery;
import com.hongkun.service.worko.UsersService;
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
public class UsersServiceImpl implements UsersService {


    @Resource
    private WorkoConsul workoConsul;

    //分页查询同事圈用户
    public PageInfoVO<Users> getUsersList(UsersQuery usersQuery){

        PageInfoVO<Users> usersIPageList = new PageInfoVO<>();

        String result = workoConsul.getUsers(usersQuery);

        JSONObject jsonObj = JSONObject.parseObject(result);
        if (jsonObj.getInteger("code") == 1) {
            JSONObject jsonObject = (JSONObject) jsonObj.get("data");
            JSONArray jsonArray = (JSONArray) jsonObject.get("records");
            List<Users> workoList = JSONObject.parseArray(jsonArray.toString(), Users.class);

            String current = jsonObject.getString("current").replaceAll("\"","");
            long longCurrent = Long.parseLong(current);
            usersIPageList.setCurrent(longCurrent);
            usersIPageList.setSize(Integer.parseInt(jsonObject.getString("size")));
            usersIPageList.setTotal(Integer.parseInt(jsonObject.getString("total")));
            usersIPageList.setPages(Integer.parseInt(jsonObject.getString("pages")));
            usersIPageList.setRecords(workoList);
        }

        return usersIPageList;
    }

    public int saveOrUpdateOaApprovalUsers(OaApprovalUsersUpdateQuery oaApprovalUsersUpdateQuery){

        int count  = 0;
        String result = workoConsul.saveOrUpdateOaApprovalUsers(oaApprovalUsersUpdateQuery);

        JSONObject jsonObj = JSONObject.parseObject(result);
        if (jsonObj.getInteger("code") == 1) {
            count = jsonObj.getInteger("data");

        }
        return count;
    }




}
