package com.nbplus.vnpy.event;

import com.nbplus.vnpy.method.Method;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/20 17:27
 * @Version 1.0
 **/
public class classDemo2 {
    public static void main(String[] args) {
        classDemo2 classDemo2 = new classDemo2();
        Method method = new Method(classDemo2,"simpletest",classDemo2.getClass());

    }
    public void simpletest(Event event) {
        System.out.println("处理每秒触发的计时器事件：" + LocalDateTime.now());
    }
}
