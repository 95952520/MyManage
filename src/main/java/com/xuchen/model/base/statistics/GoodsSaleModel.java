package com.xuchen.model.base.statistics;

public class GoodsSaleModel {
    private Integer monthTime;
    private Integer goodsId;
    private String goodsName;
    private Double saleMoney;
    private Integer saleCount;
    private Double originalMoney;
    private Double gainMoney;

    public Integer getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(Integer monthTime) {
        this.monthTime = monthTime;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(Double saleMoney) {
        this.saleMoney = saleMoney;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Double getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(Double originalMoney) {
        this.originalMoney = originalMoney;
    }

    public Double getGainMoney() {
        return gainMoney;
    }

    public void setGainMoney(Double gainMoney) {
        this.gainMoney = gainMoney;
    }

    @Override
    public String toString() {
        return "GoodsSaleModel{" +
                "monthTime=" + monthTime +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", saleMoney=" + saleMoney +
                ", saleCount=" + saleCount +
                ", originalMoney=" + originalMoney +
                ", gainMoney=" + gainMoney +
                '}';
    }
}
