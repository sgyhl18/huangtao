package com.ruoyi.lanyayx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.Result;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.lanyayx.common.Const;
import com.ruoyi.lanyayx.common.ServerResponse;
import com.ruoyi.lanyayx.domain.Htuser;
import com.ruoyi.lanyayx.domain.Suggestion;
import com.ruoyi.lanyayx.domain.User;
import com.ruoyi.lanyayx.service.IHtuserService;
import com.ruoyi.lanyayx.service.ISuggestionService;
import com.ruoyi.lanyayx.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yanghl
 * @date 2019/3/16 20:52
 */
@Slf4j
@RestController
@RequestMapping("/api/")
@Api(value = "用户controller", description = "登录注册相关接口", tags = {"ApiController"})
public class ApiController extends BaseController {

    private final IUserService iUserService;
    private final ISuggestionService iSuggestionService;
    private final IHtuserService htuserService;
    @Autowired
    public ApiController(IUserService iUserService,ISuggestionService iSuggestionService,IHtuserService htuserService) {
        this.htuserService = htuserService;
        this.iUserService = iUserService;
        this.iSuggestionService = iSuggestionService;
    }
    /**
     * 查询黄桃买家汇总列表
     */
    @PostMapping("/list")
    @ResponseBody
    public Result list(Htuser htuser){
        startPage();
        List<Htuser> list = htuserService.list(htuser);
        return Result.success(getDataTable(list));
    }

    /**
     * 新增保存黄桃买家
     */
    @ApiOperation(value = "新增保存黄桃买家")
    @PostMapping("/add")
    @ResponseBody
    public Result addSave(Htuser htuser){
        htuserService.save(htuser);
        return Result.success();
    }
    /**
     * 新增保存黄桃买家2
     */
    @ApiOperation(value = "新增保存黄桃买家2")
    @PostMapping("/add2")
    @ResponseBody
    public Result addSave2(@RequestBody Htuser htuser){
        htuserService.save(htuser);
        return Result.success();
    }

    /**
     * 修改保存黄桃买家
     */
    @ApiOperation(value = "修改保存黄桃买家")
    @PostMapping("/edit")
    @ResponseBody
    public Result editSave(Htuser htuser){
        htuserService.updateById(htuser);
        return Result.success();
    }
    /**
     * 修改保存黄桃买家2
     */
    @ApiOperation(value = "修改保存黄桃买家2")
    @PostMapping("/edit2")
    @ResponseBody
    public Result editSave2(@RequestBody Htuser htuser){
        htuserService.updateById(htuser);
        return Result.success();
    }

    /**
     * 删除黄桃买家
     */
    @ApiOperation(value = "删除黄桃买家")
    @PostMapping( "/remove")
    @ResponseBody
    public Result remove(String ids){
        Long[] longIds = Convert.toLongArray(ids);
        htuserService.removeByIds(Arrays.asList(longIds));
        return Result.success();
    }
    /**
     * 删除黄桃买家2
     */
    @ApiOperation(value = "删除黄桃买家2")
    @PostMapping( "/remove2")
    @ResponseBody
    public Result remove2(@RequestBody  String ids){
        Long[] longIds = Convert.toLongArray(ids);
        htuserService.removeByIds(Arrays.asList(longIds));
        return Result.success();
    }

    /**
     * 导出黄桃买家汇总列表
     */
    @PostMapping("/export")
    @ResponseBody
    public Result export(Htuser htuser){
        List<Htuser> list = htuserService.list(htuser);
        ExcelUtil<Htuser> util = new ExcelUtil<>(Htuser.class);
        util.exportExcel(list, "htuser");
        return Result.success();
    }
    /**
     * 发送短信验证码
     *
     * @param phoneNo 手机号码
     * @return
     */
    @PostMapping("sendSms")
    @ResponseBody
    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNo", value = "手机号", dataType = "String", paramType = "query"),
    })
    public ServerResponse<Map<String, Object>>  sendSms(String phoneNo) {
        return iUserService.sendSms(phoneNo);
    }

    /**
     *手机号验证码验证
     * @param phoneNo
     * @param code
     * @param desMsg
     * @return
     */
    @PostMapping("validateCode")
    @ResponseBody
    @ApiOperation(value = "手机号验证码验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNo", value = "手机号码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "desMsg", value = "校验码", dataType = "String", paramType = "query")
    })
    public ServerResponse<User>  validateCode(String phoneNo, String code, String desMsg) {
        return iUserService.validateCode(phoneNo, code, desMsg);
    }
    /**
     * 用户登录
     * @param username
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "登录类型：1、微信appid；2、qqtoken；3、手机号", dataType = "int", paramType = "query")
    })
    public ServerResponse<User> login(String username,int type, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,type);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 用户注销
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户注销")
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
   /* @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户注册")*/
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取用户信息")
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    /**
     * 用户反馈
     * @param session
     * @return
     */
    @RequestMapping(value = "suggestion",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "反馈内容", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deviceId", value = "设备id", dataType = "String", paramType = "query"),

    })
    public ServerResponse<String> suggestion(String content,int deviceId,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            int userId=user.getId();
            Suggestion suggestion=new Suggestion();
            suggestion.setUserId(userId);
            suggestion.setDeviceId(deviceId);
            suggestion.setContent(content);
            ServerResponse<String> response=iSuggestionService.addsu(suggestion);
            return response;
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }
}
