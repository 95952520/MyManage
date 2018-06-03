package com.xuchen.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuchen.entity.PurchaseBase;
import com.xuchen.mapper.PurchaseBaseMapper;
import com.xuchen.service.PurchaseBaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 进货表 服务实现类
 * </p>
 *
 * @author xuchen
 * @since 2018-05-29
 */
@Service
public class PurchaseBaseServiceImpl extends ServiceImpl<PurchaseBaseMapper, PurchaseBase> implements PurchaseBaseService {

    @Override
    public void updateTotalMoney(Integer purchaseBaseId) {
        baseMapper.updateTotalMoney(purchaseBaseId);
    }

    @Override
    public void updateGoodsCountForDel(Integer purchaseId) {
        baseMapper.updateGoodsCountForDel(purchaseId);
    }
}
