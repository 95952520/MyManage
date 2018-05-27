package com.xuchen.service.impl;

import com.xuchen.entity.OrderBase;
import com.xuchen.mapper.OrderMapper;
import com.xuchen.service.OrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuchen
 * @since 2018-04-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderBase> implements OrderService {

    @Override
    public void updateTotalMoney(Integer orderId) {
        this.baseMapper.updateTotalMoney(orderId);
    }

    @Override
    public void updateGoodsCountForDel(Integer orderId) {
        this.baseMapper.updateGoodsCountForDel(orderId);
    }
}
