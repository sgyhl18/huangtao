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
 * 数据监控表 lanyayx_datamonitor
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Accessors(chain = true)
@TableName("lanyayx_datamonitor")
public class Datamonitor extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
	
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 音乐播放类型
    */
    private String type;
}
