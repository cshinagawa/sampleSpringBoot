package com.wiz.sample.app.customer;

import com.wiz.sample.domain.model.Customer;
import com.wiz.sample.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import java.util.ArrayList;
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

        Map responseData = new HashMap<>();
        if(customerList.size() > 0) {
            responseData.put("success", true);
            responseData.put("customers", customerList);
        } else {
            responseData.put("success", false);
            responseData.put("message", "該当するカスタマー情報はありませんでした。");
        }

        return responseData;
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

        Map responseData = new HashMap<>();
        Customer customer = new Customer();
        try {
            customer = customerService.findCustomer(customerCode);
        } catch(NoResultException e) {
            responseData.put("success", false);
            responseData.put("message", "該当するカスタマー情報はありませんでした。");
            return responseData;
        }

        responseData.put("success", true);
        responseData.put("customer", customer);

        return responseData;
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

        Map responseData = new HashMap<>();

        // 入力エラーの有無をチェック
        Map errors = this.isValid(result);
        if(errors.size() > 0) {
            responseData.put("success", false);
            responseData.put("message", errors.get("message"));
            responseData.put("errors", errors.get("errors"));
            responseData.put("customer", resource);
            return responseData;
        }

        // エラーがなければ登録。
        Customer customer = customerService.insert(resource);

        responseData.put("success", true);
        responseData.put("message", "登録されました。");
        responseData.put("customer", customer);

        return responseData;
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

        Map responseData = new HashMap<>();

        // 入力エラーの有無をチェック
        Map errors = this.isValid(result);
        if(errors.size() > 0) {
            responseData.put("success", false);
            responseData.put("message", errors.get("message"));
            responseData.put("errors", errors.get("errors"));
            responseData.put("customer", resource);
            return responseData;
        }

        // TODO:存在チェック　Springのエラーハンドリングでできるっぽい。

        // 排他チェック
        Customer customer = customerService.findCustomerByRepository(resource.getCustomerCode());
        if(resource.getVersion() != customer.getVersion()) {
            responseData.put("success", false);
            responseData.put("message", "排他エラー。もう一度、更新処理をやり直してください。");
            return responseData;
        }

        // エラーがなければ更新。
        customer = customerService.update(resource);

        responseData.put("success", true);
        responseData.put("message", "更新されました。");
        responseData.put("customer", customer);

        return responseData;
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

        Map responseData = new HashMap<>();
        responseData.put("success", true);
        responseData.put("message", "削除されました。");
        responseData.put("customer", customer);

        return responseData;
    }

    /**
     * 入力チェックの有無により、入力エラーリストを返します。
     * @param result バリデーションチェック結果
     * @return エラー情報
     */
    private Map isValid(BindingResult result) {

        Map errorMessage = new HashMap();

        if(result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();

            List errorList = new ArrayList();
            Map error = new HashMap();
            for(FieldError fieldError : errors) {
                error.put("field", fieldError.getField());
                error.put("message", fieldError.getDefaultMessage());
                errorList.add(error);
            }

            errorMessage.put("message", "入力エラーがあります。確認してください。");
            errorMessage.put("errors", errorList);
        }

        return errorMessage;
    }

}
