package com.lian.myredis.common.bloomFilter;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/31 19:59
 */
public class MyHash {
    private int capacity;
    private int seed;

    public MyHash(int cap,int seed){
        this.capacity = cap;
        this.seed = seed;
    }

    public int hash(String value){
        int result = 0;
        int len = value.length();
        for (int i = 0;i<len;i++){
            result = seed*result + value.charAt(i);
        }
        return (capacity-1)&result;
    }
}
