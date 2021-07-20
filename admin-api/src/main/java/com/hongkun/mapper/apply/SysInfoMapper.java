package com.hongkun.mapper.apply;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongkun.bean.apply.SysInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SysInfoMapper extends BaseMapper<SysInfo> {

}
