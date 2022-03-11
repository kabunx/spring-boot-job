package com.kabunx.app.listener;

import com.kabunx.app.event.MyEvent;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements ApplicationListener<MyEvent> {

    @SneakyThrows
    @Override
    public void onApplicationEvent(MyEvent event) {
        Object source = event.getSource();
        System.out.println(source);
        Thread.sleep(5000);
    }
}
