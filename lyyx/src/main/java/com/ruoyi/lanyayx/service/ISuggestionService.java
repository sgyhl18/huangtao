package com.ruoyi.lanyayx.service;

import com.ruoyi.lanyayx.common.ServerResponse;
import com.ruoyi.lanyayx.domain.Suggestion;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户反馈 服务层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
public interface ISuggestionService extends IService<Suggestion> {
    /**
    * 列表查询
    */
    List<Suggestion> list(Suggestion suggestion);

    ServerResponse<String> addsu(Suggestion suggestion);
}
