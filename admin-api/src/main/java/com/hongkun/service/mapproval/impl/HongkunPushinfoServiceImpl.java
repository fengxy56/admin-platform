package com.hongkun.service.mapproval.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.mapproval.HongkunOutsupportinfo;
import com.hongkun.bean.mapproval.HongkunPushinfo;
import com.hongkun.mapper.mapproval.HongkunOutsupportMapper;
import com.hongkun.mapper.mapproval.HongkunPushinfoMapper;
import com.hongkun.model.vo.hx.HongkunPushinfoVO;
import com.hongkun.mongodb.dao.HxUserUndoStatusDao;
import com.hongkun.query.mapproval.HongkunPushinfoQuery;
import com.hongkun.query.mapproval.HxPushInfoUpdateQuery;
import com.hongkun.service.mapproval.HongkunPushinfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HongkunPushinfoServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 14:16
 */
@Service
public class HongkunPushinfoServiceImpl implements HongkunPushinfoService {

    @Resource
    private HongkunPushinfoMapper hongkunPushinfoMapper;


    @Resource
    private HongkunOutsupportMapper hongkunOutsupportMapper;

    @Resource
    private HxUserUndoStatusDao hxUserUndoStatusDao;


    //获取移动审批信息
    public IPage<HongkunPushinfoVO> getHongkunPushinfoList(HongkunPushinfoQuery hongkunPushinfoQuery) {

        Page<HongkunPushinfo> page = new Page<>(hongkunPushinfoQuery.getPageNo(), hongkunPushinfoQuery.getPageSize());

        QueryWrapper<HongkunPushinfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("positionName",hxSignTrackConfigureQuery.getPositionName());

        if (StringUtils.isNotBlank(hongkunPushinfoQuery.getAppId())) {
            queryWrapper.eq("appId", hongkunPushinfoQuery.getAppId());
        }
        if (StringUtils.isNotBlank(hongkunPushinfoQuery.getUserId())) {
            queryWrapper.eq("userId", hongkunPushinfoQuery.getUserId());
        }
        if (StringUtils.isNotBlank(hongkunPushinfoQuery.getMsgTitle())) {
            queryWrapper.like("msgTitle", hongkunPushinfoQuery.getMsgTitle());
        }
        if (StringUtils.isNotBlank(hongkunPushinfoQuery.getRequestId())) {
            queryWrapper.like("requestId", hongkunPushinfoQuery.getRequestId());
        }


        if (StringUtils.isNotBlank(hongkunPushinfoQuery.getIfTodo())) {
            queryWrapper.like("ifTodo", hongkunPushinfoQuery.getIfTodo());
        }

        if (StringUtils.isNotBlank(hongkunPushinfoQuery.getHxMsgId())) {
            queryWrapper.like("hx_msgid", hongkunPushinfoQuery.getHxMsgId());
        }
        queryWrapper.orderByDesc("id");
        IPage<HongkunPushinfo> pageList = hongkunPushinfoMapper.selectPage(page, queryWrapper);


        IPage<HongkunPushinfoVO> pageVOList = new Page<>();
        BeanUtils.copyProperties(pageList,pageVOList);

        List<HongkunPushinfo> list = pageList.getRecords();

        List<HongkunPushinfoVO> hongkunPushinfoVOList = new ArrayList<>();
        if(list !=null && list.size() >0){
            for(HongkunPushinfo hongkunPushinfo : list){
                HongkunPushinfoVO hongkunPushinfoVO = new HongkunPushinfoVO();
                BeanUtils.copyProperties(hongkunPushinfo,hongkunPushinfoVO);

                List<HongkunOutsupportinfo> hongkunOutsupportinfoList = this.getHongkunOutsupportinfoList(hongkunPushinfo.getAppId());
                if(hongkunOutsupportinfoList !=null && hongkunOutsupportinfoList.size() >0){
                    hongkunPushinfoVO.setAppName(hongkunOutsupportinfoList.get(0).getJoinAppName());
                }
                hongkunPushinfoVOList.add(hongkunPushinfoVO);


            }
            pageVOList.setRecords(hongkunPushinfoVOList);
        }

        return pageVOList;
    }


    //修改移动审批信息
    public int updateHongkunPushinfo(HxPushInfoUpdateQuery hxPushInfoUpdateQuery) {


        HongkunPushinfo hongkunPushinfo = new HongkunPushinfo();
        hongkunPushinfo.setId(hxPushInfoUpdateQuery.getId());
        hongkunPushinfo.setIfTodo(hxPushInfoUpdateQuery.getIfTodo());
        int count = hongkunPushinfoMapper.updateById(hongkunPushinfo);

        String todoStatus = "undo";
        if (hxPushInfoUpdateQuery.getIfTodo() == 1) {//待办
            todoStatus = "undo";
        } else if (hxPushInfoUpdateQuery.getIfTodo() == 0) {//已办
            todoStatus = "done";
        }

        if (hxPushInfoUpdateQuery.getDeleteStatus() == 1) {
            hxUserUndoStatusDao.updateUndoStatusByAccountAndMsyId(hxPushInfoUpdateQuery.getHxMsgId(), hxPushInfoUpdateQuery.getUserId(), todoStatus, true);
        } else if (hxPushInfoUpdateQuery.getIfTodo() == 0) {//修改未已办
            hxUserUndoStatusDao.updateUndoStatusByAccountAndMsyId(hxPushInfoUpdateQuery.getHxMsgId(), hxPushInfoUpdateQuery.getUserId(), todoStatus, false);
        } else {
            hxUserUndoStatusDao.updateUndoStatusByAccountAndMsyId(hxPushInfoUpdateQuery.getHxMsgId(), hxPushInfoUpdateQuery.getUserId(), todoStatus, false);
        }
        return count;
    }


    public List<HongkunOutsupportinfo> getHongkunOutsupportinfoList(String appId){
        QueryWrapper<HongkunOutsupportinfo> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(appId)){
            queryWrapper.eq("join_appId",appId);
        }
        List<HongkunOutsupportinfo> list = hongkunOutsupportMapper.selectList(queryWrapper);
        return list;
    }


}
