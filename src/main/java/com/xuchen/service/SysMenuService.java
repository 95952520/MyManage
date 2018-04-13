package com.xuchen.service;

import com.baomidou.mybatisplus.service.IService;
import com.xuchen.entity.SysMenu;
import com.xuchen.model.ParentMenu;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author eileen
 * @since 2018-04-03
 */
public interface SysMenuService extends IService<SysMenu> {

    List<ParentMenu> getMenuByUserId(Integer id);

    List<ParentMenu> getMenuByRoleId(Integer id);

    List<ParentMenu> getMenuList();

    void deleteMenuById(Integer id);

    void insertRoleAndMenu(SysMenu myEntity);
}
