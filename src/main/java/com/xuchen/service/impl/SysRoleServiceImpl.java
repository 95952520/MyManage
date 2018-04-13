package com.xuchen.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuchen.entity.SysRole;
import com.xuchen.entity.SysRoleMenu;
import com.xuchen.mapper.SysRoleMapper;
import com.xuchen.service.SysRoleMenuService;
import com.xuchen.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author edwin
 * @since 2018-04-03
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(SysRole myEntity) {
        sysRoleMenuService.delete(new EntityWrapper<SysRoleMenu>().eq("role_id",myEntity.getRoleId()));
        return super.deleteById(myEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRoleAndMenu(SysRole myEntity) {
        super.insert(myEntity);
        baseMapper.insertRoleMenu(myEntity.getRoleId());
    }

    @Override
    public List<String> findPermsByUserId(Integer userId) {
        return baseMapper.findPermsByUserId(userId);
    }

    @Override
    public List<String> findRolesByUserId(Integer userId) {
        return baseMapper.findRolesByUserId(userId);
    }
}
