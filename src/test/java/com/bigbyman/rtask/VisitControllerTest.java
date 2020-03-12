package com.bigbyman.rtask;

import com.bigbyman.rtask.controller.VisitController;
import com.bigbyman.rtask.model.Patient;
import com.bigbyman.rtask.model.Visit;
import com.bigbyman.rtask.repository.PatientRepository;
import com.bigbyman.rtask.repository.VisitRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VisitController.class)
public class VisitControllerTest {
    @MockBean
    VisitRepository visitRepository;
    @MockBean
    PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

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
        visit.setId(1L);
        visit.setInterviewExamination("Talked about stuff");
        visit.setPhysicalExamination("Touched some stuff");
        visit.setDiagnosis("Thought some stuff");
        visit.setTreatment("Done some stuff, but patient died while thinking");
        visit.setDiagnosis("Thinking kills people");

        visit.setLocalDate(LocalDate.of(2020, 3, 12));
        visitList = Arrays.asList(visit);

        Mockito.when(patientRepository.findByPesel(patient.getPesel())).thenReturn(Optional.of(patient));
        Mockito.when(visitRepository.findAll()).thenReturn(visitList);
        Mockito.when(visitRepository.findById(visit.getId())).thenReturn(Optional.ofNullable(visit));
    }

    @Test
    public void getAllVisits() throws Exception {
        mockMvc.perform(get("/visit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].patient.name", is(patient.getName())))
                .andExpect(jsonPath("$[0].diagnosis", is("Thinking kills people")))
                .andExpect(jsonPath("$[0].localDate", is("12.03.2020")));
    }

    @Test
    public void getVisitById() throws Exception {
        Long visitId = 1L;

        mockMvc.perform(get("/visit/" + visitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.localDate", is("12.03.2020")))
                .andExpect(jsonPath("$.patient.name", is(patient.getName())));
    }

    @Test
    public void whenFindByNonexistentPatientPesel_shouldThrowException() throws Exception {
        String pesel = "00110054321";
        mockMvc.perform(get("/visit/forPatient?pesel=" + pesel))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(is("Patient of pesel " + pesel + " does not exist")));

    }
}
