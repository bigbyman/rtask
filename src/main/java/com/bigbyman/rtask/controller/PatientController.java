package com.bigbyman.rtask.controller;

import com.bigbyman.rtask.model.Patient;
import com.bigbyman.rtask.repository.PatientRepository;
import com.bigbyman.rtask.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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
        if(name.equals("null") && lastName.equals("null") && pesel.equals("null"))
            return this.getAllPatients();

        return this.patientRepository.findAllByNameStartingWithAndLastNameStartingWithAndPeselStartingWith(name, lastName, pesel);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        if (this.patientRepository.findById(id).isPresent()) {
            return this.patientRepository.findById(id).get();
        } else {
            return null; //TODO error
        }
    }

    @PostMapping
    public void savePatient(@RequestBody Patient patient) {
        this.patientRepository.save(patient);
    }

    @PatchMapping
    public void modifyPatient(@RequestParam("id") Long id,
                            @RequestBody Patient patient) {
        //TODO
    }

    @DeleteMapping
    public void deletePatient(@RequestParam("id") Long id) {
        this.patientRepository.deleteById(id);
    }
}
