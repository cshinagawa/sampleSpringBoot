package com.wiz.sample.app.customer;

import com.wiz.sample.domain.model.Customer;
import com.wiz.sample.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * カスタマー コントローラークラス。
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
    public Map findAll() {

        List customerList = customerService.findAllCustomer();

        Map customerMap = new HashMap<>();
        customerMap.put("message", null);
        customerMap.put("customers", customerList);

        return customerMap;
    }

    /**
     * カスタマー情報取得 コントローラー
     *
     * @param customerCode　カスタマーコード
     * @return カスタマー情報リスト
     */
    @RequestMapping(path="/customer/{customerCode}", method= RequestMethod.GET)
    @ResponseBody
    public Map findByCustomerCode(@PathVariable int customerCode) {

        Customer customer = customerService.findCustomer(customerCode);

        Map customerMap = new HashMap<>();
        customerMap.put("message", null);
        customerMap.put("customer", customer);

        return customerMap;
    }

    /**
     * カスタマー情報登録 コントローラー
     *
     * @param resource カスタマー登録情報
     * @param result バリデーションチェック結果
     * @return 登録結果
     */
    @RequestMapping(path="/customer/add", method= RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false)
    public Map insert(@Validated@RequestBody Customer resource, BindingResult result){

        // 入力エラーの有無をチェック
        Map errors = this.isValid(result);
        if(errors != null) {
            errors.put("customer", resource);
            return errors;
        }

        // エラーがなければ登録。
        Customer customer = customerService.insert(resource);

        Map customerMap = new HashMap<>();
        customerMap.put("message", "登録されました。");
        customerMap.put("customer", customer);

        return customerMap;
    }

    /**
     * カスタマー情報更新 コントローラー
     *
     * @param resource カスタマー更新情報
     * @param result バリデーションチェック結果
     * @return 更新結果
     */
    @RequestMapping(path="/customer/edit", method= RequestMethod.PUT)
    @ResponseBody
    @Transactional(readOnly = false)
    public Map update(@Validated @RequestBody Customer resource, BindingResult result){

        // 入力エラーの有無をチェック
        Map errors = this.isValid(result);
        if(errors != null) {
            errors.put("customer", resource);
            return errors;
        }

        // エラーがなければ更新。

        Map customerMap = new HashMap<>();

        // TODO:存在チェック　Springのエラーハンドリングでできるっぽい。

        // 排他チェック
        Customer customer = customerService.findCustomerByRepository(resource.getCustomerCode());
        if(resource.getVersion() != customer.getVersion()) {
            customerMap.put("message", "排他エラー。もう一度、更新処理をやり直してください。");
            return customerMap;
        }

        // 更新
        customer = customerService.update(resource);

        customerMap.put("message", "更新されました。");
        customerMap.put("customer", customer);

        return customerMap;
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
    public Map delete(@PathVariable int customerCode) {

        Customer customer = customerService.findCustomer(customerCode);
        customerService.delete(customer);

        Map customerMap = new HashMap<>();
        customerMap.put("errorCode", null);
        customerMap.put("customer", customer);

        return customerMap;
    }

    /**
     * 入力チェックの有無により、入力エラーリストを返します。
     * @param result バリデーションチェック結果
     * @return エラー情報
     */
    private Map isValid(BindingResult result) {

        Map errors = null;

        if(result.hasErrors()) {
            List errorList = result.getFieldErrors();

            // TODO：エラー情報の形式
            errors.put("message", "入力エラーがあります。確認してください。");
            errors.put("errors", errorList);
        }

        return errors;
    }

}
