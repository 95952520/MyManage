package com.xuchen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuchen.entity.SysMenu;
import com.xuchen.model.ParentMenu;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author eileen
 * @since 2018-04-03
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<ParentMenu> getMenuByUserId(Integer id);

    List<ParentMenu> getMenuByRoleId(Integer id);

    List<ParentMenu> getMenuList();

    void deleteMenuById(Integer id);

    void insertRoleAndMenu(Integer menuId);
}
