package com.ruoyi.lanyayx.controller;

import java.util.List;
import java.util.Arrays;
import com.ruoyi.common.base.Result;
import com.ruoyi.common.support.Convert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.lanyayx.domain.Suggestion;
import com.ruoyi.lanyayx.service.ISuggestionService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户反馈 控制层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@Slf4j
@Controller
@RequestMapping("/lanyayx/suggestion")
public class SuggestionController extends BaseController {
    private String prefix = "lanyayx/suggestion";
	
	private final ISuggestionService suggestionService;

	@Autowired
	public SuggestionController(ISuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}

	/**
	*  列表页
	*/
	@RequiresPermissions("lanyayx:suggestion:view")
	@GetMapping
	public String suggestion(){
	    return prefix + "/suggestion";
	}

	/**
	 * 新增页
	 */
	@GetMapping("/add")
	public String add(){
		return prefix + "/add";
	}

	/**
	 * 编辑页
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap){
		Suggestion suggestion = suggestionService.getById(id);
		mmap.put("suggestion", suggestion);
		return prefix + "/edit";
	}
	
	/**
	 * 查询用户反馈列表
	 */
	@RequiresPermissions("lanyayx:suggestion:list")
	@PostMapping("/list")
	@ResponseBody
	public Result list(Suggestion suggestion){
		startPage();
        List<Suggestion> list = suggestionService.list(suggestion);
		return Result.success(getDataTable(list));
	}

	/**
	 * 新增保存用户反馈
	 */
	@RequiresPermissions("lanyayx:suggestion:add")
	@Log(title = "用户反馈", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public Result addSave(Suggestion suggestion){
		suggestionService.save(suggestion);
		return Result.success();
	}

	/**
	 * 修改保存用户反馈
	 */
	@RequiresPermissions("lanyayx:suggestion:edit")
	@Log(title = "用户反馈", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public Result editSave(Suggestion suggestion){
		suggestionService.updateById(suggestion);
		return Result.success();
	}
	
	/**
	 * 删除用户反馈
	 */
	@RequiresPermissions("lanyayx:suggestion:remove")
	@Log(title = "用户反馈", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public Result remove(String ids){
		Long[] longIds = Convert.toLongArray(ids);
		suggestionService.removeByIds(Arrays.asList(longIds));
		return Result.success();
	}

	/**
	 * 导出用户反馈列表
	 */
	@RequiresPermissions("lanyayx:suggestion:export")
	@PostMapping("/export")
	@ResponseBody
	public Result export(Suggestion suggestion){
		List<Suggestion> list = suggestionService.list(suggestion);
		ExcelUtil<Suggestion> util = new ExcelUtil<>(Suggestion.class);
		util.exportExcel(list, "suggestion");
		return Result.success();
	}
}
