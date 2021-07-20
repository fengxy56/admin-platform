package com.hongkun.service.apply.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongkun.bean.apply.HxSignTrackAuth;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.apply.HxSignTrackAuthMapper;
import com.hongkun.model.excel.hx.HxAppAuthExcel;
import com.hongkun.query.apply.HxSignTrackAuthDeleteQuery;
import com.hongkun.query.apply.HxSignTrackAuthQuery;
import com.hongkun.query.apply.HxSignTrackBathAuthQuery;
import com.hongkun.service.apply.HxSignTrackAuthService;
import com.hongkun.until.CodeGeneratedUtil;
import com.hongkun.model.vo.apply.HxSignTrackAuthVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName HxSignTrackAuthServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/16 17:07
 */
@Slf4j
@Service
public class HxSignTrackAuthServiceImpl implements HxSignTrackAuthService {

    @Autowired
    private HxSignTrackAuthMapper hxSignTrackAuthMapper;

    /**
     * 根据角色id获取授权用户列表
     *
     * @return
     */
    @Override
    @Transactional(transactionManager = "applyTransactionManager")
    public String saveBathHxSignTrackBathAuth(HxSignTrackBathAuthQuery hxSignTrackBathAuthQuery) {


        String message = "";

        String userNameExist = "";
        String errorUserName = "";

        int successCount = 0;
        int errorCount = 0;
        int existCount = 0;

        String accountName = UserSession.getUser().getAccountName();
        Date currentDate = new Date();

        List<HxAppAuthExcel> hxAppAuthExcelList = hxSignTrackBathAuthQuery.getList();

        if (hxAppAuthExcelList != null && hxAppAuthExcelList.size() > 0) {
            for (HxAppAuthExcel hxAppAuthExcel : hxAppAuthExcelList) {
                String authId = hxAppAuthExcel.getAuthId();
                if (StringUtils.isBlank(authId) || hxAppAuthExcel.getAuthType() == null) {
                    continue;
                }
                QueryWrapper<HxSignTrackAuth> userMenuQuery = new QueryWrapper<>();
                userMenuQuery.eq("authId", authId);
                userMenuQuery.eq("configureId", hxSignTrackBathAuthQuery.getConfigureId());
                userMenuQuery.eq("authType", hxAppAuthExcel.getAuthType());
                List<HxSignTrackAuth> hxSignTrackAuthList = hxSignTrackAuthMapper.selectList(userMenuQuery);
                //不存在，添加
                if (hxSignTrackAuthList == null || hxSignTrackAuthList.size() == 0) {

                    HxSignTrackAuth hxSignTrackAuth = new HxSignTrackAuth();
                    hxSignTrackAuth.setId(CodeGeneratedUtil.genUUID());
                    hxSignTrackAuth.setConfigureId(hxSignTrackBathAuthQuery.getConfigureId());
                    hxSignTrackAuth.setAuthId(authId);
                    hxSignTrackAuth.setAuthType(hxAppAuthExcel.getAuthType());
                    hxSignTrackAuth.setUpdateAccount(accountName);
                    hxSignTrackAuth.setCreateTime(currentDate);
                    hxSignTrackAuth.setUpdateTime(currentDate);
                    int count = hxSignTrackAuthMapper.insert(hxSignTrackAuth);
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

        message = "总共：" + hxSignTrackBathAuthQuery.getList().size() + ",成功：" + successCount
                + ",<br/>失败：" + errorCount + ",失败人员：" + errorUserName + ",已存在人员：" + userNameExist;

        return message;

    }


    //获取appid的用户角色授权列表
    public List<HxSignTrackAuthVO> getHxSignTrackAuthList(HxSignTrackAuthQuery hxSignTrackAuthQuery) {
        return hxSignTrackAuthMapper.getHxSignTrackAuthList(hxSignTrackAuthQuery);
    }

    ;


    //取消用户角色授权列表
    @Transactional(transactionManager = "applyTransactionManager")
    public Integer deleteHxSignTrackAuthList(HxSignTrackAuthDeleteQuery hxSignTrackAuthDeleteQuery) {

        List<HxSignTrackAuthQuery> authList = hxSignTrackAuthDeleteQuery.getAuthList();

        Integer deleteCout = 0;
        if (authList != null && authList.size() > 0) {
            for (HxSignTrackAuthQuery hxSignTrackAuthQuery : authList) {
                QueryWrapper<HxSignTrackAuth> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("authId", hxSignTrackAuthQuery.getAuthId());
                queryWrapper.eq("authType", hxSignTrackAuthQuery.getAuthType());

                deleteCout += hxSignTrackAuthMapper.delete(queryWrapper);
            }

        }

        if (authList != null && deleteCout != authList.size()) {
            throw new GlobalException(ResultEnum.PARAM_NOT_NULL);
        }


        return deleteCout;
    }

    ;


}
