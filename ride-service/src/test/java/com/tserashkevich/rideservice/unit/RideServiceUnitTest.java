package com.tserashkevich.rideservice.unit;

import com.tserashkevich.rideservice.dtos.*;
import com.tserashkevich.rideservice.exceptions.RideNotFinishedException;
import com.tserashkevich.rideservice.exceptions.RideNotFoundException;
import com.tserashkevich.rideservice.kafka.ChangeDriverStatusProducer;
import com.tserashkevich.rideservice.kafka.CreateRatingProducer;
import com.tserashkevich.rideservice.kafka.kafkaDtos.ChangeDriverStatusEvent;
import com.tserashkevich.rideservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.rideservice.mappers.RideMapper;
import com.tserashkevich.rideservice.models.Ride;
import com.tserashkevich.rideservice.repositories.RideRepository;
import com.tserashkevich.rideservice.services.impl.RideServiceImpl;
import com.tserashkevich.rideservice.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RideServiceUnitTest {
    @Mock
    private RideRepository rideRepository;
    @Mock
    private RideMapper rideMapper;
    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private CreateRatingProducer createRatingProducer;
    @Mock
    private ChangeDriverStatusProducer changeDriverStatusProducer;
    @InjectMocks
    private RideServiceImpl rideService;

    @Test
    void givenNonExistingRide_whenCreateRide_thenCreateNewRide() {
        CreateRideRequest rideRequest = TestUtil.getCreateRideRequest();
        Ride ride = TestUtil.getNonSavedRide();
        CreateRideResponse rideResponse = TestUtil.getCreateRideResponse();

        doReturn(ride)
                .when(rideMapper)
                .toModel(rideRequest);
        doAnswer(invocation -> {
            ride.setId(TestUtil.RIDE_ID);
            return null;
        }).when(rideRepository).save(ride);
        doReturn(rideResponse)
                .when(rideMapper)
                .toCreateRideResponse(ride);

        CreateRideResponse result = rideService.create(rideRequest);

        verify(rideMapper).toModel(rideRequest);
        verify(rideRepository).save(ride);
        verify(rideMapper).toCreateRideResponse(ride);
        assertThat(result).isEqualTo(rideResponse);
    }

    @Test
    void givenExistingRide_whenDeleteRide_thenDeleteRide() {
        Ride ride = TestUtil.getRide();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);
        doNothing()
                .when(rideRepository)
                .delete(ride);

        rideService.delete(ride.getId());

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verify(rideRepository).delete(ride);
    }

    @Test
    void givenNonExistingRide_whenDeleteRide_thenThrowException() {
        doReturn(Optional.empty())
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);

        assertThatThrownBy(() -> rideService.delete(TestUtil.RIDE_ID))
                .isInstanceOf(RideNotFoundException.class);

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verifyNoMoreInteractions(rideRepository);
    }

    @Test
    void givenValidParams_whenFindRides_thenReturnPageOfRideResponse() {
        FindAllParams findAllParams = TestUtil.getFindAllParams();
        Query findQuery = TestUtil.getFindQueryPredicate();
        List<Ride> rides = TestUtil.getRides();
        List<RideResponse> rideResponses = TestUtil.getRideResponses();
        PageResponse<RideResponse> ridePageResponse = TestUtil.getPageResponse(rideResponses);

        doReturn(rides)
                .when(mongoTemplate)
                .find(findQuery, Ride.class);
        doReturn(rideResponses)
                .when(rideMapper)
                .toRideResponses(rides);

        PageResponse<RideResponse> result = rideService.findAll(findAllParams);

        verify(mongoTemplate).find(findQuery, Ride.class);
        verify(rideMapper).toRideResponses(rides);
        assertThat(result).isEqualTo(ridePageResponse);
    }

    @Test
    void givenExistingRide_whenFindByIdRide_thenReturnRideResponse() {
        Ride ride = TestUtil.getRide();
        RideResponse rideResponse = TestUtil.getRideResponse();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);
        doReturn(rideResponse)
                .when(rideMapper)
                .toRideResponse(ride);

        rideService.findById(TestUtil.RIDE_ID);

        verify(rideRepository).findById(TestUtil.RIDE_ID);
    }

    @Test
    void givenNonExistingRide_whenFindByIdRide_thenThrowException() {
        doReturn(Optional.empty())
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);

        assertThatThrownBy(() -> rideService.findById(TestUtil.RIDE_ID))
                .isInstanceOf(RideNotFoundException.class);

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verifyNoMoreInteractions(rideRepository);
    }

    @Test
    void givenExistingRide_whenChangeDriver_thenReturnRideResponse() {
        Ride ride = TestUtil.getRide();
        ChangeDriverStatusEvent changeDriverStatusEvent = TestUtil.getChangeDriverStatusEvent();
        RideResponse rideResponse = TestUtil.getRideWithDriverResponse();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);
        doAnswer(invocation -> null).when(rideRepository).save(ride);
        doNothing()
                .when(changeDriverStatusProducer)
                .sendChangeStatusEvent(changeDriverStatusEvent);
        doReturn(rideResponse)
                .when(rideMapper)
                .toRideResponse(ride);

        RideResponse result = rideService.changeDriver(TestUtil.RIDE_ID, TestUtil.DRIVER_ID.toString());

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verify(rideRepository).save(ride);
        verify(changeDriverStatusProducer).sendChangeStatusEvent(changeDriverStatusEvent);
        verify(rideMapper).toRideResponse(ride);
        assertThat(result).isEqualTo(rideResponse);
    }

    @Test
    void givenNonExistingRide_whenChangeDriver_thenThrowException() {
        doReturn(Optional.empty())
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);

        assertThatThrownBy(() -> rideService.changeDriver(TestUtil.RIDE_ID, TestUtil.DRIVER_ID.toString()))
                .isInstanceOf(RideNotFoundException.class);

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verifyNoMoreInteractions(rideRepository);
    }

    @Test
    void givenExistingRide_whenChangeCar_thenReturnRideResponse() {
        Ride ride = TestUtil.getRide();
        RideResponse rideResponse = TestUtil.getRideWithCarResponse();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);
        doAnswer(invocation -> null).when(rideRepository).save(ride);
        doReturn(rideResponse)
                .when(rideMapper)
                .toRideResponse(ride);

        RideResponse result = rideService.changeCar(TestUtil.RIDE_ID, TestUtil.CAR_ID);

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verify(rideRepository).save(ride);
        verify(rideMapper).toRideResponse(ride);
        assertThat(result).isEqualTo(rideResponse);
    }

    @Test
    void givenNonExistingRide_whenChangeCar_thenThrowException() {
        doReturn(Optional.empty())
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);

        assertThatThrownBy(() -> rideService.changeCar(TestUtil.RIDE_ID, TestUtil.CAR_ID))
                .isInstanceOf(RideNotFoundException.class);

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verifyNoMoreInteractions(rideRepository);
    }

    @Test
    void givenExistingRide_whenCreateDriverComment_thenNothing() {
        CreateRatingRequest createRatingRequest = TestUtil.getFinishedRideCreateRatingRequest();
        Ride ride = TestUtil.getSecondRide();
        RatingCreateEvent ratingCreateEvent = TestUtil.getDriverRatingCreateEvent(createRatingRequest);

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(ride.getId());
        doNothing()
                .when(createRatingProducer)
                .sendRatingCreateEvent(ratingCreateEvent);

        rideService.createDriverComment(createRatingRequest);

        verify(rideRepository).findById(ride.getId());
        verify(createRatingProducer).sendRatingCreateEvent(ratingCreateEvent);
        verifyNoMoreInteractions(rideRepository, createRatingProducer);
    }

    @Test
    void givenNonExistingRide_whenCreateDriverComment_thenThrowException() {
        CreateRatingRequest createRatingRequest = TestUtil.getFinishedRideCreateRatingRequest();

        doReturn(Optional.empty())
                .when(rideRepository)
                .findById(createRatingRequest.getRideId());

        assertThatThrownBy(() -> rideService.createDriverComment(createRatingRequest))
                .isInstanceOf(RideNotFoundException.class);

        verify(rideRepository).findById(createRatingRequest.getRideId());
        verifyNoMoreInteractions(rideRepository);
    }

    @Test
    void givenNotFinishedRide_whenCreateDriverComment_thenThrowException() {
        CreateRatingRequest createRatingRequest = TestUtil.getNotFinishedRideCreateRatingRequest();
        Ride ride = TestUtil.getRide();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);

        assertThatThrownBy(() -> rideService.createDriverComment(createRatingRequest))
                .isInstanceOf(RideNotFinishedException.class);

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verifyNoMoreInteractions(rideRepository);
    }

    @Test
    void givenExistingRide_whenCreatePassengerComment_thenNothing() {
        CreateRatingRequest createRatingRequest = TestUtil.getFinishedRideCreateRatingRequest();
        Ride ride = TestUtil.getSecondRide();
        RatingCreateEvent ratingCreateEvent = TestUtil.getPassengerRatingCreateEvent(createRatingRequest);

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(ride.getId());
        doNothing()
                .when(createRatingProducer)
                .sendRatingCreateEvent(ratingCreateEvent);

        rideService.createPassengerComment(createRatingRequest);

        verify(rideRepository).findById(ride.getId());
        verify(createRatingProducer).sendRatingCreateEvent(ratingCreateEvent);
        verifyNoMoreInteractions(rideRepository, createRatingProducer);
    }

    @Test
    void givenNonExistingRide_whenCreatePassengerComment_thenThrowException() {
        CreateRatingRequest createRatingRequest = TestUtil.getFinishedRideCreateRatingRequest();

        doReturn(Optional.empty())
                .when(rideRepository)
                .findById(createRatingRequest.getRideId());

        assertThatThrownBy(() -> rideService.createPassengerComment(createRatingRequest))
                .isInstanceOf(RideNotFoundException.class);

        verify(rideRepository).findById(createRatingRequest.getRideId());
        verifyNoMoreInteractions(rideRepository);
    }

    @Test
    void givenNotFinishedRide_whenCreatePassengerComment_thenThrowException() {
        CreateRatingRequest createRatingRequest = TestUtil.getNotFinishedRideCreateRatingRequest();
        Ride ride = TestUtil.getRide();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(TestUtil.RIDE_ID);

        assertThatThrownBy(() -> rideService.createPassengerComment(createRatingRequest))
                .isInstanceOf(RideNotFinishedException.class);

        verify(rideRepository).findById(TestUtil.RIDE_ID);
        verifyNoMoreInteractions(rideRepository);
    }
}
