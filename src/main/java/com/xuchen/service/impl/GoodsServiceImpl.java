package com.xuchen.service.impl;

import com.xuchen.entity.Goods;
import com.xuchen.mapper.GoodsMapper;
import com.xuchen.service.GoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuchen
 * @since 2018-04-16
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    /**
     * 更新商品库存
     *
     * @param goodsId          商品id
     * @param stockCountChange 库存增加值(减少则为负数)
     */
    @Override
    public void updateGoodsStock(Integer goodsId, Integer stockCountChange) {
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        Goods originalGoods = baseMapper.selectById(goods);
        goods.setStockCount(originalGoods.getStockCount() + stockCountChange);
        baseMapper.updateById(goods);
    }


}
