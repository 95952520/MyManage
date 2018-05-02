package com.xuchen.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseQuery;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.core.annotation.RequestLog;
import com.xuchen.entity.SysRole;
import com.xuchen.entity.SysRoleMenu;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.model.ParentMenu;
import com.xuchen.model.SonMenu;
import com.xuchen.model.base.TreeParModel;
import com.xuchen.model.base.TreeSonModel;
import com.xuchen.service.SysMenuService;
import com.xuchen.service.SysRoleMenuService;
import com.xuchen.service.SysRoleService;
import com.xuchen.util.MyUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("sysRole")
@RequiresRoles("superUser")
public class SysRoleController extends BaseController {


    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "sys/role/sys-role-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, SysRole myEntity, String params, HttpServletRequest request) {
        if (MyUtils.isNotEmpty(params)) {
            myEntity = JSONObject.parseObject(params).toJavaObject(SysRole.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
        wrapper.like("role_name");
        List<SysRole> list = sysRoleService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping("editText")
    @ResponseBody
    Result editText(SysRole myEntity) {
        if (MyUtils.isEmpty(myEntity.getRoleSign())){
            return Result.fail("不能为空");
        }
        sysRoleService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(HttpServletRequest request) {
        setAttributeEnums(request);
        return "sys/role/sys-role-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    @RequestLog
    Result doAdd(SysRole myEntity) {
        myEntity.setUserIdCreate(getSessionUserId());
        sysRoleService.insertRoleAndMenu(myEntity);
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequestLog
    Result delete(SysRole myEntity) {
        sysRoleService.deleteById(myEntity);
        return Result.success();
    }


    @RequestMapping(value = "toRoleMenu", method = RequestMethod.GET)
    String toRoleMenu(Integer roleId, HttpServletRequest request) {
        request.setAttribute("roleId",roleId);
        return "sys/role/sys-role-menu";
    }

    @RequestMapping(value = "menuTree", method = RequestMethod.GET)
    @ResponseBody
    String menuTree(Integer paramId) {
        List<TreeParModel> list = new ArrayList<>();

        List<ParentMenu> menuByRoleId = sysMenuService.getMenuByRoleId(paramId);
        for (ParentMenu parentMenu : menuByRoleId) {
            TreeParModel model = new TreeParModel();
            model.setValue(parentMenu.getId());
            model.setTitle(parentMenu.getName());
            List<TreeSonModel> sonList = new ArrayList<>();
            for (SonMenu sonMenu : parentMenu.getList()) {
                TreeSonModel sonModel = new TreeSonModel();
                sonModel.setValue(sonMenu.getSonId());
                sonModel.setTitle(sonMenu.getSonName());
                sonModel.setChecked(sonMenu.getSonStatus()==1);
                sonList.add(sonModel);
            }
            model.setData(sonList);
            list.add(model);
        }
        return JSONArray.toJSONString(list);
    }

    @RequestMapping(value = "updateRoleMenu",method = RequestMethod.POST)
    @ResponseBody
    @RequestLog
    Result updateRoleMenu(Integer id, Integer[] ids) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setStatus(0);
        sysRoleMenuService.update(sysRoleMenu,new EntityWrapper<SysRoleMenu>().eq("role_id",id));
        if (ids.length > 0 ){
            sysRoleMenu.setStatus(1);
            sysRoleMenuService.update(sysRoleMenu,new EntityWrapper<SysRoleMenu>().in("menu_id",ids).eq("role_id",id));
        }
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
//        request.setAttribute("menuType", MenuTypeEnum.getMap());
    }
}
