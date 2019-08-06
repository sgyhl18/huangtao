package com.ruoyi.lanyayx.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * 用户表 lanyayx_user
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Accessors(chain = true)
@TableName("lanyayx_user")
public class User extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
	
    /**
    * 用户名
    */
    private String username;
    /**
    * 真实姓名
    */
    private String realName;
    /**
    * 密码
    */
    private String password;
    /**
    * 微信登录appid
    */
    private String appid;
    /**
    * qq登录token
    */
    private String qqtoken;
    /**
    * 手机号
    */
    private String phone;
    /**
    * 昵称
    */
    private String nickName;
    /**
    * 性别
    */
    private Integer sex;
    /**
    * 生日
    */
    private Date birthDay;
}
