package com.ruoyi.lanyayx.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.lanyayx.mapper.DatamonitorMapper;
import com.ruoyi.lanyayx.domain.Datamonitor;
import com.ruoyi.lanyayx.service.IDatamonitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据监控 服务层实现
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@Slf4j
@Service
public class DatamonitorServiceImpl extends ServiceImpl<DatamonitorMapper, Datamonitor> implements IDatamonitorService {

    @Override
    public List<Datamonitor> list(Datamonitor datamonitor){
        LambdaQueryWrapper<Datamonitor> wrapper = new LambdaQueryWrapper<>();
        if(datamonitor != null){
            if(StringUtils.isNotEmpty(datamonitor.getId())){
                wrapper.eq(Datamonitor::getId, datamonitor.getId());
            }
            if(StringUtils.isNotEmpty(datamonitor.getUserId())){
                wrapper.eq(Datamonitor::getUserId, datamonitor.getUserId());
            }
            if(StringUtils.isNotEmpty(datamonitor.getType())){
                wrapper.eq(Datamonitor::getType, datamonitor.getType());
            }
            if(StringUtils.isNotEmpty(datamonitor.getCreateTime())){
                wrapper.eq(Datamonitor::getCreateTime, datamonitor.getCreateTime());
            }
        }
        return baseMapper.selectList(wrapper);
    }
}
