package com.wiz.sample.app.order;

import com.wiz.sample.domain.model.Order;
import com.wiz.sample.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * オーダー コントローラークラス。
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
public class OrderController {

    /**
     * データアクセスサービスクラス。
     */
    @Autowired
    OrderService orderService;

    /**
     * 初期化メソッド。
     */
    @PostConstruct
    public void init() {
        // 初期化処理など
    }

    /**
     * オーダー情報リスト取得 コントローラー
     *
     * @return オーダー情報リスト
     */
    @RequestMapping(path="/order", method= RequestMethod.GET)
    @ResponseBody
    public List<Order> findAll() {

        List result = orderService.findAllOrder();

        Order order = (Order) result.get(0);
        System.out.println(result.size());
        System.out.println(((Order) result.get(0)).getCustomer().getCustomerCode());

        for(Order o : result) {

        }

        return result;
    }
}
