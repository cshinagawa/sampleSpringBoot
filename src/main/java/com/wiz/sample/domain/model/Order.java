package com.wiz.sample.domain.model;

import javax.persistence.*;

/**
 * オーダー情報 エンティティクラス。
 * <br>
 * 多対一の関係にあるカスタマー情報プロパティを持ちます。
 * <br>
 * TODO:Validate
 *
 * @version 1.00(2020/03/12)
 * @author C.Shinagawa
 */
@Entity
@Table(name="order_list")
public class Order {

    /** オーダーNo. */
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo;

    /** 商品コード */
    private int productCode;

    /** 注文数量 */
    private int quantity;

    /** カスタマー情報 */
    @ManyToOne
    @JoinColumn(name="customerCode")
    private Customer customer;

    // JOINするプロパティは定義しない。
//    private int customerCode;

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

//    public void setCustomerCode(int customerCode) {
//        this.customerCode = customerCode;
//    }

    /**
     * カスタマー情報 setter。
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
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

//    public int getCustomerCode() {
//        return customerCode;
//    }

    /**
     * カスタマー情報 getter。
     * @return カスタマー情報
     */
    public Customer getCustomer() {
        return customer;
    }
}
