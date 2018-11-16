package com.example.host_0303.xposednani.util;

import java.util.Random;

public class RandomUtil {
    private final static Random RANDOM = new Random(System.currentTimeMillis());
    //随机返回一个整数值
    public static int randomInt(int size){
        return RANDOM.nextInt(size);
    }
}
