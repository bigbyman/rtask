package com.bigbyman.rtask.repository;

import com.bigbyman.rtask.model.Patient;
import com.bigbyman.rtask.model.Visit;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository extends BaseRepository<Visit> {
    List<Visit> findAll();
    List<Visit> findAllByPatient(Patient patient);
    List<Visit> findAllByLocalDateLike(LocalDate localDate);
}
