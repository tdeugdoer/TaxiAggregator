package com.tserashkevich.driverservice.kafka;

import com.tserashkevich.driverservice.kafka.kafkaDtos.ChangeDriverStatusEvent;
import com.tserashkevich.driverservice.services.DriverService;
import com.tserashkevich.driverservice.utils.LogList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChangeStatusConsumer {
    private final DriverService driverService;

    @KafkaListener(topics = "${spring.kafka.consumer.change-driver-status-topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void handle(ChangeDriverStatusEvent changeDriverStatusEvent) {
        log.info(LogList.RECEIVED_EVENT, changeDriverStatusEvent);
        driverService.changeAvailableStatus(changeDriverStatusEvent.getDriverId(),
                changeDriverStatusEvent.getAvailable());
    }
}
