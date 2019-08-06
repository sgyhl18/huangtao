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
import com.ruoyi.lanyayx.domain.Datamonitor;
import com.ruoyi.lanyayx.service.IDatamonitorService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据监控 控制层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@Slf4j
@Controller
@RequestMapping("/lanyayx/datamonitor")
public class DatamonitorController extends BaseController {
    private String prefix = "lanyayx/datamonitor";
	
	private final IDatamonitorService datamonitorService;

	@Autowired
	public DatamonitorController(IDatamonitorService datamonitorService) {
		this.datamonitorService = datamonitorService;
	}

	/**
	*  列表页
	*/
	@RequiresPermissions("lanyayx:datamonitor:view")
	@GetMapping
	public String datamonitor(){
	    return prefix + "/datamonitor";
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
		Datamonitor datamonitor = datamonitorService.getById(id);
		mmap.put("datamonitor", datamonitor);
		return prefix + "/edit";
	}
	
	/**
	 * 查询数据监控列表
	 */
	@RequiresPermissions("lanyayx:datamonitor:list")
	@PostMapping("/list")
	@ResponseBody
	public Result list(Datamonitor datamonitor){
		startPage();
        List<Datamonitor> list = datamonitorService.list(datamonitor);
		return Result.success(getDataTable(list));
	}

	/**
	 * 新增保存数据监控
	 */
	@RequiresPermissions("lanyayx:datamonitor:add")
	@Log(title = "数据监控", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public Result addSave(Datamonitor datamonitor){
		datamonitorService.save(datamonitor);
		return Result.success();
	}

	/**
	 * 修改保存数据监控
	 */
	@RequiresPermissions("lanyayx:datamonitor:edit")
	@Log(title = "数据监控", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public Result editSave(Datamonitor datamonitor){
		datamonitorService.updateById(datamonitor);
		return Result.success();
	}
	
	/**
	 * 删除数据监控
	 */
	@RequiresPermissions("lanyayx:datamonitor:remove")
	@Log(title = "数据监控", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public Result remove(String ids){
		Long[] longIds = Convert.toLongArray(ids);
		datamonitorService.removeByIds(Arrays.asList(longIds));
		return Result.success();
	}

	/**
	 * 导出数据监控列表
	 */
	@RequiresPermissions("lanyayx:datamonitor:export")
	@PostMapping("/export")
	@ResponseBody
	public Result export(Datamonitor datamonitor){
		List<Datamonitor> list = datamonitorService.list(datamonitor);
		ExcelUtil<Datamonitor> util = new ExcelUtil<>(Datamonitor.class);
		util.exportExcel(list, "datamonitor");
		return Result.success();
	}
}
