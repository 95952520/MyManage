package com.xuchen.controller.sys;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseQuery;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.SysUser;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.enums.UserStatusEnum;
import com.xuchen.model.UserRoleModel;
import com.xuchen.model.base.TreeParModel;
import com.xuchen.service.SysUserRoleService;
import com.xuchen.service.SysUserService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author edwin
 * @since 2018-03-01
 */
@Controller
@RequestMapping("sysUser")
@RequiresRoles("superUser")
public class SysUserController extends BaseController {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysUserRoleService sysUserRoleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "sys/user/sys-user-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, SysUser myEntity, String params, HttpServletRequest request) {
        if (MyUtils.isNotEmpty(params)) {
            myEntity = JSONObject.parseObject(params).toJavaObject(SysUser.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.like("user_name").eq("status");
        List<SysUser> list = sysUserService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(HttpServletRequest request) {
        setAttributeEnums(request);
        return "sys/user/sys-user-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(SysUser myEntity, HttpServletRequest request) {
        myEntity.setCreateTime(new Date());
        myEntity.setCreateUser(getSessionUserId());
        MyUtils.encrypPassword(myEntity);
        sysUserService.insert(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(SysUser myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", sysUserService.selectById(myEntity));
        return "sys/user/sys-user-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(SysUser myEntity, HttpServletRequest request) {
        if (myEntity.getStatus()==null){
            myEntity.setStatus(0);
        }
        sysUserService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toUserRole", method = RequestMethod.GET)
    String toRoleMenu(Integer id, HttpServletRequest request) {
        request.setAttribute("userId",id);
        return "sys/user/sys-user-role";
    }

    @RequestMapping(value = "userRoleTree", method = RequestMethod.GET)
    @ResponseBody
    String userRoleTree(Integer paramId) {
        List<TreeParModel> list = new ArrayList<>();
        List<UserRoleModel> userRoleList = sysUserRoleService.getUseRoleTreeByUserId(paramId);
        for (UserRoleModel model : userRoleList) {
            TreeParModel treeParModel = new TreeParModel();
            treeParModel.setValue(model.getRoleId());
            treeParModel.setTitle(model.getRoleName());
            treeParModel.setChecked(model.getUserId()!=null);
            list.add(treeParModel);
        }
        return JSONArray.toJSONString(list);
    }

    @RequestMapping(value = "updateUserRole",method = RequestMethod.POST)
    @ResponseBody
    @RequestLog
    Result updateUserRole(Integer id, Integer[] ids) {
        sysUserRoleService.updateUserRole(id,ids);
        return Result.success();
    }

    @RequestMapping(value = "resetPwd",method = RequestMethod.POST)
    @ResponseBody
    @RequestLog
    Result resetPwd(SysUser myEntity) {
        myEntity.setPassword("123456");
        MyUtils.encrypPassword(myEntity);
        sysUserService.updateById(myEntity);
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request){
        request.setAttribute("userStatus", UserStatusEnum.getMap());
    }
}

