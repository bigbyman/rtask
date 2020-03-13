package com.bigbyman.rtask;

import com.bigbyman.rtask.controller.PatientController;
import com.bigbyman.rtask.model.Patient;
import com.bigbyman.rtask.repository.PatientRepository;
import com.bigbyman.rtask.repository.VisitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @MockBean
    PatientRepository patientRepository;
    @MockBean
    VisitRepository visitRepository;

    @Autowired
    private MockMvc mockMvc;

    Patient patient = new Patient("Jan", "Kowalski", "11223312345");
    Patient patientZero = new Patient("Covid", "Divoc", "00000000000");

    @Test
    public void getAllPatients() throws Exception {
        List<Patient> list = Arrays.asList(patient);
        given(patientRepository.findAll()).willReturn(list);

        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(patient.getName())))
                .andExpect(jsonPath("$[0].lastName", is(patient.getLastName())))
                .andExpect(jsonPath("$[0].pesel", is(patient.getPesel())));

    }

    @Test
    public void partialSearch() throws Exception {
        List<Patient> list = new ArrayList<>();
        list.add(patient);
        given(patientRepository.findAllByNameStartingWithAndLastNameStartingWithAndPeselStartingWithAllIgnoreCase("Jan", "", "")).willReturn(list);

        mockMvc.perform(get("/patient/query?name=Jan&lastName=&pesel="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(patient.getName())))
                .andExpect(jsonPath("$[0].lastName", is(patient.getLastName())))
                .andExpect(jsonPath("$[0].pesel", is(patient.getPesel())));
    }

    @Test
    public void partialSearchFail() throws Exception {
        List<Patient> list = Arrays.asList(patient);
        given(patientRepository.findAllByNameStartingWithAndLastNameStartingWithAndPeselStartingWithAllIgnoreCase("Jan", "", "")).willReturn(list);

        mockMvc.perform(get("/patient/query?name=Jan&lastName=&pesel=null"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void checkGetStatusOnEmptyDatabase() throws Exception {
        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
