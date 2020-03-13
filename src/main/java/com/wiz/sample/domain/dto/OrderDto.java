package com.wiz.sample.domain.dto;

import com.wiz.sample.domain.model.Customer;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * オーダー情報Dtoクラス。
 * <br>
 * 関連するカスタマー情報も付加して返します。
 * <br>
 *
 * @version 1.00(2020/03/12)
 * @author C.Shinagawa
 */
public class OrderDto {

    /** オーダーNo. */
    private int orderNo;

    /** 商品コード */
    private int productCode;

    /** 注文数量 */
    private int quantity;

    /** カスタマーコード */
    private int customerCode;

    private String customerName;

    private String account;

    /**
     * オーダー番号 setter。
     * @param orderNo
     */
    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 商品コード setter。
     * @param productCode
     */
    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    /**
     * 注文数量 setter。
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * オーダー番号 getter。
     * @return オーダー番号
     */
    public int getOrderNo() {
        return orderNo;
    }

    /**
     * 商品コード getter。
     * @return 商品コード
     */
    public int getProductCode() {
        return productCode;
    }

    /**
     * 注文数量 getter。
     * @return 注文数量
     */
    public int getQuantity() {
        return quantity;
    }

    public int getCustomerCode() {
        return customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAccount() {
        return account;
    }

}
