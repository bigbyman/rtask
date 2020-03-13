package com.bigbyman.rtask.repository;

import com.bigbyman.rtask.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends BaseRepository<Patient> {
    List<Patient> findAll();
    Optional<Patient> findByPesel(String pesel);
    List<Patient> findAllByNameStartingWithAndLastNameStartingWithAndPeselStartingWithAllIgnoreCase(String name, String lastName, String pesel);
}
