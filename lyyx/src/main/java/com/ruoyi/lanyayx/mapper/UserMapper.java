package com.ruoyi.lanyayx.mapper;

import com.ruoyi.lanyayx.domain.User;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 数据层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
public interface UserMapper extends BaseMapper<User> {
    int checkUsername(String username);
    int checkAPPID(String appid);
    int checkQQTOKEN(String qqtoken);
    int getUserInfoByPhoneNo(String phone);
    User selectLogin(@Param("type") int type,@Param("username") String username);
}