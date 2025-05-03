package com.patient.patient_service.service;

import com.patient.patient_service.dto.PatientResponseDTO;
import com.patient.patient_service.mapper.PatientMapper;
import com.patient.patient_service.model.Patient;
import com.patient.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

}
