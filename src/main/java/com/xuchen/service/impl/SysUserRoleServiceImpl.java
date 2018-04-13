package com.xuchen.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuchen.entity.SysUserRole;
import com.xuchen.mapper.SysUserRoleMapper;
import com.xuchen.model.UserRoleModel;
import com.xuchen.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author edwin
 * @since 2018-04-08
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public List<UserRoleModel> getUseRoleTreeByUserId(Integer userId) {
        return baseMapper.getUseRoleTreeByUserId(userId);
    }

    @Override
    public void updateUserRole(Integer id, Integer[] ids) {
        baseMapper.delete(new EntityWrapper<SysUserRole>().eq("user_id",id));
        if (ids.length>0){
            baseMapper.insertUserRole(id,ids);
        }
    }
}
