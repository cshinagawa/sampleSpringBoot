package com.wiz.sample.domain.dto;

import com.wiz.sample.domain.model.Order;

import java.util.List;

/**
 * カスタマー情報Dtoクラス。
 * <br>
 * 関連するオーター情報も付加して返します。
 * <br>
 *
 * @version 1.00(2020/03/12)
 * @author C.Shinagawa
 */
public class CustomerDto {

    private int customerCode;

    private String customerName;

    private String accout;

    // TODO:JSONに必要な情報
    // TODO;セッターゲッター

    List<Order> orderList;

    private int orderNo;

    private int productCode;

    private int quantity;

//    private int getDeptName() {
//        return this.dept.getDeptName();
//    }


    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public int getCustomerCode() {
        return customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAccout() {
        return accout;
    }
}
