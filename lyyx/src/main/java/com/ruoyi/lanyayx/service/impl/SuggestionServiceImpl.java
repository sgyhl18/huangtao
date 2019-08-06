package com.ruoyi.lanyayx.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.lanyayx.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.lanyayx.mapper.SuggestionMapper;
import com.ruoyi.lanyayx.domain.Suggestion;
import com.ruoyi.lanyayx.service.ISuggestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户反馈 服务层实现
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@Slf4j
@Service
public class SuggestionServiceImpl extends ServiceImpl<SuggestionMapper, Suggestion> implements ISuggestionService {
    @Autowired
    private SuggestionMapper suggestionMapper;
    @Override
    public  ServerResponse<String> addsu(Suggestion suggestion){
        int resultCount =suggestionMapper.insert(suggestion);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("反馈失败");
        }
        return ServerResponse.createBySuccessMessage("反馈成功");
    }
    @Override
    public List<Suggestion> list(Suggestion suggestion){
        LambdaQueryWrapper<Suggestion> wrapper = new LambdaQueryWrapper<>();
        if(suggestion != null){
            if(StringUtils.isNotEmpty(suggestion.getId())){
                wrapper.eq(Suggestion::getId, suggestion.getId());
            }
            if(StringUtils.isNotEmpty(suggestion.getUserId())){
                wrapper.eq(Suggestion::getUserId, suggestion.getUserId());
            }
            if(StringUtils.isNotEmpty(suggestion.getContent())){
                wrapper.eq(Suggestion::getContent, suggestion.getContent());
            }
            if(StringUtils.isNotEmpty(suggestion.getDeviceId())){
                wrapper.eq(Suggestion::getDeviceId, suggestion.getDeviceId());
            }
            if(StringUtils.isNotEmpty(suggestion.getCreateTime())){
                wrapper.eq(Suggestion::getCreateTime, suggestion.getCreateTime());
            }
        }
        return baseMapper.selectList(wrapper);
    }
}
