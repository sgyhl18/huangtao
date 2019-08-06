package com.ruoyi.lanyayx.service;

import com.ruoyi.lanyayx.domain.Htuser;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 黄桃买家汇总 服务层
 * 
 * @author yanghl
 * @date 2019-07-31
 */
public interface IHtuserService extends IService<Htuser> {
    /**
    * 列表查询
    */
    List<Htuser> list(Htuser htuser);
}
