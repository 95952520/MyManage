package com.xuchen.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseQuery;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.SysMenu;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.model.ParentMenu;
import com.xuchen.service.SysMenuService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("sysMenu")
@RequiresRoles("superUser")
public class SysMenuController extends BaseController {


    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        //setAttributeEnums(request);
        List<ParentMenu> list = sysMenuService.getMenuList();
        request.setAttribute("menuList",list);
        return "sys/menu/sys-menu-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, SysMenu myEntity, String params, HttpServletRequest request) {
        if (MyUtils.isNotEmpty(params)) {
            myEntity = JSONObject.parseObject(params).toJavaObject(SysMenu.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.eq("parent_id",0).orderBy("order_num",false);
        List<SysMenu> list = sysMenuService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(SysMenu myEntity, HttpServletRequest request) {
       // setAttributeEnums(request);
        if(myEntity.getParentId() != null){
            request.setAttribute("parentId", myEntity.getParentId());

            request.setAttribute("parentName", myEntity.getName());
        }
        return "sys/menu/sys-menu-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(SysMenu myEntity, HttpServletRequest request) {
       // sysMenuService.insert(myEntity);
        sysMenuService.insertRoleAndMenu(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(BaseQuery baseQuery, SysMenu myEntity, HttpServletRequest request) {
        SysMenu menu =  sysMenuService.selectById(myEntity);
        if(menu.getParentId() > 0){
            MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
            wrapper.eq("parent_id",0).orderBy("order_num",false);
            List<SysMenu> list = sysMenuService.selectList(wrapper);
            request.setAttribute("mainMenus", list);
        }
        request.setAttribute("menuEntity", menu);

        return "sys/menu/sys-menu-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    @RequestLog
    Result doEdit(SysMenu myEntity, HttpServletRequest request) {
        sysMenuService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequestLog
    Result delete(SysMenu myEntity, HttpServletRequest request) {
        sysMenuService.deleteMenuById(myEntity.getMenuId());
        return Result.success();
    }
}
