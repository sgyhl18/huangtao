package com.ruoyi.lanyayx.controller;

import java.util.List;
import java.util.Arrays;
import com.ruoyi.common.base.Result;
import com.ruoyi.common.support.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.lanyayx.domain.Htuser;
import com.ruoyi.lanyayx.service.IHtuserService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 黄桃买家汇总 控制层
 * 
 * @author yanghl
 * @date 2019-07-31
 */
@Slf4j
@Controller
@RequestMapping("/lanyayx/htuser")
@Api(value = "用户controller", description = "黄桃买家相关接口", tags = {"HtuserController"})
public class HtuserController extends BaseController {
    private String prefix = "lanyayx/htuser";
	
	private final IHtuserService htuserService;

	@Autowired
	public HtuserController(IHtuserService htuserService) {
		this.htuserService = htuserService;
	}

	/**
	*  列表页
	*/
	@RequiresPermissions("lanyayx:htuser:view")
	@GetMapping
	public String htuser(){
	    return prefix + "/htuser";
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
		Htuser htuser = htuserService.getById(id);
		mmap.put("htuser", htuser);
		return prefix + "/edit";
	}
	
	/**
	 * 查询黄桃买家汇总列表
	 */
	@RequiresPermissions("lanyayx:htuser:list")
	@PostMapping("/list")
	@ResponseBody
	public Result list(Htuser htuser){
		startPage();
        List<Htuser> list = htuserService.list(htuser);
		return Result.success(getDataTable(list));
	}

	/**
	 * 新增保存黄桃买家汇总
	 */
	@ApiOperation(value = "新增保存黄桃买家汇总")
	@RequiresPermissions("lanyayx:htuser:add")
	@Log(title = "黄桃买家汇总", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public Result addSave(Htuser htuser){
		htuserService.save(htuser);
		return Result.success();
	}

	/**
	 * 修改保存黄桃买家汇总
	 */
	@ApiOperation(value = "修改保存黄桃买家汇总")
	@RequiresPermissions("lanyayx:htuser:edit")
	@Log(title = "黄桃买家汇总", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public Result editSave(Htuser htuser){
		htuserService.updateById(htuser);
		return Result.success();
	}
	
	/**
	 * 删除黄桃买家汇总
	 */
	@ApiOperation(value = "删除黄桃买家汇总")
	@RequiresPermissions("lanyayx:htuser:remove")
	@Log(title = "黄桃买家汇总", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public Result remove(String ids){
		Long[] longIds = Convert.toLongArray(ids);
		htuserService.removeByIds(Arrays.asList(longIds));
		return Result.success();
	}

	/**
	 * 导出黄桃买家汇总列表
	 */
	@RequiresPermissions("lanyayx:htuser:export")
	@PostMapping("/export")
	@ResponseBody
	public Result export(Htuser htuser){
		List<Htuser> list = htuserService.list(htuser);
		ExcelUtil<Htuser> util = new ExcelUtil<>(Htuser.class);
		util.exportExcel(list, "htuser");
		return Result.success();
	}
}
