package com.xuchen.service;

import com.baomidou.mybatisplus.service.IService;
import com.xuchen.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author edwin
 * @since 2018-04-03
 */
public interface SysRoleService extends IService<SysRole> {

    boolean deleteById(SysRole myEntity);

    void insertRoleAndMenu(SysRole roleId);

    List<String> findPermsByUserId(Integer userId);

    List<String> findRolesByUserId(Integer userId);
}
