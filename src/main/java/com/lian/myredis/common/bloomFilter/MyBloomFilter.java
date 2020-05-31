package com.lian.myredis.common.bloomFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.BitSet;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/31 19:57
 */
@Component
public class MyBloomFilter {

    private static final int SIZE = 2<<10;

    private static final int[] num = new int[]{5,19,23,31,47,71};

    private BitSet bits = new BitSet(SIZE);

    private MyHash[] function = new MyHash[num.length];

    public MyBloomFilter(){
        for (int i=0;i<num.length;i++){
            function[i] = new MyHash(SIZE,num[i]);
        }
    }

    public void add(String value){
        for (MyHash f : function){
            bits.set(f.hash(value),true);
        }
    }

    public boolean contains(String value){
        if(value == null){
            return false;
        }
        boolean result = true;
        for (MyHash f : function){
            result = result&&bits.get(f.hash(value));
        }
        return result;
    }

    public static void main(String[] args) {
        String value = "hehehe";
        MyBloomFilter myBloomFilter = new MyBloomFilter();
        System.out.println(myBloomFilter.contains(value));
        myBloomFilter.add(value);
        System.out.println(myBloomFilter.contains(value));
    }
}
