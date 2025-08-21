package com.travelbooking.controller;

import com.travelbooking.dto.UserDTO;
import com.travelbooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void testGetCurrentUser() throws Exception {
        String mockJwt = "Bearer header.payload.signature";
        String mockEmail = "user@example.com";

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(mockEmail);
        userDTO.setFirstName("Ashish");
        userDTO.setLastName("Shenoy");
        userDTO.setPassportNumber("P1234567");
        userDTO.setAddress("India");
        userDTO.setPhone("9876543210");

        when(userService.getCurrentUserProfile(mockEmail)).thenReturn(userDTO);

        mockMvc.perform(get("/api/user/me").header("Authorization", mockJwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(mockEmail))
                .andExpect(jsonPath("$.firstName").value("Ashish"))
                .andExpect(jsonPath("$.lastName").value("Shenoy"))
                .andExpect(jsonPath("$.passportNumber").value("P1234567"))
                .andExpect(jsonPath("$.address").value("India"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }

    @Test
    public void testDeleteCurrentUser() throws Exception {
        String mockJwt = "Bearer header.payload.signature";
        String mockEmail = "user@example.com";

        doNothing().when(userService).deleteCurrentUserProfile(mockEmail);

        mockMvc.perform(delete("/api/user/me").header("Authorization", mockJwt))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));

        verify(userService, times(1)).deleteCurrentUserProfile(mockEmail);
    }
}
