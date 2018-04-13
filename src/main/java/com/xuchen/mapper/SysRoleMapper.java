package com.xuchen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuchen.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author edwin
 * @since 2018-04-03
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    void insertRoleMenu(Integer roleId);

    List<String> findPermsByUserId(Integer userId);

    List<String> findRolesByUserId(Integer userId);
}
