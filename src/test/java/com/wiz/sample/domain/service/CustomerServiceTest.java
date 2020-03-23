package com.wiz.sample.domain.service;

import com.wiz.sample.TestConfig;
import com.wiz.sample.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback
@Import(TestConfig.class)
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;


//    @Test
//    void findAllCustomer() {
//    }
    @Test
    void すべてのカスタマー情報を取得できること() {
    }

//    @Test
//    void findCustomer() {
//    }

    @Test
    void カスタマーコードに該当するカスタマー情報を取得できること() {
        Customer customer = customerService.findCustomer(1000);
        assertEquals(1000, customer.getCustomerCode());
        assertEquals("○×商事", customer.getCustomerName());
        assertEquals("000-1234", customer.getAccount());
    }

    @Test
    void カスタマーコードに該当するカスタマー情報が0件の場合はExceptionを返すこと() {
        assertThrows(NoResultException.class, () -> customerService.findCustomer(1234));
    }

//    @Test
//    void findCustomerWithOrder() {
//    }

    @Test
    void すべてのカスタマー情報と関連するオーダー情報を取得できること() {
    }

//    @Test
//    void insert() {
//    }

    @Test
    void カスタマー情報を登録できること() {
    }

//    @Test
//    void update() {
//    }

    @Test
    void カスタマー情報を更新できること() {
    }

//    @Test
//    void delete() {
//    }

    @Test
    void カスタマー情報を削除できること() {
        Customer customer = customerService.findCustomer(1000);
        assertEquals(1000, customer.getCustomerCode());
        assertEquals("○×商事", customer.getCustomerName());
        assertEquals("000-1234", customer.getAccount());

        // 削除
        customerService.delete(customer);

        // 0件であることをテストする。
        assertThrows(NoResultException.class, () -> customerService.findCustomer(1000));
    }
}