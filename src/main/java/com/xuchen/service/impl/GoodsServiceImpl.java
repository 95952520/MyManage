package com.xuchen.service.impl;

import com.xuchen.entity.Goods;
import com.xuchen.mapper.GoodsMapper;
import com.xuchen.service.GoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuchen
 * @since 2018-04-16
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public void updateTotalMoney(Integer orderId) {
        this.baseMapper.updateTotalMoney(orderId);
    }
}
