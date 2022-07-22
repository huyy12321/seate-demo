package cn.hyy.product.service.impl;

import cn.hyy.product.entity.SeataProduct;
import cn.hyy.product.mapper.SeataProductMapper;
import cn.hyy.product.service.SeataProductService;
import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description: 产品服务类
 * @author: zyf
 * @date: 2022/01/24
 * @version: V1.0
 */
@Slf4j
@Service
public class SeataProductServiceImpl implements SeataProductService {

    @Resource
    private SeataProductMapper productMapper;

    /**
     * 事务传播特性设置为 REQUIRES_NEW 开启新的事务
     */
    @DS("product")
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public BigDecimal reduceStock(Long productId, Integer count) {
        log.info("=============PRODUCT START=================");
        // 检查库存
        SeataProduct product = productMapper.selectById(productId);
        Assert.notNull(product, "商品不存在");
        Integer stock = product.getStock();
        log.info("商品编号为 {} 的库存为{},订单商品数量为{}", productId, stock, count);

        if (stock < count) {
            log.warn("商品编号为{} 库存不足，当前库存:{}", productId, stock);
            throw new RuntimeException("库存不足");
        }
        log.info("开始扣减商品编号为 {} 库存,单价商品价格为{}", productId, product.getPrice());
        // 扣减库存
        int currentStock = stock - count;
        product.setStock(currentStock);
        productMapper.updateById(product);
        BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(count));
        log.info("扣减商品编号为 {} 库存成功,扣减后库存为{}, {} 件商品总价为 {} ", productId, currentStock, count, totalPrice);
        log.info("=============PRODUCT END=================");
        return totalPrice;
    }
}