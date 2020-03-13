package com.wiz.sample.domain.repositories;

import com.wiz.sample.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * カスタマー情報リポジトリークラス。
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByCustomerCodeOrCustomerName(int code, String name);
    List<Customer> findByCustomerCodeOrCustomerNameOrderByCustomerNameAsc(int code, String name);
}
