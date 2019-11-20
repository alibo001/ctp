package com.nbplus.vnpy.event;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description  事件引擎
 * @Author gongtan
 * @Date 2019/11/20 15:15
 * @Version 1.0
 **/
public class EventEngine {

    /**
     * 事件引擎： 描述，有序且高效的处理所有事件的发生以及处理
     */


    //事件队列
    private LinkedBlockingQueue<Event> queue;

    // 事件引擎开关
    private boolean active;
    // 事件处理线程
    private Thread thread;
    // 计时器，用于触发计时器事件
    private Thread timer;
    // 计时器工作状态
    private boolean timerActive;
    // 计时器触发间隔（默认1秒）
    private int timerSleep;

    //这里的__handlers是一个字典，用来保存对应的事件调用关系
    //private Map<String, List<Method>> __handlers;

    //__generalHandlers是一个列表，用来保存通用回调函数（所有事件均调用）
    //private List<Method> __generalHandlers;


    /**
     * @Description 定时器事件 Timer事件的生成  此处为开辟的一个线程 与其他线程互不干扰 结束线程时，调用join方法 处理完再结束
     * @author gt_vv
     * @date 2019/11/20
     * @param
     * @return void
     */
    private void runTimer(){
        //判断引擎是否开启
        while(this.active) {
            //开启后生成 Timer事件并放入事件队列中等待被处理
            Event event = new Event(EventType.EVENT_TIMER);  //event 目前代表一个 Timer事件
            //放入对列中 等待事件的处理
            this.put(event);
            //设置线程睡眠 (默认1s) 后-->在执行此循环
            try {
                //timerSleep = 1000
                Thread.sleep(this.timerSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description 向事件队列中存入事件
     * @author gt_vv
     * @date 2019/11/20
     * @param event
     * @return void
     */
    public void put(Event event) {
        this.queue.offer(event);
    }

    /**
     * @Description 引擎运行线程   与事件生成线程互不干涉  结束时候调用 join 方法 执行完线程 再结束
     * @author gt_vv
     * @date 2019/11/20
     * @param
     * @return void
     */
    private  synchronized void engineRun(){
        //判断 引擎是否被开启
        while(this.active){
            try {
                //向队列索求  弹出一个事件（有序）
                Event event = this.queue.poll(1000, TimeUnit.MILLISECONDS);
                if (event == null) {
                    //事件为空 跳出本次循环 ，有事件 则进入事件处理方法
                    continue;
                }
                //this.process(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description  事件处理 主方法
     * @author gt_vv
     * @date 2019/11/20
     * @param event
     * @return void
     */
/*    private void process(Event event) {
        // 检查是否存在对该事件进行监听的处理函数
        if (this.__handlers.containsKey(event.getType_())) {
            // 若存在，则按顺序将事件传递给处理函数执行
            for (Method handler : this.__handlers.get(event.getType_())) {
                //执行 回调函数
                handler.invoke(event);
            }
        }

        // 调用通用处理函数进行处理
        if (this.__generalHandlers != null) {
            for (Method handler : this.__generalHandlers) {
                handler.invoke(event);
            }
        }
    }*/

}
