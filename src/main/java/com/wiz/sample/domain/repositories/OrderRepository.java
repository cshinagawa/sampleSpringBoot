package com.wiz.sample.domain.repositories;

import com.wiz.sample.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * オーダー情報リポジトリークラス。
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
