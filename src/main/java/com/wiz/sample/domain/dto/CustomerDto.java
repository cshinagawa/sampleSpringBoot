package com.wiz.sample.domain.dto;

import com.wiz.sample.domain.model.Order;

import javax.persistence.Version;
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

    private String account;

    private int version;

//    List<Order> orderList;
//
//    private int orderNo;
//
//    private int productCode;
//
//    private int quantity;

    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public int getVersion() {
        return version;
    }
}
