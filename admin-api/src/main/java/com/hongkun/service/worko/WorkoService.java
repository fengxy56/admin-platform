package com.hongkun.service.worko;

import com.hongkun.bean.worko.Worko;
import com.hongkun.query.worko.WorkoQuery;
import com.hongkun.query.worko.WorkoUpdateQuery;
import com.hongkun.model.vo.page.PageInfoVO;

/**
 * @ClassName WorkoService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/19 18:45
 */
public interface WorkoService {


    //分页查询同事圈
    PageInfoVO<Worko> getWorkoList(WorkoQuery workoQuery);

    //分页查询同事圈
    Integer updateWorko(WorkoUpdateQuery workoUpdateQuery);
}
