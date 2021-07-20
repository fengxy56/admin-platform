package com.hongkun.service.hxgol.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongkun.bean.hx.HxAppAuth;
import com.hongkun.bean.hx.HxAppAuthHistory;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.hxgol.HxAppAuthHistoryMapper;
import com.hongkun.mapper.hxgol.HxAppAuthMapper;
import com.hongkun.model.excel.hx.HxAppAuthExcel;
import com.hongkun.query.hx.AppUserRoleAuthQuery;
import com.hongkun.query.hx.HxAppUserBathAuthQuery;
import com.hongkun.query.hx.HxUserRoleAppAuthQuery;
import com.hongkun.service.hxgol.HxAppAuthService;
import com.hongkun.until.CodeGeneratedUtil;
import com.hongkun.model.vo.hx.AppUserRoleAuthVO;
import com.hongkun.model.vo.hx.UserRoleAppVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName HxAppAuthServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/28 16:08
 */
@Component
public class HxAppAuthServiceImpl implements HxAppAuthService {


    @Autowired
    private HxAppAuthMapper hxAppAuthMapper;

    @Autowired
    private HxAppAuthHistoryMapper hxAppAuthHistoryMapper;


    /**
     * 根据角色id获取授权用户列表
     *
     * @return
     */
    @Override
    @Transactional(transactionManager = "hxgolTransactionManager")
    public void saveHxUserRoleAppAuth(HxUserRoleAppAuthQuery hxUserRoleAppAuthQuery) {

        String accountName = UserSession.getUser().getAccountName();
        Date currentDate = new Date();


        //获取页面传入的菜单id
        List<String> menuIdList = hxUserRoleAppAuthQuery.getMenuIdList();

        //2、首先判断版本id为空，或者不为空并且不是编制中状态,则重新添加角色授权表和版本表
        if (menuIdList != null && menuIdList.size() > 0) {

            QueryWrapper<HxAppAuth> userMenuQuery = new QueryWrapper<>();
            userMenuQuery.eq("authId", hxUserRoleAppAuthQuery.getAuthId());
            userMenuQuery.eq("authType", hxUserRoleAppAuthQuery.getAuthType());
            List<HxAppAuth> hxAppAuthList = hxAppAuthMapper.selectList(userMenuQuery);

            for (HxAppAuth hxAppAuth : hxAppAuthList) {
                HxAppAuthHistory hxAppAuthHistory = new HxAppAuthHistory();
                BeanUtils.copyProperties(hxAppAuth, hxAppAuthHistory);
                hxAppAuthHistory.setUuid(CodeGeneratedUtil.genUUID());
                hxAppAuthHistoryMapper.insert(hxAppAuthHistory);
                //删除授权表
                hxAppAuthMapper.deleteById(hxAppAuth.getId());
            }

            //重新保存角色授权表
            for (String menuId : menuIdList) {
                HxAppAuth hxAppAuth = new HxAppAuth();
                hxAppAuth.setId(CodeGeneratedUtil.genUUID());
                hxAppAuth.setAppId(Integer.parseInt(menuId));
                hxAppAuth.setAuthId(hxUserRoleAppAuthQuery.getAuthId());
                hxAppAuth.setAuthType(hxUserRoleAppAuthQuery.getAuthType());
                hxAppAuth.setUpdateAccount(accountName);
                hxAppAuth.setCreateTime(currentDate);
                hxAppAuth.setUpdateTime(currentDate);
                hxAppAuthMapper.insert(hxAppAuth);
            }

        } else {//如果存在版本,并且是编制中,直接修改
            throw new GlobalException(ResultEnum.SAVE_USERROLE_APP_NOTEXIST);
        }

    }

    /**
     * 根据角色id获取授权用户列表
     *
     * @return
     */
    @Override
    @Transactional(transactionManager = "hxgolTransactionManager")
    public String saveBathHxUserRoleAppAuth(HxAppUserBathAuthQuery hxAppUserBathAuthQuery) {


        String message = "";

        String userNameExist = "";
        String errorUserName = "";

        int successCount = 0;
        int errorCount = 0;
        int existCount = 0;

        String accountName = UserSession.getUser().getAccountName();
        Date currentDate = new Date();

        List<HxAppAuthExcel> hxAppAuthExcelList = hxAppUserBathAuthQuery.getList();

        if (hxAppAuthExcelList != null && hxAppAuthExcelList.size() > 0) {
            for (HxAppAuthExcel hxAppAuthExcel : hxAppAuthExcelList) {
                String authId = hxAppAuthExcel.getAuthId();
                if (StringUtils.isBlank(authId) || hxAppAuthExcel.getAuthType() == null) {
                    continue;
                }
                QueryWrapper<HxAppAuth> userMenuQuery = new QueryWrapper<>();
                userMenuQuery.eq("authId", authId);
                userMenuQuery.eq("appId", hxAppUserBathAuthQuery.getAppId());
                userMenuQuery.eq("authType", hxAppAuthExcel.getAuthType());
                List<HxAppAuth> hxAppAuthList = hxAppAuthMapper.selectList(userMenuQuery);
                //不存在，添加
                if (hxAppAuthList == null || hxAppAuthList.size() == 0) {

                    HxAppAuth hxAppAuth = new HxAppAuth();
                    hxAppAuth.setId(CodeGeneratedUtil.genUUID());
                    hxAppAuth.setAppId(hxAppUserBathAuthQuery.getAppId());
                    hxAppAuth.setAuthId(authId);
                    hxAppAuth.setAuthType(hxAppAuthExcel.getAuthType());
                    hxAppAuth.setUpdateAccount(accountName);
                    hxAppAuth.setCreateTime(currentDate);
                    hxAppAuth.setUpdateTime(currentDate);
                    int count = hxAppAuthMapper.insert(hxAppAuth);
                    if (count > 0) {
                        successCount++;
                    } else {
                        errorCount++;
                        errorUserName = authId + "(" + hxAppAuthExcel.getAuthName() + "),";
                    }

                } else {
                    existCount++;
                    userNameExist = userNameExist + "," + authId + "(" + hxAppAuthExcel.getAuthName() + "),";
                }


            }
        }

        message = "总共：" + hxAppUserBathAuthQuery.getList().size() + ",成功：" + successCount
                + ",<br/>失败：" + errorCount + ",失败人员：" + errorUserName + ",已存在人员：" + userNameExist;

        return message;

    }


    //获取appid的用户角色授权列表
    public List<AppUserRoleAuthVO> getAppUserRoleAuthList(AppUserRoleAuthQuery appUserRoleAuthQuery) {
        return hxAppAuthMapper.getAppUserRoleAuthList(appUserRoleAuthQuery);
    }


    //获取用户角色授权列表
    public List<UserRoleAppVO> getHxUserRoleAppTree(String authType, String authId) {

        List<UserRoleAppVO> rootList = hxAppAuthMapper.getUserRoleAppList(authType, authId);


        // 最后的结果
        List<UserRoleAppVO> finalList = new ArrayList<>();

        // 先找到所有的一级菜单
        for (int i = 0; i < rootList.size(); i++) {
            // 一级菜单parentId为-1
            if ("-1".equals(rootList.get(i).getParentId())) {
                finalList.add(rootList.get(i));
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (UserRoleAppVO userRoleAppVO : finalList) {
            userRoleAppVO.setChildren(this.getChildByHxUserRoleAppVO(userRoleAppVO.getMenuId(), rootList));
        }


        return finalList;

    }


    /**
     * 递归查找子菜单
     *
     * @param appId    当前菜单id
     * @param rootList 要查找的列表
     * @return
     */
    private List<UserRoleAppVO> getChildByHxUserRoleAppVO(String appId, List<UserRoleAppVO> rootList) {
        // 子菜单
        List<UserRoleAppVO> childList = new ArrayList<>();

        for (UserRoleAppVO userRoleAppVO : rootList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(userRoleAppVO.getParentId())) {
                if (userRoleAppVO.getParentId().equals(appId)) {
                    childList.add(userRoleAppVO);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (UserRoleAppVO userRoleAppVO : childList) {// 没有url子菜单还有子菜单
            // 递归
            userRoleAppVO.setChildren(this.getChildByHxUserRoleAppVO(userRoleAppVO.getMenuId(), rootList));
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


//
//    public IPage<HxAppAuth> getHxCustAppInfoList(HxCustAppInfoQuery hxCustAppInfoQuery){
//
//        Page<HxAppAuth> page=new Page<>(hxCustAppInfoQuery.getPageNo(),hxCustAppInfoQuery.getPageSize());
//
//        QueryWrapper<HxCustAppInfo> queryWrapper = new QueryWrapper<>();
//        if(StringUtils.isNotBlank(hxCustAppInfoQuery.getAppId() ) ){
//            queryWrapper.eq("appId",hxCustAppInfoQuery.getAppId());
//        }
//
//        if(StringUtils.isNotBlank(hxCustAppInfoQuery.getAppName() ) ){
//            queryWrapper.like("appName",hxCustAppInfoQuery.getAppName());
//        }
////        queryWrapper.eq("orgInfoId",configInfo.getHxOrgInfoId());
//        IPage pageList = hxAppAuthMapper.selectPage(page,queryWrapper);
//
//        return pageList;
//    }


}
