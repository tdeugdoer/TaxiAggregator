package com.tserashkevich.passengerservice.unit;

import com.querydsl.core.types.Predicate;
import com.tserashkevich.passengerservice.dtos.FindAllParams;
import com.tserashkevich.passengerservice.dtos.PageResponse;
import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.exceptions.PassengerNotFoundException;
import com.tserashkevich.passengerservice.mappers.PassengerMapper;
import com.tserashkevich.passengerservice.models.Passenger;
import com.tserashkevich.passengerservice.repositories.PassengerRepository;
import com.tserashkevich.passengerservice.services.impl.PassengerServiceImpl;
import com.tserashkevich.passengerservice.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceUnitTest {
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private PassengerMapper passengerMapper;
    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Test
    void givenNonExistingPassenger_whenCreatePassenger_thenCreateNewPassenger() {
        PassengerRequest passengerRequest = TestUtil.getPassengerRequest();
        Passenger passenger = TestUtil.getNonSavedPassenger();
        PassengerResponse passengerResponse = TestUtil.getPassengerResponse();

        doReturn(passenger)
                .when(passengerMapper)
                .toModel(passengerRequest);
        doAnswer(invocation -> {
            passenger.setId(TestUtil.ID);
            return null;
        }).when(passengerRepository).save(passenger);
        doReturn(passengerResponse)
                .when(passengerMapper)
                .toResponse(passenger);

        PassengerResponse result = passengerService.create(passengerRequest);

        verify(passengerMapper).toModel(passengerRequest);
        verify(passengerRepository).save(passenger);
        verify(passengerMapper).toResponse(passenger);
        assertThat(result).isEqualTo(passengerResponse);
    }

    @Test
    void givenExistingPassenger_whenEditPassenger_thenUpdatePassenger() {
        PassengerRequest passengerRequest = TestUtil.getEditPassengerRequest();
        Passenger passenger = TestUtil.getPassenger();
        PassengerResponse passengerResponse = TestUtil.getEditPassengerResponse();

        doReturn(Optional.of(passenger))
                .when(passengerRepository)
                .findById(TestUtil.ID);
        doAnswer(invocation -> {
            TestUtil.updatePassenger(passenger, passengerRequest);
            return null;
        }).when(passengerMapper).updateModel(passenger, passengerRequest);
        doReturn(passenger)
                .when(passengerRepository)
                .save(passenger);
        doReturn(passengerResponse)
                .when(passengerMapper)
                .toResponse(passenger);

        PassengerResponse result = passengerService.update(TestUtil.ID, passengerRequest);

        verify(passengerRepository).findById(TestUtil.ID);
        verify(passengerMapper).updateModel(passenger, passengerRequest);
        verify(passengerRepository).save(passenger);
        verify(passengerMapper).toResponse(passenger);
        assertThat(result).isEqualTo(passengerResponse);
    }

    @Test
    void givenNonExistingPassenger_whenEditPassenger_thenThrowException() {
        PassengerRequest passengerRequest = TestUtil.getPassengerRequest();

        doReturn(Optional.empty())
                .when(passengerRepository)
                .findById(TestUtil.ID);

        assertThatThrownBy(() -> passengerService.update(TestUtil.ID, passengerRequest))
                .isInstanceOf(PassengerNotFoundException.class);

        verify(passengerRepository).findById(TestUtil.ID);
        verifyNoMoreInteractions(passengerRepository);
    }

    @Test
    void givenExistingPassenger_whenDeletePassenger_thenDeletePassenger() {
        Passenger passenger = TestUtil.getPassenger();

        doReturn(Optional.of(passenger))
                .when(passengerRepository)
                .findById(passenger.getId());
        doNothing()
                .when(passengerRepository)
                .delete(passenger);

        passengerService.delete(passenger.getId());

        verify(passengerRepository).findById(passenger.getId());
        verify(passengerRepository).delete(passenger);
    }

    @Test
    void givenNonExistingPassenger_whenDeletePassenger_thenThrowException() {
        doReturn(Optional.empty())
                .when(passengerRepository)
                .findById(TestUtil.ID);

        assertThatThrownBy(() -> passengerService.delete(TestUtil.ID))
                .isInstanceOf(PassengerNotFoundException.class);

        verify(passengerRepository).findById(TestUtil.ID);
        verifyNoMoreInteractions(passengerRepository);
    }

    @Test
    void givenValidParams_whenFindPassengers_thenReturnPageOfPassengerResponse() {
        FindAllParams findAllParams = TestUtil.getFindAllParams();
        Predicate predicate = TestUtil.getPredicate();
        PageRequest pageRequest = TestUtil.getPageRequest();
        Page<Passenger> passengerPage = TestUtil.getPageOfPassengers();
        List<PassengerResponse> passengerResponses = TestUtil.getPassengerResponses();
        PageResponse<PassengerResponse> passengerPageResponse = TestUtil.getPageResponse();

        doReturn(passengerPage)
                .when(passengerRepository)
                .findAll(predicate, pageRequest);
        doReturn(passengerResponses)
                .when(passengerMapper)
                .toResponses(passengerPage.getContent());

        PageResponse<PassengerResponse> result = passengerService.findAll(findAllParams);

        verify(passengerRepository).findAll(predicate, pageRequest);
        assertThat(result).usingRecursiveComparison().isEqualTo(passengerPageResponse);
    }

    @Test
    void givenExistingPassenger_whenFindByIdPassenger_thenReturnPassengerResponse() {
        Passenger passenger = TestUtil.getPassenger();
        PassengerResponse passengerResponse = TestUtil.getPassengerResponse();

        doReturn(Optional.of(passenger))
                .when(passengerRepository)
                .findById(passenger.getId());
        doReturn(passengerResponse)
                .when(passengerMapper)
                .toResponse(passenger);

        passengerService.findById(passenger.getId());

        verify(passengerRepository).findById(passenger.getId());
    }

    @Test
    void givenNonExistingPassenger_whenFindByIdPassenger_thenThrowException() {
        doReturn(Optional.empty())
                .when(passengerRepository)
                .findById(TestUtil.ID);

        assertThatThrownBy(() -> passengerService.findById(TestUtil.ID))
                .isInstanceOf(PassengerNotFoundException.class);

        verify(passengerRepository).findById(TestUtil.ID);
        verifyNoMoreInteractions(passengerRepository);
    }

    @Test
    void givenExistingPassenger_whenExistByIdPassenger_thenTrue() {
        doReturn(true)
                .when(passengerRepository)
                .existsById(TestUtil.ID);

        Boolean result = passengerService.existById(TestUtil.ID);

        verify(passengerRepository).existsById(TestUtil.ID);
        verifyNoMoreInteractions(passengerRepository);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void givenNonExistingPassenger_whenExistByIdPassenger_thenFalse() {
        doReturn(false)
                .when(passengerRepository)
                .existsById(TestUtil.ID);

        Boolean result = passengerService.existById(TestUtil.ID);

        verify(passengerRepository).existsById(TestUtil.ID);
        verifyNoMoreInteractions(passengerRepository);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void givenExistingPassenger_whenExistByPhoneNumberPassenger_thenTrue() {
        doReturn(true)
                .when(passengerRepository)
                .existsByPhoneNumber(TestUtil.PHONE);

        Boolean result = passengerService.existByPhoneNumber(TestUtil.PHONE);

        verify(passengerRepository).existsByPhoneNumber(TestUtil.PHONE);
        verifyNoMoreInteractions(passengerRepository);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void givenNonExistingPassenger_whenExistByPhoneNumberPassenger_thenFalse() {
        doReturn(false)
                .when(passengerRepository)
                .existsByPhoneNumber(TestUtil.PHONE);

        Boolean result = passengerService.existByPhoneNumber(TestUtil.PHONE);

        verify(passengerRepository).existsByPhoneNumber(TestUtil.PHONE);
        verifyNoMoreInteractions(passengerRepository);
        assertThat(result).isEqualTo(false);
    }
}
