package com.xuchen.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.xuchen.base.BaseQuery;
import com.xuchen.base.Result;
import com.xuchen.controller.base.BaseController;
import com.xuchen.entity.SysRoleMenu;
import com.xuchen.entity.base.MyEntityWrapper;
import com.xuchen.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("sysRoleMenu")
public class SysRoleMenuController extends BaseController {


    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String index(HttpServletRequest request) {
        setAttributeEnums(request);
        return "sys/role/menu/sys-role-menu-list";
    }

    @RequestMapping("list")
    @ResponseBody
    Result list(BaseQuery baseQuery, SysRoleMenu myEntity, String params, HttpServletRequest request) {
        if (params != null) {
            myEntity = JSONObject.parseObject(params).toJavaObject(SysRoleMenu.class);
        }
        MyEntityWrapper wrapper = new MyEntityWrapper(baseQuery, myEntity);
//        wrapper.eq("id").like("user_name");
        List<SysRoleMenu> list = sysRoleMenuService.selectList(wrapper);
        return Result.success(PageHelper.freeTotal(), list);
    }

    @RequestMapping("editText")
    @ResponseBody
    Result editText(SysRoleMenu myEntity, HttpServletRequest request) {
        sysRoleMenuService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    String toAdd(Integer id, HttpServletRequest request) {
        setAttributeEnums(request);
        return "sys/role/menu/sys-role-menu-add";
    }

    @RequestMapping("doAdd")
    @ResponseBody
    Result doAdd(SysRoleMenu myEntity, HttpServletRequest request) {
        sysRoleMenuService.insert(myEntity);
        return Result.success();
    }

    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    String toEdit(SysRoleMenu myEntity, HttpServletRequest request) {
        setAttributeEnums(request);
        request.setAttribute("myEntity", sysRoleMenuService.selectById(myEntity));
        return "sys/role/menu/sys-role-menu-edit";
    }

    @RequestMapping("doEdit")
    @ResponseBody
    Result doEdit(SysRoleMenu myEntity, HttpServletRequest request) {
        sysRoleMenuService.updateById(myEntity);
        return Result.success();
    }

    @RequestMapping("delete")
    @ResponseBody
    Result delete(SysRoleMenu myEntity, HttpServletRequest request) {
        sysRoleMenuService.deleteById(myEntity);
        return Result.success();
    }

    @RequestMapping("deleteList")
    @ResponseBody
    Result deleteList(String jsonObj, HttpServletRequest request) {
//        List<SysRoleMenu> list = JSONArray.parseArray(jsonObj,SysRoleMenu.class);
//        List<Integer> ids = new ArrayList<>();
//       for (SysRoleMenu obj : list) {
//            ids.add(obj.getId());
//        }
//        sysRoleMenuService.deleteBatchIds(ids);
        return Result.success();
    }

    private void setAttributeEnums(HttpServletRequest request) {
//        request.setAttribute("menuType", MenuTypeEnum.getMap());
    }
}
