package com.qiguang.wanandroid;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-19 下午5:26
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */

public class LambdaTest {
    @Test
    public void test1(){
        sayHello(100,(x)-> System.out.println(x+=x));
    }

    @Test
    public void test2(){
        testSupplier(()-> 100*100);
    }

    @Test
    public void test3(){
        Integer integer = testFunction(10000, (x) -> x * x);
        System.out.println(integer);
    }

    @Test
    public void test4(){
        Boolean b = testPredicate(1000, x -> x >= 1000);
        System.out.println(b);
    }

    /**
     * 消费型接口
     * @param num
     * @param consumer
     */
    private void sayHello(int num, Consumer<Integer> consumer){
        consumer.accept(num);
    }

    /**
     * 供给型接口
     * @param supplier
     */
    private void testSupplier(Supplier<Integer> supplier){
        System.out.println(supplier.get());
    }

    /**
     * 方法型接口
     * @param num
     * @param function
     * @return
     */
    private Integer testFunction(int num,Function<Integer,Integer> function){
        return function.apply(num);
    }

    /**
     * 断言型接口
     * @param num
     * @param predicate
     * @return
     */
    private Boolean testPredicate(int num, Predicate<Integer> predicate){
        return predicate.test(num);
    }
}
