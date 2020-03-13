package com.wiz.sample.app.customer;

import com.wiz.sample.domain.model.Customer;
import com.wiz.sample.domain.model.Order;
import com.wiz.sample.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * カスタマー コントローラークラス。
 * <br>
 * （SpringBootのURLマッピングにより特定のコントローラーにハンドリングされます。）
 *
 * TODO:DTOにしてJSONを返す。
 * TODO:リクエストデータのチェック
 * TODO:エラー処理
 *
 * @version 1.00(2020/03/12)
 * @author C.Shinagawa
 */
@RestController
public class CustomerController {

    /**
     * データアクセスサービスクラス。
     */
    @Autowired
    CustomerService customerService;

    /**
     * 初期化メソッド。
     */
    @PostConstruct
    public void init() {
        // 初期化処理など
    }

    /**
     * コントローラーサンプル
     *
     * @return
     */
    @RequestMapping(path="/", method= RequestMethod.GET)
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    /**
     * カスタマー情報リスト取得 コントローラー
     *
     * @return カスタマー情報リスト
     */
    @RequestMapping(path="/customer", method= RequestMethod.GET)
    @ResponseBody
    public List<Customer> findAll() {

        List<Customer> list = customerService.findAllCustomer();

        return list;
    }

    /**
     * カスタマー情報取得 コントローラー
     *
     * @param customerCode　カスタマーコード
     * @return カスタマー情報リスト
     */
    @RequestMapping(path="/customer/{customerCode}", method= RequestMethod.GET)
    @ResponseBody
    public Customer findByCustomerCode(@PathVariable int customerCode) {

        Customer customer = customerService.findCustomer(customerCode);

        return customer;
    }

    /**
     * カスタマー情報・オーター情報リスト取得 コントローラー
     *
     * @return カスタマー情報リスト
     */
    @RequestMapping(path="/customer/orderlist", method=RequestMethod.GET)
    @ResponseBody
    public List<Order> findCustomerWithOrder() {

        List result = customerService.findCustomerWithOrder();

        return result;
    }

//    @RequestMapping(path="/customerOr", method= RequestMethod.POST)
//    @ResponseBody
//    public List<Customer> findByCustomerCodeOrCustomerName(@RequestBody Customer resource) {
//
//        int code = resource.getCustomerCode();
//        String name = resource.getCustomerName();
//
//        List<Customer> list = repository.findByCustomerCodeOrCustomerNameOrderByCustomerNameAsc(code, name);
//
//        return list;
//    }

    // TODO:@Validate

    /**
     * カスタマー情報登録 コントローラー
     *
     * @param resource カスタマー登録情報
     * @return 登録結果
     */
    @RequestMapping(path="/customer/add", method= RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false)
    public String insert(@RequestBody Customer resource){

        Customer result;

        // TODO：入力エラーはコントローラーで処理する。
        Customer customer = new Customer();
        customer.setCustomerName(resource.getCustomerName());
        customer.setAccount(resource.getAccount());

        // TODO：SQL実行エラー処理
        // 一意制約エラーなど
        result = customerService.insert(customer);

        return "customerCode：" + result.getCustomerCode() + "を作成しました。";
    }

    /**
     * カスタマー情報更新 コントローラー
     *
     * @param resource カスタマー更新情報
     * @return 更新結果
     */
    @RequestMapping(path="/customer/edit", method= RequestMethod.PUT)
    @ResponseBody
    @Transactional(readOnly = false)
    public String update(@RequestBody Customer resource){

        Customer result;

        // TODO：SQL実行エラー処理
        Customer customer = new Customer();
        customer.setCustomerName(resource.getCustomerName());
        customer.setAccount(resource.getAccount());

        result = customerService.update(customer);

        return "customerCode：" + result.getCustomerCode() + "を更新しました。";
    }

    /**
     * カスタマー情報削除 コントローラー
     *
     * @param customerCode 削除するカスタマーコード
     * @return 削除結果
     */
    @RequestMapping(path="/customer/delete/{customerCode}", method= RequestMethod.DELETE)
    @ResponseBody
    @Transactional(readOnly = false)
    public String delete(@PathVariable int customerCode) {

        boolean result;

        Customer customer = customerService.findCustomer(customerCode);

        // TODO：エラー処理

        result = customerService.delete(customer);

        return "customerCode：" + customerCode + "を削除しました。";
    }

}
