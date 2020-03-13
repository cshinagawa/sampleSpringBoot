package com.wiz.sample.app.customer;

import com.wiz.sample.domain.dto.CustomerDto;
import com.wiz.sample.domain.model.Customer;
import com.wiz.sample.domain.model.Order;
import com.wiz.sample.domain.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * カスタマー コントローラークラス。
 * <br>
 * （SpringBootのURLマッピングにより特定のコントローラーにハンドリングされます。）
 *
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
    public Map findAll() {

        Map customerMap = this.getAllCustomer();

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

        Map customerMap = this.getCustomer(customerCode);

        return customerMap;
    }

    /**
     * カスタマー情報登録 コントローラー
     *
     * @param resource カスタマー登録情報
     * @return 登録結果
     */
    @RequestMapping(path="/customer/add", method= RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false)
    public Map insert(@RequestBody Customer resource){

        // TODO：入力エラーはコントローラーで処理する。
        Customer customer = new Customer();
        customer.setCustomerName(resource.getCustomerName());
        customer.setAccount(resource.getAccount());

        // エラーがなければ登録。
        Map customerMap = this.insertCustomer(customer);

        return customerMap;
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
    public Map update(@RequestBody Customer resource){

        // TODO：SQL実行エラー処理
        Customer customer = new Customer();
        customer.setCustomerCode(resource.getCustomerCode());
        customer.setCustomerName(resource.getCustomerName());
        customer.setAccount(resource.getAccount());

        // エラーがなければ更新。
        Map customerMap = this.updateCustomer(customer);

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

        Map customerMap = this.deleteCustomer(customerCode);

        return customerMap;
    }

    /**
     * カスタマー情報 取得メソッド。
     * <br>
     * カスタマー情報を取得し、レスポンス用に変換します。
     *
     * @return カスタマー情報
     */
    private Map getCustomer(int customerCode) {

        Map customerMap = new HashMap<>();

        CustomerDto dto = new CustomerDto();
        Customer customer = customerService.findCustomer(customerCode);

        BeanUtils.copyProperties(customer, dto);

        customerMap.put("errorCode", null);
        customerMap.put("customer", dto);

        return customerMap;
    }

    /**
     * カスタマーリスト 取得メソッド。
     * <br>
     * カスタマーリストを取得し、レスポンス用に変換します。
     *
     * @return カスタマーリスト
     */
    private Map getAllCustomer() {

        Map customerMap = new HashMap<>();

        List customerList = customerService.findAllCustomer();

        List<CustomerDto> dtoList = new ArrayList<>();
        for(Object o : customerList) {
            CustomerDto dto = new CustomerDto();
            BeanUtils.copyProperties(o, dto);
            dtoList.add(dto);
        }

        customerMap.put("errorCode", null);
        customerMap.put("customers", dtoList);

        return customerMap;
    }

    /**
     * カスタマー 登録メソッド。
     * <br>
     * カスタマー情報を登録し、レスポンスデータを返します。
     *
     * @return 登録結果
     */
    private Map insertCustomer(Customer customer) {

        Map customerMap = new HashMap<>();

        Customer result = customerService.insert(customer);
        CustomerDto dto = new CustomerDto();
        BeanUtils.copyProperties(result, dto);

        customerMap.put("errorCode", null);
        customerMap.put("customer", dto);

        return customerMap;
    }

    /**
     * カスタマー 更新メソッド。
     * <br>
     * カスタマー情報を更新し、レスポンスデータを返します。
     *
     * @return 更新結果
     */
    private Map updateCustomer(Customer customer) {

        Map customerMap = new HashMap<>();

        Customer result = customerService.update(customer);
        CustomerDto dto = new CustomerDto();
        BeanUtils.copyProperties(result, dto);

        customerMap.put("errorCode", null);
        customerMap.put("customer", dto);

        return customerMap;
    }

    /**
     * カスタマー 削除メソッド。
     * <br>
     * カスタマー情報を削除し、レスポンスデータを返します。
     *
     * @return 削除結果
     */
    private Map deleteCustomer(int customerCode) {

        Map customerMap = new HashMap<>();

        Customer customer = customerService.findCustomer(customerCode);
        customerService.delete(customer);

        CustomerDto dto = new CustomerDto();
        BeanUtils.copyProperties(customer, dto);

        customerMap.put("errorCode", null);
        customerMap.put("customer", dto);

        return customerMap;
    }

}
