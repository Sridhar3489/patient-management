package com.patient.patient_service.kafka;

import com.patient.patient_service.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    KafkaTemplate<String, byte[]> kafkaTemplate;

    public void sendEvent(Patient patient){

        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();
        try{

            log.info("Sending patient event to Kafka {}", patientEvent);
            kafkaTemplate.send("patient", patientEvent.toByteArray()); //for serialization, the event is serialized

        } catch (RuntimeException e) {

            log.error("Error sending patient event to Kafka {}", patientEvent);
            throw new RuntimeException(e);

        }
    }

}
