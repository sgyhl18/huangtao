package com.ruoyi.lanyayx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloopen.rest.sdk.CCPRestSDK;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.lanyayx.common.Const;
import com.ruoyi.lanyayx.common.ServerResponse;
import com.ruoyi.lanyayx.domain.User;
import com.ruoyi.lanyayx.mapper.UserMapper;
import com.ruoyi.lanyayx.service.IUserService;
import com.ruoyi.lanyayx.service.RedisService;
import com.ruoyi.lanyayx.util.EncryptUtil;
import com.ruoyi.lanyayx.util.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 服务层实现
 *
 * @author yanghl
 * @date 2019-07-23
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    public int sendccp(String phoneNo, String code) {
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount("8aaf07086bfdc83a016c21da335815b0", "dbf7f0d94c5644c2886f41007a568a05");// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId("8aaf07086bfdc83a016c21da33b115b7");// 初始化应用ID
        result = restAPI.sendTemplateSMS(phoneNo, "459067", new String[]{code});
        System.out.println("SDKTestSendTemplateSMS result=" + result);
        if ("000000".equals(result.get("statusCode"))) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public ServerResponse<Map<String, Object>> sendSms(String phoneNo) {
        if (!PhoneUtils.isPhone(phoneNo)) {
            return ServerResponse.createByErrorMessage("手机号码格式输入有误！");
        }
        //获取随机6位数
        Integer code = (int) ((Math.random() * 9 + 1) * 100000);
        int res=sendccp(phoneNo,code+"");
        if (res==1) {
            //短信发送成功，利用手机号码和短信验证码进行加密存储
            String baseMsg = phoneNo + "," + code.toString();
            byte[] encrypted = EncryptUtil.AES_CBC_Encrypt(baseMsg.getBytes(), baseMsg.getBytes(), "abcdefghijklmnop".getBytes());
            String desMsg = EncryptUtil.byteToHexString(encrypted);
            //加密数据存储到redis缓存
            if (redisService.get(desMsg) != null) {
                redisService.remove(desMsg);
            }
            redisService.set(desMsg, desMsg);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("desMsg", desMsg);
            return ServerResponse.createBySuccess("验证码发送成功",map);
        } else {
            return   ServerResponse.createByErrorMessage("验证码发送失败");
        }

    }
    @Override
    public ServerResponse<User>  validateCode(String phoneNo, String code, String desMsg) {
        if (!PhoneUtils.isPhone(phoneNo)) {
            return ServerResponse.createByErrorMessage("手机号码格式输入有误！");
        }
        //拼接手机号码和验证码进行加密得到加密字符串
        String baseMsg = phoneNo + "," + code;
        byte[] encrypted = EncryptUtil.AES_CBC_Encrypt(baseMsg.getBytes(), baseMsg.getBytes(), "abcdefghijklmnop".getBytes());
        String baseDesMsg = EncryptUtil.byteToHexString(encrypted);
        //从redis中获取该加密字符串
        String value = (String) redisService.get(desMsg);
        if (value != null&&value.equalsIgnoreCase(baseDesMsg)) {
            ServerResponse<User> response=login(phoneNo,3);

            //验证当前手机号码是否已经被注册
         /*   int resultCount = userMapper.getUserInfoByPhoneNo(phoneNo);
            if (resultCount > 0) {
                return ServerResponse.createByErrorMessage("该手机号已经注册！");
            }*/
            return response;
        }
        return ServerResponse.createByErrorMessage("验证失败，请重新发送验证码!");
    }
    @Override
    public ServerResponse<User> login(String username,int type) {
        User user =null;
        //加密
       /* String baseMsg=password;
        byte[] encrypted = EncryptUtil.AES_CBC_Encrypt(baseMsg.getBytes(), baseMsg.getBytes(), "abcdefghijklmnop".getBytes());
        String baseDesMsg = EncryptUtil.byteToHexString(encrypted);*/
       /* if(type==1){
            int resultCount = userMapper.checkUsername(username);
            if (resultCount == 0) {
                return ServerResponse.createByErrorMessage("用户名不存在");
            }
        }*/
        if(type==1){
            int resultCount = userMapper.checkAPPID(username);
            if (resultCount == 0) {
                User userr =new User();
                userr.setAppid(username);
                register(userr);
               // return ServerResponse.createByErrorMessage("appid不存在");
            }
        }
        if(type==2){
            int resultCount = userMapper.checkQQTOKEN(username);
            if (resultCount == 0) {
                User userr =new User();
                userr.setQqtoken(username);
                register(userr);
               // return ServerResponse.createByErrorMessage("qqtoken不存在");
            }
        }
        if(type==3){
            int resultCount = userMapper.getUserInfoByPhoneNo(username);
            if (resultCount == 0) {
                User userr =new User();
                userr.setPhone(username);
                register(userr);
                //return ServerResponse.createByErrorMessage("手机号不存在");
            }
        }
        user = userMapper.selectLogin(type,username);
      /*  user = userMapper.selectLogin(type,username, baseDesMsg);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
*/
        //user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
         validResponse = this.checkValid(user.getAppid(),Const.APPID);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
         validResponse = this.checkValid(user.getQqtoken(),Const.QQTOKEN);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
         validResponse = this.checkValid(user.getPhone(),Const.PHONE);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
       /* // user.setRole(Const.Role.ROLE_CUSTOMER);
        //加密
        String baseMsg=user.getPassword();
        byte[] encrypted = EncryptUtil.AES_CBC_Encrypt(baseMsg.getBytes(), baseMsg.getBytes(), "abcdefghijklmnop".getBytes());
        String baseDesMsg = EncryptUtil.byteToHexString(encrypted);
        user.setPassword(baseDesMsg);*/
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.APPID.equals(type)){
                int resultCount = userMapper.checkAPPID(str);
                if(resultCount > 0 ){
                    return ServerResponse.createByErrorMessage("appid已存在");
                }
            }
            if(Const.QQTOKEN.equals(type)){
                int resultCount = userMapper.checkQQTOKEN(str);
                if(resultCount > 0 ){
                    return ServerResponse.createByErrorMessage("qqtoken已存在");
                }
            }
            if(Const.PHONE.equals(type)){
                int resultCount = userMapper.getUserInfoByPhoneNo(str);
                if(resultCount > 0 ){
                    return ServerResponse.createByErrorMessage("手机号已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public List<User> list(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (user != null) {
            if (StringUtils.isNotEmpty(user.getId())) {
                wrapper.eq(User::getId, user.getId());
            }
            if (StringUtils.isNotEmpty(user.getUsername())) {
                wrapper.eq(User::getUsername, user.getUsername());
            }
            if (StringUtils.isNotEmpty(user.getRealName())) {
                wrapper.eq(User::getRealName, user.getRealName());
            }
            if (StringUtils.isNotEmpty(user.getPassword())) {
                wrapper.eq(User::getPassword, user.getPassword());
            }
            if (StringUtils.isNotEmpty(user.getAppid())) {
                wrapper.eq(User::getAppid, user.getAppid());
            }
            if (StringUtils.isNotEmpty(user.getQqtoken())) {
                wrapper.eq(User::getQqtoken, user.getQqtoken());
            }
            if (StringUtils.isNotEmpty(user.getPhone())) {
                wrapper.eq(User::getPhone, user.getPhone());
            }
            if (StringUtils.isNotEmpty(user.getNickName())) {
                wrapper.eq(User::getNickName, user.getNickName());
            }
            if (StringUtils.isNotEmpty(user.getSex())) {
                wrapper.eq(User::getSex, user.getSex());
            }
            if (StringUtils.isNotEmpty(user.getBirthDay())) {
                wrapper.eq(User::getBirthDay, user.getBirthDay());
            }
            if (StringUtils.isNotEmpty(user.getCreateTime())) {
                wrapper.eq(User::getCreateTime, user.getCreateTime());
            }
            if (StringUtils.isNotEmpty(user.getUpdateTime())) {
                wrapper.eq(User::getUpdateTime, user.getUpdateTime());
            }
        }
        return baseMapper.selectList(wrapper);
    }
}
