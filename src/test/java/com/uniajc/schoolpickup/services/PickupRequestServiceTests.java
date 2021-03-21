package com.uniajc.schoolpickup.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.repositories.PickupRequestRepository;
import com.uniajc.schoolpickup.util.Mocker;

@RunWith(MockitoJUnitRunner.class)
public class PickupRequestServiceTests {

    @Mock
    private PickupRequestRepository repository;

    @InjectMocks
    private PickupRequestService service;

    @Test
    public void test_FindAllEntities_Success() throws Exception {
        List<PickupRequest> pickupRequests = new ArrayList<PickupRequest>();
        for (int i = 0; i < 5; i++) {
            pickupRequests.add(Mocker.getPickupRequest(Long.valueOf(i)));
        }

        when(repository.findAll()).thenReturn(pickupRequests);

        List<PickupRequest> foundPickupRequests = service.findAllEntities();

        assertTrue(pickupRequests.containsAll(foundPickupRequests));

        verify(repository).findAll();
    }

    @Test
    public void test_FindEntityById_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        Optional<PickupRequest> optionalPickupRequest = Optional.of(pickupRequest);

        when(repository.findById(isA(Long.class))).thenReturn(optionalPickupRequest);

        Optional<PickupRequest> foundPickupRequest = service.findEntityById(1L);
        assertTrue(pickupRequest.equals(foundPickupRequest.get()));

        verify(repository).findById(isA(Long.class));
    }

    @Test
    public void test_SaveEntity_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);

        when(repository.save(any(PickupRequest.class))).thenReturn(pickupRequest);

        PickupRequest created = service.saveEntity(pickupRequest);

        assertThat(created.equals(pickupRequest));
        verify(repository).save(pickupRequest);
    }

    @Test
    public void test_DeleteEntity_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        Optional<PickupRequest> optionalPickupRequest = Optional.of(pickupRequest);

        when(repository.findById(isA(Long.class))).thenReturn(optionalPickupRequest);
        doNothing().when(repository).deleteById(isA(Long.class));

        service.deleteEntity(1L);

        verify(repository).findById(isA(Long.class));
        verify(repository).deleteById(isA(Long.class));
    }

    @Test
    public void test_UpdateEntity_Success() throws Exception {
        PickupRequest pickupRequest = Mocker.getPickupRequest(1L);
        Optional<PickupRequest> optionalPickupRequest = Optional.of(pickupRequest);

        when(repository.findById(isA(Long.class))).thenReturn(optionalPickupRequest);
        when(repository.save(isA(PickupRequest.class))).thenReturn(pickupRequest);

        Optional<PickupRequest> updatedPickupRequest = service.updateEntity(pickupRequest.getId(), pickupRequest);
        assertTrue(pickupRequest.equals(updatedPickupRequest.get()));

        verify(repository).findById(isA(Long.class));
        verify(repository).save(isA(PickupRequest.class));
    }
}
