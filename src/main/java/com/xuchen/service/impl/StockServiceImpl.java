package com.xuchen.service.impl;

import com.xuchen.entity.Stock;
import com.xuchen.mapper.StockMapper;
import com.xuchen.service.StockService;
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
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

}
