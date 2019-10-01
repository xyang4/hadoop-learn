package com.example.spark;

/**
 * @author xYang
 * @date 2019/9/23 0023 10:42
 * @purchase //TODO 一句话说明
 */
public class Test {
    public static void main(String[] args) {
        System.out.println((null + ""));
        System.out.println(tryHandle());
    }

    private static String tryHandle() {
        String r = null;
        try {
            return "t";
        } finally {
            System.out.println("F");
        }
    }
}
