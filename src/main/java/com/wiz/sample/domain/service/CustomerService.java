package com.wiz.sample.domain.service;

import com.wiz.sample.domain.model.Customer;
import com.wiz.sample.domain.model.Order;
import com.wiz.sample.domain.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * カスタマー情報サービスクラス。
 * <br>
 * customer_listテーブルへのデータアクセスなどを行います。
 *
 * <br>
 * TODO:クエリー実行回りの初期化を一カ所にまとめる。<br>
 * TODO:エラー処理
 *
 * <br>
 * 学習補足）
 * 　・Springのフレームワーク（CriteriaAPI、Repository）を使ったデータアクセス処理を試しています。
 * 　　ほかにもいいやり方があるかも・・・。
 *
 * @version 1.00(2020/03/12)
 * @author C.Shinagawa
 */
@Service
@Transactional
public class CustomerService {

    // TODO:privateにする。
    /**
     * データへアクセスするインターフェース。
     */
    @Autowired
    CustomerRepository repository;

    /**
     * エンティティを扱うインターフェース。
     */
    @PersistenceContext
    EntityManager entityManager;

    /**
     * カスタマー情報リスト 取得メソッド。
     * <br>
     * カスタマー情報の一覧を返します。
     *
     * @return カスタマー情報リスト
     */
    public List<Customer> findAllCustomer() {

        List<Customer> resultList;

        // クエリー生成管理クラス
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        // クエリー実行クラス
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        // 検索対象エンティティのルート
        Root<Customer> root = query.from(Customer.class);

        // エンティティ絞り込み（この場合はすべてのカスタマー情報を対象とする。）
        query.select(root);

        // クエリー生成、実行して結果を取得。
        resultList = entityManager.createQuery(query).getResultList();

        return resultList;
    }

    /**
     * カスタマー情報 取得メソッド。
     * <br>
     * 指定されたカスタマーコードに該当するカスタマー情報を返します。
     *
     * @param customerCode カスタマーコード
     * @return 指定されたカスタマーコードに該当するカスタマー情報
     */
    public Customer findCustomer(int customerCode) {

        Customer result;

        // クエリー生成管理クラス
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        // クエリー実行クラス
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        // 検索対象エンティティ(ルート)
        Root<Customer> root = query.from(Customer.class);

        // エンティティ絞り込み（この場合はcustomer_codeと指定の名称が等しいものを対象とする。）
        query.select(root).where(builder.equal(root.get("customerCode"), customerCode));

        // クエリー生成、実行して結果を取得。
        result = entityManager.createQuery(query).getSingleResult();

        return result;
    }

    /**
     * カスタマー・オーダー情報リスト 取得メソッド。
     * <br>
     * カスタマー情報とオーダー情報の一覧を返します。
     *
     * @return カスタマー情報とオーダー情報リスト
     */
    public List<Order> findCustomerWithOrder() {

        List<Order> resultList;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);

        resultList = entityManager.createQuery(query).getResultList();
        System.out.print(resultList.size() + "---------------------------------------------------");

        // TODO：
        return null;
    }

    /**
     * カスタマー情報 登録メソッド。
     * <br>
     * 指定されたカスタマー情報を登録します。
     *
     * @param customer カスタマー情報
     * @return 登録されたカスタマー情報
     */
    public Customer insert(Customer customer) {

        Customer result = repository.save(customer);

        return result;
    }

    /**
     * カスタマー情報 更新メソッド。
     * <br>
     * 指定されたカスタマー情報を登録します。
     *
     * @param customer カスタマー情報
     * @return 登録されたカスタマー情報
     */
    public Customer update(Customer customer) {

        // 更新処理の戻り値で登録結果を取得することも可能。
        Customer result = repository.save(customer);

        return result;
    }

    /**
     * カスタマー情報 削除メソッド。
     * <br>
     * 指定されたカスタマー情報を削除します。
     *
     * @param customer カスタマー情報
     * @return 削除結果
     */
    public boolean delete(Customer customer) {

        // 更新処理の戻り値で登録結果を取得することも可能。
        repository.delete(customer);

        // TODO:結果
        return true;
    }

    //-----------------------------------------------------
    // 以下のメソッドは学習用にRepositoryを使用。
    //-----------------------------------------------------
    /**
     * カスタマー情報リスト取得メソッド。
     * <br>
     * すべてのカスタマー情報を返します。（Repositoryを利用）
     *
     * @return カスタマー情報リスト
     */
    public List<Customer> find() {
        List<Customer> list = new ArrayList<Customer>();
        list = repository.findAll();

        return list;
    }

    /**
     * カスタマー情報取得メソッド。
     * <br>
     * 指定されたカスタマーコードに該当するカスタマー情報を返します。（Repositoryを利用）
     *
     * @param customerCode カスタマーコード
     * @return 指定されたカスタマーコードに該当するカスタマー情報
     */
    public Customer findCustomerByRepository(int customerCode) {

        // 戻り値はOptional型で取得（検索結果がnullの場合もある為）。
        Optional<Customer> optional = repository.findById(customerCode);

        // データを取り出して返す
        return optional.orElse(new Customer());
    }

}
