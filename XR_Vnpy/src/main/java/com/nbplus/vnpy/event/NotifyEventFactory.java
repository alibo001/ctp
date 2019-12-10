package com.nbplus.vnpy.event;

import com.lmax.disruptor.EventFactory;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/12/4 15:55
 * @Version 1.0
 **/
public class NotifyEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new NotifyEvent();
    }
}
