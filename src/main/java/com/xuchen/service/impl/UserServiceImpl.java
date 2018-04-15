package com.xuchen.service.impl;

import com.xuchen.entity.User;
import com.xuchen.mapper.UserMapper;
import com.xuchen.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author edwin
 * @since 2018-04-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
