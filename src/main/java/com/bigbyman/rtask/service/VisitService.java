package com.bigbyman.rtask.service;

import com.bigbyman.rtask.repository.PatientRepository;
import com.bigbyman.rtask.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitService {
    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PatientRepository patientRepository;

}
