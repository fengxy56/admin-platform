package com.hongkun.service.hx;

import com.hongkun.model.hx.HxUserUndoStatusModel;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusQuery;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusUpdateQuery;

import java.util.List;

/**
 * @ClassName HxUserUndoStatusService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:31
 */
public interface HxUserUndoStatusService {

    //获取总条数
    Integer countUndoList(HxUserUndoStatusQuery hxUserUndoStatusQuery);


    /**
     * 根据修改时间的(起时时间和结束时间)   分页查询待办信息
     *
     * @param
     */
    List<HxUserUndoStatusModel> findUndoList(HxUserUndoStatusQuery hxUserUndoStatusQuery);


    /**
     * 根据主键id把待办修改为已办或者删除已办
     *
     * @param
     */
    void updateHxUndoStatus(HxUserUndoStatusUpdateQuery hxUserUndoStatusUpdateQuery);

}
