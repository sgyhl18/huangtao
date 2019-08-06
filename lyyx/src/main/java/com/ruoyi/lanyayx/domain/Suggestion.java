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
 * 用户反馈表 lanyayx_suggestion
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Accessors(chain = true)
@TableName("lanyayx_suggestion")
public class Suggestion extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
	
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 反馈内容
    */
    private String content;
    /**
    * 反馈设备id
    */
    private Integer deviceId;
}
