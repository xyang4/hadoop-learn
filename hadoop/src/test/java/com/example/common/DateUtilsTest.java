package com.example.common;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @author xYang
 * @date 2019/9/12 0012 18:01
 * @purchase //TODO 一句话说明
 */
public class DateUtilsTest {
    /**
     * pattern 参考： https://blog.csdn.net/yaomingyang/article/details/79143954
     */
    @Test
    public void formatTest() {
        System.out.println(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
    }
}
