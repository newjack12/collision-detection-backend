package com.example.detectioncar;

import com.example.detectioncar.controller.AdminFamilyController;
import com.example.detectioncar.model.Family;
import com.example.detectioncar.repo.FamilyRepository;
import com.example.detectioncar.repo.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminFamilyController.class)
class AdminFamilyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FamilyRepository familyRepository;

    @MockBean
    private UserInfoRepository userInfoRepository;

    @Test
    void createFamily_whenFamilyIdAlreadyExists_returns400() throws Exception {
        when(familyRepository.findByFamilyId(123)).thenReturn(List.of(new Family()));

        mockMvc.perform(
                post("/api/admin/families")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"familyId\":123,\"name\":\"Test Family\"}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("familyId already exists"));

        verify(familyRepository, never()).save(any(Family.class));
    }
}
