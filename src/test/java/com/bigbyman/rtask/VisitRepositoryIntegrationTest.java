package com.bigbyman.rtask;

import com.bigbyman.rtask.model.Patient;
import com.bigbyman.rtask.model.Visit;
import com.bigbyman.rtask.repository.VisitRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VisitRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private VisitRepository visitRepository;

    Visit visit;
    List<Visit> visitList;
    Patient patient;
    List<Patient> patientList;

    @Before
    public void setup() {
        patient = new Patient("Covid", "Divoc", "11001112345");
        patientList = Arrays.asList(patient);

        visit = new Visit();
        visit.setPatient(patient);
        visit.setInterviewExamination("Talked about stuff");
        visit.setPhysicalExamination("Touched some stuff");
        visit.setDiagnosis("Thought some stuff");
        visit.setTreatment("Done some stuff, but patient died while thinking");
        visit.setDiagnosis("Thinking kills people");

        visit.setLocalDate(LocalDate.of(2020, 3, 12));
        visitList = Arrays.asList(visit);
    }

    @Test
    public void whenFindByPatient_thenReturnVisits() {
        testEntityManager.persist(patient);
        testEntityManager.persist(visit);
        testEntityManager.flush();

        List<Visit> visitsFound = visitRepository.findAllByPatient(patient);

        assertThat(visitsFound.size()).isEqualTo(1);
        assertThat(visitsFound.get(0).getPatient().getName()).isEqualTo(patient.getName());
    }


}
