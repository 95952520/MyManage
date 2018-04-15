package com.xuchen.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author edwin
 * @since 2018-04-14
 */
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库存表
     */
    @TableId(value = "stock_id", type = IdType.AUTO)
    private Integer stockId;
    private String stockName;
    /**
     * 0.粉类 1.浆类 2.乳液类 3.沙子 4.小器具 5.其他
     */
    private Integer stockType;
    /**
     * 0.袋 1.桶 2.瓶 3.个 4.公斤
     */
    private Integer unitType;
    /**
     * 每份的重量
     */
    private BigDecimal weight;
    /**
     * 0.克 1.千克 2.吨
     */
    private Integer weightType;
    /**
     * 库存量
     */
    private Integer stockCount;
    /**
     * 供应商
     */
    private Integer supplier;
    private Date createTime;
    private String createUser;


    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getWeightType() {
        return weightType;
    }

    public void setWeightType(Integer weightType) {
        this.weightType = weightType;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "Stock{" +
        ", stockId=" + stockId +
        ", stockName=" + stockName +
        ", stockType=" + stockType +
        ", unitType=" + unitType +
        ", weight=" + weight +
        ", weightType=" + weightType +
        ", stockCount=" + stockCount +
        ", supplier=" + supplier +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        "}";
    }
}
