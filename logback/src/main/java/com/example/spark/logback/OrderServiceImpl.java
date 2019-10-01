package com.example.spark.logback;

import lombok.extern.slf4j.Slf4j;


/**
 * @author xYang
 * @date 2019/9/19 0019 10:51
 * @purchase //TODO 一句话说明
 */
@Slf4j(topic = "order")
public class OrderServiceImpl implements IOrderService {
    @Override
    public void printPrice(String goodsId, int num) {
        log.info("goodsID[{}] price[{}].", goodsId, num * 100.2F);
    }
}
