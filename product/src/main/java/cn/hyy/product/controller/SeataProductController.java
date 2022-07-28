package cn.hyy.product.controller;


import cn.hyy.product.service.SeataProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zyf
 */
@RestController
@RequestMapping("/test/seata/product")
public class SeataProductController {

    @Autowired
    private SeataProductService seataProductService;

    @GetMapping("/reduceStock")
    public BigDecimal reduceStock(@RequestParam Long productId, @RequestParam Integer count) {
        return seataProductService.reduceStock(productId, count);
    }
}
