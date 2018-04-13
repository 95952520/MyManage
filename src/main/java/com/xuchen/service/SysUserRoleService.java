package com.xuchen.service;

import com.baomidou.mybatisplus.service.IService;
import com.xuchen.entity.SysUserRole;
import com.xuchen.model.UserRoleModel;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author edwin
 * @since 2018-04-08
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    List<UserRoleModel> getUseRoleTreeByUserId(Integer userId);

    void updateUserRole(Integer id, Integer[] ids);
}
