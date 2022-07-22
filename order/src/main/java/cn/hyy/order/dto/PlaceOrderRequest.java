package cn.hyy.order.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description: 订单请求对象
 * @author: zyf
 * @date: 2022/01/24
 * @version: V1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest {


    private Long userId;


    private Long productId;


    private Integer count;
}