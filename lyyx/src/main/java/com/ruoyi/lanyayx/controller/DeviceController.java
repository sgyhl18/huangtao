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
import com.ruoyi.lanyayx.domain.Device;
import com.ruoyi.lanyayx.service.IDeviceService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备 控制层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@Slf4j
@Controller
@RequestMapping("/lanyayx/device")
public class DeviceController extends BaseController {
    private String prefix = "lanyayx/device";
	
	private final IDeviceService deviceService;

	@Autowired
	public DeviceController(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	/**
	*  列表页
	*/
	@RequiresPermissions("lanyayx:device:view")
	@GetMapping
	public String device(){
	    return prefix + "/device";
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
		Device device = deviceService.getById(id);
		mmap.put("device", device);
		return prefix + "/edit";
	}
	
	/**
	 * 查询设备列表
	 */
	@RequiresPermissions("lanyayx:device:list")
	@PostMapping("/list")
	@ResponseBody
	public Result list(Device device){
		startPage();
        List<Device> list = deviceService.list(device);
		return Result.success(getDataTable(list));
	}

	/**
	 * 新增保存设备
	 */
	@RequiresPermissions("lanyayx:device:add")
	@Log(title = "设备", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public Result addSave(Device device){
		deviceService.save(device);
		return Result.success();
	}

	/**
	 * 修改保存设备
	 */
	@RequiresPermissions("lanyayx:device:edit")
	@Log(title = "设备", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public Result editSave(Device device){
		deviceService.updateById(device);
		return Result.success();
	}
	
	/**
	 * 删除设备
	 */
	@RequiresPermissions("lanyayx:device:remove")
	@Log(title = "设备", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public Result remove(String ids){
		Long[] longIds = Convert.toLongArray(ids);
		deviceService.removeByIds(Arrays.asList(longIds));
		return Result.success();
	}

	/**
	 * 导出设备列表
	 */
	@RequiresPermissions("lanyayx:device:export")
	@PostMapping("/export")
	@ResponseBody
	public Result export(Device device){
		List<Device> list = deviceService.list(device);
		ExcelUtil<Device> util = new ExcelUtil<>(Device.class);
		util.exportExcel(list, "device");
		return Result.success();
	}
}
