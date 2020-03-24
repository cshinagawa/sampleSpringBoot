package com.wiz.sample.app.order;

import com.wiz.sample.domain.dto.OrderDto;
import com.wiz.sample.domain.model.Order;
import com.wiz.sample.domain.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Api(tags = "order")
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
    public Map findAll() {

        Map result = this.getAllOrder();

        return result;
    }

    /**
     * オーダー情報 取得メソッド。
     * <br>
     * オーダー情報を取得し、レスポンス用に変換します。
     *
     * @return オーダー情報
     */
    private Map getAllOrder() {

        Map orderMap = new HashMap<>();

        List orderList = orderService.findAllOrder();

        List<OrderDto> dtoList = new ArrayList<>();
        for(Object o : orderList) {
            OrderDto dto = new OrderDto();
            BeanUtils.copyProperties(o, dto);
            BeanUtils.copyProperties(((Order)o).getCustomer(), dto);
            dtoList.add(dto);
        }

        orderMap.put("errorCode", null);
        orderMap.put("orders", dtoList);

        return orderMap;
    }
}
