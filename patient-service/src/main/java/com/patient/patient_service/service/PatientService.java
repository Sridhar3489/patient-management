package com.patient.patient_service.service;

import com.patient.patient_service.dto.PatientRequestDTO;
import com.patient.patient_service.dto.PatientResponseDTO;
import com.patient.patient_service.exception.EmailAlreadyExistsException;
import com.patient.patient_service.exception.PatientNotFoundException;
import com.patient.patient_service.mapper.PatientMapper;
import com.patient.patient_service.model.Patient;
import com.patient.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A Patient with this Email already exists "+patientRequestDTO.getEmail());
        }
        Patient patient = PatientMapper.toPatient(patientRequestDTO);
        patientRepository.save(patient);
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID uuid, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(uuid).orElseThrow(()->new PatientNotFoundException("Patient not found with the Id "+uuid));
//        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
//            throw new EmailAlreadyExistsException("A Patient with this Email already exists "+patientRequestDTO.getEmail());
//        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
//        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        Patient updatedPatient= patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);

    }

}
