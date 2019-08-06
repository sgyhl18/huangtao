package com.ruoyi.lanyayx.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.lanyayx.mapper.DeviceMapper;
import com.ruoyi.lanyayx.domain.Device;
import com.ruoyi.lanyayx.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备 服务层实现
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@Slf4j
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Override
    public List<Device> list(Device device){
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        if(device != null){
            if(StringUtils.isNotEmpty(device.getId())){
                wrapper.eq(Device::getId, device.getId());
            }
            if(StringUtils.isNotEmpty(device.getUserId())){
                wrapper.eq(Device::getUserId, device.getUserId());
            }
            if(StringUtils.isNotEmpty(device.getDevicename())){
                wrapper.eq(Device::getDevicename, device.getDevicename());
            }
            if(StringUtils.isNotEmpty(device.getCreateTime())){
                wrapper.eq(Device::getCreateTime, device.getCreateTime());
            }
        }
        return baseMapper.selectList(wrapper);
    }
}
