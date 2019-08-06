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
 * 设备表 lanyayx_device
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Accessors(chain = true)
@TableName("lanyayx_device")
public class Device extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
	
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 设备名称
    */
    private String devicename;
}
