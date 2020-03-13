package com.bigbyman.rtask.controller;

import com.bigbyman.rtask.model.Patient;
import com.bigbyman.rtask.repository.PatientRepository;
import com.bigbyman.rtask.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitRepository visitRepository;

    @GetMapping
    public List<Patient> getAllPatients() {
        return this.patientRepository.findAll();
    }

    @GetMapping("/query")
    public List<Patient> findByNameLastNamePesel(@RequestParam("name") String name,
                                                 @RequestParam("lastName") String lastName,
                                                 @RequestParam("pesel") String pesel) {

        return this.patientRepository.findAllByNameStartingWithAndLastNameStartingWithAndPeselStartingWithAllIgnoreCase(name, lastName, pesel);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        if (this.patientRepository.findById(id).isPresent()) {
            return this.patientRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Patient of id " + id + " does not exist");
        }
    }

    @PostMapping
    public Patient savePatient(@RequestBody Patient patient) {
        if (this.patientRepository.findByPesel(patient.getPesel()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "Patient of pesel " + patient.getPesel() + " already exists");
        }
        return this.patientRepository.save(patient);
    }

    @PutMapping
    public Patient putPatient(@RequestBody Patient patient) {
        if (this.patientRepository.findByPesel(patient.getPesel()).isPresent()) {
            if (!this.patientRepository.findByPesel(patient.getPesel()).get().getId().equals(patient.getId())) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, "Patient of pesel " + patient.getPesel() + " already exists");
            }
        }
        return this.patientRepository.save(patient);
    }

    @DeleteMapping
    public void deletePatient(@RequestParam("id") Long id) {
        try {
            this.patientRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "Patient has visits");
        }
    }
}
