package com.bigbyman.rtask;

import com.bigbyman.rtask.model.Patient;
import com.bigbyman.rtask.repository.PatientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private PatientRepository patientRepository;

    Patient patient = new Patient("Covid", "Divoc", "11001154321");

    @Test
    public void whenFindByPesel_thenReturnPatient() {
        testEntityManager.persist(patient);
        testEntityManager.flush();

        Patient patientZero = patientRepository.findByPesel(patient.getPesel()).get();

        assertThat(patientZero.getName()).isEqualTo(patient.getName());
    }

    @Test
    public void whenPartialFindByNameLastNamePesel_thenReturnPatient() {

        testEntityManager.persist(patient);
        testEntityManager.flush();

        List<Patient> list = patientRepository
                .findAllByNameStartingWithAndLastNameStartingWithAndPeselStartingWith(patient.getName().substring(0, 3),
                        patient.getLastName().substring(0, 3),
                        patient.getPesel().substring(0, 6));

        Patient patientFound = list.get(0);

        assertThat(list.size()).isEqualTo(1);
        assertThat(patientFound.getName()).isEqualTo(patient.getName());
        assertThat(patientFound.getLastName()).isEqualTo(patient.getLastName());
        assertThat(patientFound.getPesel()).isEqualTo(patient.getPesel());
    }
}
