package com.xuchen.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuchen.entity.SysMenu;
import com.xuchen.mapper.SysMenuMapper;
import com.xuchen.model.ParentMenu;
import com.xuchen.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author eileen
 * @since 2018-04-03
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<ParentMenu> getMenuByUserId(Integer i) {
        return sysMenuMapper.getMenuByUserId(i);
    }

    @Override
    public List<ParentMenu> getMenuByRoleId(Integer id) {
        return baseMapper.getMenuByRoleId(id);
    }

    @Override
    public List<ParentMenu> getMenuList() {
        return sysMenuMapper.getMenuList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuById(Integer id) {
         sysMenuMapper.deleteMenuById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRoleAndMenu(SysMenu myEntity) {
        super.insert(myEntity);
        sysMenuMapper.insertRoleAndMenu(myEntity.getMenuId());
    }

}
