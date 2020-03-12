package com.bigbyman.rtask.controller;

import com.bigbyman.rtask.model.Visit;
import com.bigbyman.rtask.repository.PatientRepository;
import com.bigbyman.rtask.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/visit")
public class VisitController {
    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public List<Visit> getAllVisits() {
        return this.visitRepository.findAll();
    }

    @GetMapping("/{id}")
    public Visit getVisitById(@PathVariable("id") Long id) {
        if (this.visitRepository.findById(id).isPresent()) {
            return this.visitRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Visit of id " + id + " does not exist");
        }
    }

    @GetMapping("/forPatient")
    public List<Visit> getVisitsForPatient(@RequestParam("pesel") String pesel) {
        if (this.patientRepository.findByPesel(pesel).isPresent()) {
            return this.visitRepository.findAllByPatient(
                    this.patientRepository.findByPesel(pesel).get()
            );
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Patient of pesel " + pesel + " does not exist");
        }
    }

    @GetMapping("/byDate")
    public List<Visit> getVisitsByDate(@RequestParam("date") String date) {
        if (date.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Date must be specified");
        }
        return this.visitRepository.findAllByLocalDateLike(LocalDate.parse(date));
    }

    @PostMapping
    public void saveVisit(@RequestBody Visit visit,
                          @RequestParam("id") Long patientId) {
        if (this.patientRepository.findById(patientId).isPresent()) {
            visit.setPatient(this.patientRepository.findById(patientId).get());
            this.visitRepository.save(visit);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Patient of id " + patientId + " does not exist");
        }
    }
}
