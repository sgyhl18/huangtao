package com.ruoyi.lanyayx.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.lanyayx.mapper.HtuserMapper;
import com.ruoyi.lanyayx.domain.Htuser;
import com.ruoyi.lanyayx.service.IHtuserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 黄桃买家汇总 服务层实现
 * 
 * @author yanghl
 * @date 2019-07-31
 */
@Slf4j
@Service
public class HtuserServiceImpl extends ServiceImpl<HtuserMapper, Htuser> implements IHtuserService {

    @Override
    public List<Htuser> list(Htuser htuser){
        LambdaQueryWrapper<Htuser> wrapper = new LambdaQueryWrapper<>();
        if(htuser != null){
            if(StringUtils.isNotEmpty(htuser.getId())){
                wrapper.eq(Htuser::getId, htuser.getId());
            }
            if(StringUtils.isNotEmpty(htuser.getName())){
                wrapper.eq(Htuser::getName, htuser.getName());
            }
            if(StringUtils.isNotEmpty(htuser.getTelphone())){
                wrapper.eq(Htuser::getTelphone, htuser.getTelphone());
            }
            if(StringUtils.isNotEmpty(htuser.getAddress())){
                wrapper.eq(Htuser::getAddress, htuser.getAddress());
            }
            if(StringUtils.isNotEmpty(htuser.getMoney())){
                wrapper.eq(Htuser::getMoney, htuser.getMoney());
            }
            if(StringUtils.isNotEmpty(htuser.getBox())){
                wrapper.eq(Htuser::getBox, htuser.getBox());
            }
            if(StringUtils.isNotEmpty(htuser.getWuliudh())){
                wrapper.eq(Htuser::getWuliudh, htuser.getWuliudh());
            }
            if(StringUtils.isNotEmpty(htuser.getSuggestion())){
                wrapper.eq(Htuser::getSuggestion, htuser.getSuggestion());
            }
            if(StringUtils.isNotEmpty(htuser.getCreateTime())){
                wrapper.eq(Htuser::getCreateTime, htuser.getCreateTime());
            }
            if(StringUtils.isNotEmpty(htuser.getUpdateTime())){
                wrapper.eq(Htuser::getUpdateTime, htuser.getUpdateTime());
            }
            if(StringUtils.isNotEmpty(htuser.getCreateBy())){
                wrapper.eq(Htuser::getCreateBy, htuser.getCreateBy());
            }
            if(StringUtils.isNotEmpty(htuser.getUpdateBy())){
                wrapper.eq(Htuser::getUpdateBy, htuser.getUpdateBy());
            }
            if(StringUtils.isNotEmpty(htuser.getRemark())){
                wrapper.eq(Htuser::getRemark, htuser.getRemark());
            }
        }
        return baseMapper.selectList(wrapper);
    }
}
