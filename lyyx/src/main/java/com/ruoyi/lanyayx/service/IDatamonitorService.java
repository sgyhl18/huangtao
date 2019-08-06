package com.ruoyi.lanyayx.service;

import com.ruoyi.lanyayx.domain.Datamonitor;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 数据监控 服务层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
public interface IDatamonitorService extends IService<Datamonitor> {
    /**
    * 列表查询
    */
    List<Datamonitor> list(Datamonitor datamonitor);
}
