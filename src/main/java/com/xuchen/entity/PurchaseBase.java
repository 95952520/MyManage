package com.xuchen.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 进货表
 * </p>
 *
 * @author xuchen
 * @since 2018-05-29
 */
public class PurchaseBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "purchase_id", type = IdType.AUTO)
    private Integer purchaseId;
    /**
     * 供货商
     */
    private Integer supplierId;
    /**
     * 0.未付款 1.付部分 2.已付款
     */
    private Integer payType;
    /**
     * 应付金额
     */
    private BigDecimal totalPrice;
    /**
     * 运费
     */
    private BigDecimal deliveryPrice;
    /**
     * 已付款
     */
    private BigDecimal payMoney;
    /**
     * 未付款
     */
    private BigDecimal unpayMoney;
    /**
     * 进货时间
     */
    private Date purchaseTime;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 备注
     */
    private String remark;
    private Date createTime;


    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getUnpayMoney() {
        return unpayMoney;
    }

    public void setUnpayMoney(BigDecimal unpayMoney) {
        this.unpayMoney = unpayMoney;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PurchaseBase{" +
        ", purchaseId=" + purchaseId +
        ", supplierId=" + supplierId +
        ", payType=" + payType +
        ", totalPrice=" + totalPrice +
        ", deliveryPrice=" + deliveryPrice +
        ", payMoney=" + payMoney +
        ", unpayMoney=" + unpayMoney +
        ", purchaseTime=" + purchaseTime +
        ", payTime=" + payTime +
        ", remark=" + remark +
        ", createTime=" + createTime +
        "}";
    }
}
