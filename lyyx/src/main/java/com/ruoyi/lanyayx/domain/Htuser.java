package com.ruoyi.lanyayx.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * 黄桃买家汇总表 huangtao_htuser
 * 
 * @author yanghl
 * @date 2019-07-31
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Accessors(chain = true)
@TableName("huangtao_htuser")
public class Htuser extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")

    private String name;
    /**
    * 手机号
    */
    @ApiModelProperty(value = "手机号")
    private String telphone;
    /**
    * 地址
    */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
    * 金额
    */
    @ApiModelProperty(value = "金额")
    private String money;
    /**
    * 箱
    */
    @ApiModelProperty(value = "箱")
    private String box;
    /**
    * 物流单号
    */
    @ApiModelProperty(value = "物流单号")
    private String wuliudh;
    /**
    * 用户反馈
    */
    @ApiModelProperty(value = "用户反馈")
    private String suggestion;
}
