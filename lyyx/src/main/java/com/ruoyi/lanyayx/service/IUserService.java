package com.ruoyi.lanyayx.service;

import com.ruoyi.lanyayx.domain.User;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.lanyayx.common.*;
/**
 * 用户 服务层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
public interface IUserService extends IService<User> {
    /**
    * 列表查询
    */
    List<User> list(User user);
    /**
     *用户登录
     */
    ServerResponse<User> login(String username,int type);

    /**
     * 用户注册
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);
    /**
     * 发送短信验证码接口
     * @param phoneNo
     * @return
     */
    ServerResponse<Map<String, Object>> sendSms(String phoneNo);

    /**
     * 手机号验证码验证
     * @param phoneNo
     * @param code
     * @param desMsg
     */
    ServerResponse<User> validateCode(String phoneNo,String code,String desMsg);
}
