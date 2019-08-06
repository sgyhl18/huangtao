package com.ruoyi.lanyayx.service;

import com.ruoyi.lanyayx.domain.Device;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 设备 服务层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
public interface IDeviceService extends IService<Device> {
    /**
    * 列表查询
    */
    List<Device> list(Device device);
}
