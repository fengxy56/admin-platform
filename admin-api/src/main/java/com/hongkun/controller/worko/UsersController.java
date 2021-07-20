package com.hongkun.controller.worko;


import com.hongkun.bean.worko.OaApprovalUsers;
import com.hongkun.bean.worko.Users;
import com.hongkun.bean.worko.UsersQuery;
import com.hongkun.exception.GlobalException;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.page.PageInfoVO;
import com.hongkun.query.worko.OaApprovalUsersUpdateQuery;
import com.hongkun.service.worko.UsersService;
import com.hongkun.until.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName WorkoController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/19 16:25
 */
@RestController
@RequestMapping("/users")
@Api(tags = "同事圈-用户信息")
@Slf4j
public class UsersController {


    @Resource
    private UsersService usersService;

    /**
     * @return
     */
    @ApiOperation(value = "分页查询同事圈-用户", notes = "")
    @PostMapping(value = "/getUsersList")
    public ResultVO<Users> getWorkoList(@RequestBody UsersQuery usersQuery) {
        PageInfoVO<Users> list = usersService.getUsersList(usersQuery);

        return ResponseUtil.success(list);
    }

    /**
     * @return
     */
    @ApiOperation(value = "oa审批禁用同事圈用户", notes = "")
    @PostMapping(value = "/saveOrUpdateOaApprovalUsers")
    public ResultVO<Integer> saveOrUpdateOaApprovalUsers(@RequestBody OaApprovalUsersUpdateQuery oaApprovalUsersUpdateQuery) {
        int count = usersService.saveOrUpdateOaApprovalUsers(oaApprovalUsersUpdateQuery);
        if(oaApprovalUsersUpdateQuery != null && StringUtils.isBlank(oaApprovalUsersUpdateQuery.getApprovalReason())){
            throw new GlobalException(0,"启用/禁用原因不能为空");
        }

        return ResponseUtil.success(count);
    }


}
