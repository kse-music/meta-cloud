package com.hiekn.metacloud.monitor;

import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

//@Configuration
//@EnableScheduling
public class NotifierConfiguration {

    @Autowired
    private Notifier delegate;

    @Bean
    @Primary
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remindingNotifier = new RemindingNotifier(delegate);
        remindingNotifier.setReminderPeriod(TimeUnit.MINUTES.toMillis(10));
        return remindingNotifier;
    }

    @Scheduled(fixedRate = 600_000L)
    public void remind() {
        remindingNotifier().sendReminders();
    }

}
