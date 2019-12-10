package com.nbplus.vnpy.event;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/12/4 15:56
 * @Version 1.0
 **/
public class NotifyEventHandler implements EventHandler<NotifyEvent>, WorkHandler<NotifyEvent> {
    @Override
    public void onEvent(NotifyEvent notifyEvent, long l, boolean b) throws Exception {
        System.out.println("接收到消息");
        this.onEvent(notifyEvent);
    }

    @Override
    public void onEvent(NotifyEvent notifyEvent) throws Exception {
        System.out.println(notifyEvent+">>>"+ UUID.randomUUID().toString());
    }
}
