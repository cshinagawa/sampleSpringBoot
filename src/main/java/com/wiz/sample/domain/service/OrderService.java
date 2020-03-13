package com.wiz.sample.domain.service;

import com.wiz.sample.domain.model.Customer;
import com.wiz.sample.domain.model.Order;
import com.wiz.sample.domain.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * オーダー 情報サービスクラス。
 * <br>
 * customer_listテーブルへのデータアクセスなどを行います。
 *
 * <br>
 * TODO:クエリー実行回りの初期化を一カ所にまとめる。<br>
 * TODO:エラー処理
 *
 * <br>
 *
 * @version 1.00(2020/03/12)
 * @author C.Shinagawa
 */
@Service
@Transactional
public class OrderService {

    // TODO:privateにする。
    /**
     * データへアクセスするインターフェース。
     */
    @Autowired
    private OrderRepository repository;

    /**
     * エンティティを扱うインターフェース。
     */
    @PersistenceContext
    EntityManager entityManager;

    /**
     * オーダー情報リスト 取得メソッド。
     * <br>
     * オーダー情報の一覧を返します。
     *
     * @return オーダー情報リスト
     */
    public List<Order> findAllOrder() {

        List<Order> resultList;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);

        query.select(root);

        resultList = entityManager.createQuery(query).getResultList();

        return resultList;
    }
}
