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
import com.ruoyi.lanyayx.domain.User;
import com.ruoyi.lanyayx.service.IUserService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户 控制层
 * 
 * @author yanghl
 * @date 2019-07-23
 */
@Slf4j
@Controller
@RequestMapping("/lanyayx/user")
public class UserController extends BaseController {
    private String prefix = "lanyayx/user";
	
	private final IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	/**
	*  列表页
	*/
	@RequiresPermissions("lanyayx:user:view")
	@GetMapping
	public String user(){
	    return prefix + "/user";
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
		User user = userService.getById(id);
		mmap.put("user", user);
		return prefix + "/edit";
	}
	
	/**
	 * 查询用户列表
	 */
	@RequiresPermissions("lanyayx:user:list")
	@PostMapping("/list")
	@ResponseBody
	public Result list(User user){
		startPage();
        List<User> list = userService.list(user);
		return Result.success(getDataTable(list));
	}

	/**
	 * 新增保存用户
	 */
	@RequiresPermissions("lanyayx:user:add")
	@Log(title = "用户", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public Result addSave(User user){
		userService.save(user);
		return Result.success();
	}

	/**
	 * 修改保存用户
	 */
	@RequiresPermissions("lanyayx:user:edit")
	@Log(title = "用户", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public Result editSave(User user){
		userService.updateById(user);
		return Result.success();
	}
	
	/**
	 * 删除用户
	 */
	@RequiresPermissions("lanyayx:user:remove")
	@Log(title = "用户", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public Result remove(String ids){
		Long[] longIds = Convert.toLongArray(ids);
		userService.removeByIds(Arrays.asList(longIds));
		return Result.success();
	}

	/**
	 * 导出用户列表
	 */
	@RequiresPermissions("lanyayx:user:export")
	@PostMapping("/export")
	@ResponseBody
	public Result export(User user){
		List<User> list = userService.list(user);
		ExcelUtil<User> util = new ExcelUtil<>(User.class);
		util.exportExcel(list, "user");
		return Result.success();
	}
}
