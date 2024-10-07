package com.tserashkevich.driverservice.unit;

import com.querydsl.core.types.Predicate;
import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.exceptions.CarNotFoundException;
import com.tserashkevich.driverservice.mappers.CarMapper;
import com.tserashkevich.driverservice.models.Car;
import com.tserashkevich.driverservice.models.Driver;
import com.tserashkevich.driverservice.repositories.CarRepository;
import com.tserashkevich.driverservice.services.impl.CarServiceImpl;
import com.tserashkevich.driverservice.utils.CarTestUtil;
import com.tserashkevich.driverservice.utils.DriverTestUtil;
import org.assertj.core.api.Assertions;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceUnitTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;
    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void givenNonExistingCar_whenCreateCar_thenCreateNewCar() {
        CarRequest carRequest = CarTestUtil.getCarRequest();
        Car car = CarTestUtil.getNonSavedCar();
        CarResponse carResponse = CarTestUtil.getCarResponse();

        doReturn(car)
                .when(carMapper)
                .toModel(carRequest);
        doAnswer(invocation -> {
            car.setId(CarTestUtil.DEFAULT_ID);
            return null;
        }).when(carRepository).save(car);
        doReturn(carResponse)
                .when(carMapper)
                .toResponse(car);

        CarResponse result = carService.create(carRequest);

        verify(carMapper).toModel(carRequest);
        verify(carRepository).save(car);
        verify(carMapper).toResponse(car);
        assertThat(result).isEqualTo(carResponse);
    }

    @Test
    void givenNonExistingCarsAndDriver_whenCreateCars_thenCreateNewCars() {
        Driver driver = DriverTestUtil.getDriver();
        List<Car> cars = CarTestUtil.getNonSavedCars();
        List<CarWithoutDriverRequest> carRequest = CarTestUtil.getCarsWithoutDriverRequest();

        doReturn(cars)
                .when(carMapper)
                .toModel(carRequest);
        doAnswer(invocation -> {
            cars.get(0).setId(CarTestUtil.DEFAULT_ID);
            cars.get(1).setId(CarTestUtil.DEFAULT_SECOND_ID);
            return null;
        }).when(carRepository).saveAll(cars);

        List<Car> result = carService.create(driver, carRequest);

        verify(carMapper).toModel(carRequest);
        verify(carRepository).saveAll(cars);
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void givenExistingCar_whenEditCar_thenUpdateCar() {
        CarRequest carRequest = CarTestUtil.getEditCarRequest();
        Car car = CarTestUtil.getCar();
        CarResponse carResponse = CarTestUtil.getEditCarResponse();

        doReturn(Optional.of(car))
                .when(carRepository)
                .findById(CarTestUtil.DEFAULT_ID);
        doAnswer(invocation -> {
            CarTestUtil.updateCar(car, carRequest);
            return null;
        }).when(carMapper).updateModel(car, carRequest);
        doReturn(car)
                .when(carRepository)
                .save(car);
        doReturn(carResponse)
                .when(carMapper)
                .toResponse(car);

        CarResponse result = carService.update(CarTestUtil.DEFAULT_ID, carRequest);

        verify(carRepository).findById(CarTestUtil.DEFAULT_ID);
        verify(carMapper).updateModel(car, carRequest);
        verify(carRepository).save(car);
        verify(carMapper).toResponse(car);
        assertThat(result).isEqualTo(carResponse);
    }

    @Test
    void givenNonExistingCar_whenEditCar_thenThrowException() {
        CarRequest carRequest = CarTestUtil.getCarRequest();

        doReturn(Optional.empty())
                .when(carRepository)
                .findById(CarTestUtil.DEFAULT_ID);

        assertThatThrownBy(() -> carService.update(CarTestUtil.DEFAULT_ID, carRequest))
                .isInstanceOf(CarNotFoundException.class);

        verify(carRepository).findById(CarTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void givenExistingCar_whenDeleteCar_thenDeleteCar() {
        Car car = CarTestUtil.getCar();

        doReturn(Optional.of(car))
                .when(carRepository)
                .findById(CarTestUtil.DEFAULT_ID);
        doNothing()
                .when(carRepository)
                .delete(car);

        carService.delete(car.getId());

        verify(carRepository).findById(CarTestUtil.DEFAULT_ID);
        verify(carRepository).delete(car);
    }

    @Test
    void givenNonExistingCar_whenDeleteCar_thenThrowException() {
        doReturn(Optional.empty())
                .when(carRepository)
                .findById(CarTestUtil.DEFAULT_ID);

        assertThatThrownBy(() -> carService.delete(CarTestUtil.DEFAULT_ID))
                .isInstanceOf(CarNotFoundException.class);

        verify(carRepository).findById(CarTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void givenValidParams_whenFindCars_thenReturnPageOfCarResponse() {
        CarFindAllParams findAllParams = CarTestUtil.getFindAllParams();
        Predicate predicate = CarTestUtil.getPredicate();
        PageRequest pageRequest = CarTestUtil.getPageRequest();
        Page<Car> carPage = CarTestUtil.getPageOfCars();
        List<CarResponse> carResponses = CarTestUtil.getCarResponses();
        PageResponse<CarResponse> carPageResponse = CarTestUtil.getPageResponse(carResponses);

        doReturn(carPage)
                .when(carRepository)
                .findAll(predicate, pageRequest);
        doReturn(carResponses)
                .when(carMapper)
                .toResponses(carPage.getContent());

        PageResponse<CarResponse> result = carService.findAll(findAllParams);

        verify(carRepository).findAll(predicate, pageRequest);
        assertThat(result).isEqualTo(carPageResponse);
    }

    @Test
    void givenExistingCar_whenFindByIdCar_thenReturnCarResponse() {
        Car car = CarTestUtil.getCar();
        CarResponse carResponse = CarTestUtil.getCarResponse();

        doReturn(Optional.of(car))
                .when(carRepository)
                .findById(CarTestUtil.DEFAULT_ID);
        doReturn(carResponse)
                .when(carMapper)
                .toResponse(car);

        carService.findById(car.getId());

        verify(carRepository).findById(CarTestUtil.DEFAULT_ID);
    }

    @Test
    void givenNonExistingCar_whenFindByIdCar_thenThrowException() {
        doReturn(Optional.empty())
                .when(carRepository)
                .findById(CarTestUtil.DEFAULT_ID);

        Assertions.assertThatThrownBy(() -> carService.findById(CarTestUtil.DEFAULT_ID))
                .isInstanceOf(CarNotFoundException.class);

        verify(carRepository).findById(CarTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void givenExistingCar_whenExistByIdCar_thenTrue() {
        doReturn(true)
                .when(carRepository)
                .existsById(CarTestUtil.DEFAULT_ID);

        Boolean result = carService.existById(CarTestUtil.DEFAULT_ID);

        verify(carRepository).existsById(CarTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(carRepository);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void givenNonExistingCar_whenExistByIdCar_thenFalse() {
        doReturn(false)
                .when(carRepository)
                .existsById(CarTestUtil.DEFAULT_ID);

        Boolean result = carService.existById(CarTestUtil.DEFAULT_ID);

        verify(carRepository).existsById(CarTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(carRepository);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void givenExistingCar_whenExistByCarNumber_thenTrue() {
        doReturn(true)
                .when(carRepository)
                .existsByNumber(CarTestUtil.DEFAULT_NUMBER);

        Boolean result = carService.existByCarNumber(CarTestUtil.DEFAULT_NUMBER);

        verify(carRepository).existsByNumber(CarTestUtil.DEFAULT_NUMBER);
        verifyNoMoreInteractions(carRepository);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void givenNonExistingCar_whenExistByCarNumber_thenFalse() {
        doReturn(false)
                .when(carRepository)
                .existsByNumber(CarTestUtil.DEFAULT_NUMBER);

        Boolean result = carService.existByCarNumber(CarTestUtil.DEFAULT_NUMBER);

        verify(carRepository).existsByNumber(CarTestUtil.DEFAULT_NUMBER);
        verifyNoMoreInteractions(carRepository);
        assertThat(result).isEqualTo(false);
    }
}
