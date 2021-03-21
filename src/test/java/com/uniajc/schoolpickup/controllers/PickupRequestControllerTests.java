package com.uniajc.schoolpickup.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.services.PickupRequestService;
import com.uniajc.schoolpickup.util.JsonUtil;
import com.uniajc.schoolpickup.util.Mocker;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.uniajc.schoolpickup.security.WithMockAuthorityUser;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PickupRequestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PickupRequestService pickupRequestService;

    @Test
    @WithMockAuthorityUser("admin")
    public void test_FindAllEntities_Admin_Success() throws Exception {
        List<PickupRequest> pickupRequests = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            pickupRequests.add(Mocker.getPickupRequest(Long.valueOf(i)));
        }

        // Service mock definition
        given(pickupRequestService.findAllEntities()).willReturn(pickupRequests);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/pickup-requests") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        String jsonResponse = result.getResponse().getContentAsString();
        List<PickupRequest> foundPickupRequests = JsonUtil.toInstances(jsonResponse,
                new TypeReference<List<PickupRequest>>() {
                });

        assertTrue(pickupRequests.containsAll(foundPickupRequests));

        // Controller call should reach the service
        verify(pickupRequestService).findAllEntities();
    }

    @WithMockAuthorityUser("parent")
    public void test_FindAllEntities_Parent_Success() throws Exception {
        List<PickupRequest> pickupRequests = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            pickupRequests.add(Mocker.getPickupRequest(Long.valueOf(i)));
        }

        // Service mock definition
        given(pickupRequestService.findAllEntities()).willReturn(pickupRequests);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/pickup-requests") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        String jsonResponse = result.getResponse().getContentAsString();
        List<PickupRequest> foundPickupRequests = JsonUtil.toInstances(jsonResponse,
                new TypeReference<List<PickupRequest>>() {
                });

        assertTrue(pickupRequests.containsAll(foundPickupRequests));

        // Controller call should reach the service
        verify(pickupRequestService).findAllEntities();
    }

    public void test_FindAllEntities_Anonymous_Failure() throws Exception {
        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/pickup-requests") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }

    @Test
    @WithMockAuthorityUser("admin")
    public void test_GetById_Admin_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        Optional<PickupRequest> optionalPickupRequest = Optional.of(pickupRequest);

        // Service mock definition
        given(pickupRequestService.findEntityById(isA(Long.class))).willReturn(optionalPickupRequest);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/pickup-requests/" + pickupRequest.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        PickupRequest foundPickupRequest = JsonUtil.toInstance(result.getResponse().getContentAsString(),
                PickupRequest.class);
        assertTrue(pickupRequest.equals(foundPickupRequest));

        // Controller call should reach the service
        verify(pickupRequestService).findEntityById(isA(Long.class));
    }

    @Test
    @WithMockAuthorityUser("parent")
    public void test_GetById_Parent_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        Optional<PickupRequest> optionalPickupRequest = Optional.of(pickupRequest);

        // Service mock definition
        given(pickupRequestService.findEntityById(isA(Long.class))).willReturn(optionalPickupRequest);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/pickup-requests/" + pickupRequest.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        PickupRequest foundPickupRequest = JsonUtil.toInstance(result.getResponse().getContentAsString(),
                PickupRequest.class);
        assertTrue(pickupRequest.equals(foundPickupRequest));

        // Controller call should reach the service
        verify(pickupRequestService).findEntityById(isA(Long.class));
    }

    @Test
    public void test_GetById_Anonymous_Failure() throws Exception {
        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/pickup-requests/1") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }

    @Test
    @WithMockAuthorityUser("admin")
    public void test_Add_Admin_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);

        // Service mock definition
        given(pickupRequestService.saveEntity(isA(PickupRequest.class))).willReturn(pickupRequest);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                post("/pickup-requests") //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(pickupRequest))) //
                .andExpect(status().isCreated()) //
                .andReturn();

        // Controller response assertions
        PickupRequest createdPickupRequest = JsonUtil.toInstance(result.getResponse().getContentAsString(),
                PickupRequest.class);
        assertTrue(pickupRequest.equals(createdPickupRequest));

        // Controller call should reach the service
        verify(pickupRequestService).saveEntity(isA(PickupRequest.class));
    }

    @Test
    @WithMockAuthorityUser("parent")
    public void test_Add_Parent_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);

        // Service mock definition
        given(pickupRequestService.saveEntity(isA(PickupRequest.class))).willReturn(pickupRequest);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                post("/pickup-requests") //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(pickupRequest))) //
                .andExpect(status().isCreated()) //
                .andReturn();

        // Controller response assertions
        PickupRequest createdPickupRequest = JsonUtil.toInstance(result.getResponse().getContentAsString(),
                PickupRequest.class);
        assertTrue(pickupRequest.equals(createdPickupRequest));

        // Controller call should reach the service
        verify(pickupRequestService).saveEntity(isA(PickupRequest.class));
    }

    @Test
    public void test_Add_Anonymous_Failure() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                post("/pickup-requests") //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(pickupRequest))) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }

    @Test
    @WithMockAuthorityUser("admin")
    public void test_Delete_Admin_Success() throws Exception {
        // Service mock definition
        doNothing().when(pickupRequestService).deleteEntity(isA(Long.class));

        mockMvc.perform( //
                delete("/pickup-requests/1") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isNoContent()) //
                .andReturn();

        // Controller call should reach the service
        verify(pickupRequestService).deleteEntity(isA(Long.class));
    }

    @Test
    @WithMockAuthorityUser("parent")
    public void test_Delete_Parent_Success() throws Exception {
        // Service mock definition
        doNothing().when(pickupRequestService).deleteEntity(isA(Long.class));

        mockMvc.perform( //
                delete("/pickup-requests/1") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isNoContent()) //
                .andReturn();

        // Controller call should reach the service
        verify(pickupRequestService).deleteEntity(isA(Long.class));
    }

    @Test
    public void test_Delete_Anonymous_Failure() throws Exception {
        mockMvc.perform( //
                delete("/pickup-requests/1") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }

    @Test
    @WithMockAuthorityUser("admin")
    public void test_Update_Admin_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        Optional<PickupRequest> optionalPickupRequest = Optional.of(pickupRequest);

        // Service mock definition
        given(pickupRequestService.updateEntity(isA(Long.class), isA(PickupRequest.class)))
                .willReturn(optionalPickupRequest);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                put("/pickup-requests/" + pickupRequest.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(pickupRequest))) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        PickupRequest updatedPickupRequest = JsonUtil.toInstance(result.getResponse().getContentAsString(),
                PickupRequest.class);
        assertTrue(pickupRequest.equals(updatedPickupRequest));

        // Controller call should reach the service
        verify(pickupRequestService).updateEntity(isA(Long.class), isA(PickupRequest.class));
    }

    @Test
    @WithMockAuthorityUser("parent")
    public void test_Update_Parent_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        Optional<PickupRequest> optionalPickupRequest = Optional.of(pickupRequest);

        // Service mock definition
        given(pickupRequestService.updateEntity(isA(Long.class), isA(PickupRequest.class)))
                .willReturn(optionalPickupRequest);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                put("/pickup-requests/" + pickupRequest.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(pickupRequest))) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        PickupRequest updatedPickupRequest = JsonUtil.toInstance(result.getResponse().getContentAsString(),
                PickupRequest.class);
        assertTrue(pickupRequest.equals(updatedPickupRequest));

        // Controller call should reach the service
        verify(pickupRequestService).updateEntity(isA(Long.class), isA(PickupRequest.class));
    }

    @Test
    public void test_Update_Anonymous_Failure() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                put("/pickup-requests/" + pickupRequest.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(pickupRequest))) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }
}
