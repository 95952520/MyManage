package com.xuchen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuchen.entity.SysUserRole;
import com.xuchen.model.UserRoleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author edwin
 * @since 2018-04-08
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    List<UserRoleModel> getUseRoleTreeByUserId(Integer userId);

    void insertUserRole(@Param("userId") Integer id, @Param("array") Integer[] ids);
}
