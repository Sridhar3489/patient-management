package com.patient.analytics_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-service") //groupId is for partitioning
    public void consumeEvent(byte[] event) {
        try{
            log.info("Received event {}", event);
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("Event received {} {} {} {}", patientEvent.getEmail(), patientEvent.getEventType(), patientEvent.getPatientId(), patientEvent.getName(), patientEvent.getPatientId());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event {}", event);
            throw new RuntimeException(e);
        }

    }

}
