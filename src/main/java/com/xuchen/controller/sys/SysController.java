package com.xuchen.controller.sys;

import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.SysUser;
import com.xuchen.enums.UserStatusEnum;
import com.xuchen.service.SysUserService;
import com.xuchen.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("sys")
public class SysController extends BaseController {

    @Autowired
    SysUserService sysUserService;


    @RequestMapping("/personalInfo")
    String personalInfo(HttpServletRequest request) {
        request.setAttribute("sysUser", sysUserService.selectById(getSessionUserId()));
        return "sys/personal-info";
    }

    @RequestMapping("/updatePersonalInfo")
    @ResponseBody
    @RequestLog
    Result updatePersonalInfo(SysUser sysUser) {
        if ("".equals(sysUser.getPassword())) {
            sysUser.setPassword(null);
        }else {
            MyUtils.encrypPassword(sysUser);
        }
        sysUserService.updateById(sysUser);
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
        request.setAttribute("userStatus", UserStatusEnum.getMap());
    }
}
