package com.xuchen.service;

import com.baomidou.mybatisplus.service.IService;
import com.xuchen.entity.PurchaseBase;

/**
 * <p>
 * 进货表 服务类
 * </p>
 *
 * @author xuchen
 * @since 2018-05-29
 */
public interface PurchaseBaseService extends IService<PurchaseBase> {

    void updateTotalMoney(Integer purchaseBaseId);

    void updateGoodsCountForDel(Integer purchaseId);
}
