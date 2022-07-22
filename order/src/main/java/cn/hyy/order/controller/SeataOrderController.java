package cn.hyy.order.controller;

/**
 * @Description: TODO
 * @author: zyf
 * @date: 2022/01/24
 * @version: V1.0
 */


import cn.hyy.order.dto.PlaceOrderRequest;
import cn.hyy.order.service.SeataOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/seata/order")
public class SeataOrderController {

    @Autowired
    private SeataOrderService orderService;

    /**
     * 自由下单
     */
    @PostMapping("/placeOrder")
    public String placeOrder(@Validated @RequestBody PlaceOrderRequest request) {
        orderService.placeOrder(request);
        return "下单成功";
    }

    /**
     * 测试商品库存不足-异常回滚
     */
    @PostMapping("/test1")
    public String test1() {
        //商品单价10元，库存20个,用户余额50元，模拟一次性购买22个。 期望异常回滚
        orderService.placeOrder(new PlaceOrderRequest(1L, 1L, 22));
        return "下单成功";
    }

    /**
     * 测试用户账户余额不足-异常回滚
     */
    @PostMapping("/test2")
    public String test2() {
        //商品单价10元，库存20个，用户余额50元，模拟一次性购买6个。 期望异常回滚
        orderService.placeOrder(new PlaceOrderRequest(1L, 1L, 6));
        return "下单成功";
    }
}