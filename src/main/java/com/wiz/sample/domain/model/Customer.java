package com.wiz.sample.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * カスタマー情報 エンティティクラス。
 * <br>
 * 一対多の関係にあるオーダー情報リスト プロパティを持ちます。
 * <br>
 * TODO:Validate
 *
 * @version 1.00(2020/03/12)
 * @author C.Shinagawa
 */
@Entity
@Table(name="customer_list")
public class Customer {

    /** カスタマーコード */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerCode;

    /** 名前 */
    @Column(length=4, nullable=false)
    private String customerName;

    /** 口座番号 */
    @NotEmpty(message = "account:空白は不可")
    private String account;

    /** オーダー情報リスト */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orderList;

    /**
     * カスタマーコード  setter。
     * @param customerCode
     */
    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * 名前 setter。
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 口座番号  setter。
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * オーダー情報リスト  setter。
     * @param orderList
     */
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    /**
     * カスタマーコード getter。
     * @return カスタマーコード
     */
    public int getCustomerCode() {
        return customerCode;
    }

    /**
     * 名前 getter。
     * @return 名前
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 口座番号 getter。
     * @return 口座番号
     */
    public String getAccount() {
        return account;
    }

    /**
     * オーダー情報リスト getter。
     * @return オーダー情報リスト
     */
    public List<Order> getOrderList() {
        return orderList;
    }
}
