package com.hongkun.service.apply;

import com.hongkun.query.apply.SysAuthMenuQuery;
import com.hongkun.model.vo.apply.HxOrgVO;

import java.util.List;

/**
 * @ClassName HxOrgService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/30 7:18
 */
public interface HxOrgService {

    //获取组织数结构
    List<HxOrgVO> getHxOrgTreeList(SysAuthMenuQuery sysAuthMenuQuery);

}
