package com.example.spark.logback;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xYang
 * @date 2019/9/19 0019 10:21
 * @purchase //TODO 一句话说明
 */
@Slf4j
public class LoggerMain {
    public static void main(String[] args) {
        log.trace("----- trace -----");
        log.info("----- Begin Calculation Order Price ： Begin -----");
        IOrderService orderService = new OrderServiceImpl();
        orderService.printPrice("肥皂", 20);
        log.warn("----- warn -----");
        log.error("----- something error -----");
        log.info("----- Calculation Order Price ： END -----");
    }
}
