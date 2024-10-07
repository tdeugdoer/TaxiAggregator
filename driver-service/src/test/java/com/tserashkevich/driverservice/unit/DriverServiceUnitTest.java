package com.tserashkevich.driverservice.unit;

import com.querydsl.core.types.Predicate;
import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.exceptions.DriverNotFoundException;
import com.tserashkevich.driverservice.mappers.DriverMapper;
import com.tserashkevich.driverservice.models.Driver;
import com.tserashkevich.driverservice.repositories.DriverRepository;
import com.tserashkevich.driverservice.services.CarService;
import com.tserashkevich.driverservice.services.impl.DriverServiceImpl;
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
public class DriverServiceUnitTest {
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private DriverMapper driverMapper;
    @Mock
    private CarService carService;
    @InjectMocks
    private DriverServiceImpl driverService;

    @Test
    void givenNonExistingDriverWitchCars_whenCreateDriver_thenCreateNewDriver() {
        DriverRequest driverRequest = DriverTestUtil.getDriverRequest();
        Driver driver = DriverTestUtil.getNonSavedDriver();
        DriverResponse driverResponse = DriverTestUtil.getDriverResponse();

        doReturn(driver)
                .when(driverMapper)
                .toModel(driverRequest);
        doAnswer(invocation -> {
            driver.setId(DriverTestUtil.DEFAULT_ID);
            return null;
        }).when(driverRepository).saveAndFlush(driver);
        doReturn(CarTestUtil.getCars())
                .when(carService)
                .create(driver, driverRequest.getCars());
        doReturn(driverResponse)
                .when(driverMapper)
                .toResponse(driver);

        DriverResponse result = driverService.create(driverRequest);

        verify(driverMapper).toModel(driverRequest);
        verify(driverRepository).saveAndFlush(driver);
        verify(carService).create(driver, driverRequest.getCars());
        verify(driverMapper).toResponse(driver);
        assertThat(result).isEqualTo(driverResponse);
    }

    @Test
    void givenNonExistingDriverWithoutCars_whenCreateDriver_thenCreateNewDriver() {
        DriverRequest driverRequest = DriverTestUtil.getDriverRequestWithoutCars();
        Driver driver = DriverTestUtil.getNonSavedDriver();
        DriverResponse driverResponse = DriverTestUtil.getDriverResponse();

        doReturn(driver)
                .when(driverMapper)
                .toModel(driverRequest);
        doAnswer(invocation -> {
            driver.setId(DriverTestUtil.DEFAULT_ID);
            return null;
        }).when(driverRepository).saveAndFlush(driver);
        doReturn(driverResponse)
                .when(driverMapper)
                .toResponse(driver);

        DriverResponse result = driverService.create(driverRequest);

        verify(driverMapper).toModel(driverRequest);
        verify(driverRepository).saveAndFlush(driver);
        verify(carService, never()).create(driver, driverRequest.getCars());
        verify(driverMapper).toResponse(driver);
        assertThat(result).isEqualTo(driverResponse);
    }

    @Test
    void givenExistingDriver_whenEditDriver_thenUpdateDriver() {
        DriverUpdateRequest driverUpdateRequest = DriverTestUtil.getDriverUpdateRequest();
        Driver driver = DriverTestUtil.getDriver();
        DriverResponse driverResponse = DriverTestUtil.getEditDriverResponse();

        doReturn(Optional.of(driver))
                .when(driverRepository)
                .findById(DriverTestUtil.DEFAULT_ID);
        doAnswer(invocation -> {
            DriverTestUtil.updateDriver(driver, driverUpdateRequest);
            return null;
        }).when(driverMapper).updateModel(driver, driverUpdateRequest);
        doReturn(driver)
                .when(driverRepository)
                .save(driver);
        doReturn(driverResponse)
                .when(driverMapper)
                .toResponse(driver);

        DriverResponse result = driverService.update(DriverTestUtil.DEFAULT_ID, driverUpdateRequest);

        verify(driverRepository).findById(DriverTestUtil.DEFAULT_ID);
        verify(driverMapper).updateModel(driver, driverUpdateRequest);
        verify(driverRepository).save(driver);
        verify(driverMapper).toResponse(driver);
        assertThat(result).isEqualTo(driverResponse);
    }

    @Test
    void givenNonExistingDriver_whenEditDriver_thenThrowException() {
        DriverUpdateRequest driverUpdateRequest = DriverTestUtil.getDriverUpdateRequest();

        doReturn(Optional.empty())
                .when(driverRepository)
                .findById(DriverTestUtil.DEFAULT_ID);

        assertThatThrownBy(() -> driverService.update(DriverTestUtil.DEFAULT_ID, driverUpdateRequest))
                .isInstanceOf(DriverNotFoundException.class);

        verify(driverRepository).findById(DriverTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    void givenExistingDriver_whenDeleteDriver_thenDeleteDriver() {
        Driver driver = DriverTestUtil.getDriver();

        doReturn(Optional.of(driver))
                .when(driverRepository)
                .findById(driver.getId());
        doNothing()
                .when(driverRepository)
                .delete(driver);

        driverService.delete(driver.getId());

        verify(driverRepository).findById(driver.getId());
        verify(driverRepository).delete(driver);
    }

    @Test
    void givenNonExistingDriver_whenDeleteDriver_thenThrowException() {
        doReturn(Optional.empty())
                .when(driverRepository)
                .findById(DriverTestUtil.DEFAULT_ID);

        assertThatThrownBy(() -> driverService.delete(DriverTestUtil.DEFAULT_ID))
                .isInstanceOf(DriverNotFoundException.class);

        verify(driverRepository).findById(DriverTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    void givenValidParams_whenFindDrivers_thenReturnPageOfDriverResponse() {
        DriverFindAllParams findAllParams = DriverTestUtil.getFindAllParams();
        Predicate predicate = DriverTestUtil.getPredicate();
        PageRequest pageRequest = DriverTestUtil.getPageRequest();
        Page<Driver> driverPage = DriverTestUtil.getPageOfDrivers();
        List<DriverResponse> driverResponses = DriverTestUtil.getDriverResponses();
        PageResponse<DriverResponse> driverPageResponse = DriverTestUtil.getPageResponse(driverResponses);

        doReturn(driverPage)
                .when(driverRepository)
                .findAll(predicate, pageRequest);
        doReturn(driverResponses)
                .when(driverMapper)
                .toResponses(driverPage.getContent());

        PageResponse<DriverResponse> result = driverService.findAll(findAllParams);

        verify(driverRepository).findAll(predicate, pageRequest);
        assertThat(result).isEqualTo(driverPageResponse);
    }

    @Test
    void givenExistingDriver_whenFindByIdDriver_thenReturnDriverResponse() {
        Driver driver = DriverTestUtil.getDriver();
        DriverResponse driverResponse = DriverTestUtil.getDriverResponse();

        doReturn(Optional.of(driver))
                .when(driverRepository)
                .findById(driver.getId());
        doReturn(driverResponse)
                .when(driverMapper)
                .toResponse(driver);

        driverService.findById(driver.getId());

        verify(driverRepository).findById(driver.getId());
    }

    @Test
    void givenNonExistingDriver_whenFindByIdDriver_thenThrowException() {
        doReturn(Optional.empty())
                .when(driverRepository)
                .findById(DriverTestUtil.DEFAULT_ID);

        Assertions.assertThatThrownBy(() -> driverService.findById(DriverTestUtil.DEFAULT_ID))
                .isInstanceOf(DriverNotFoundException.class);

        verify(driverRepository).findById(DriverTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    void givenExistingDriver_whenExistByPhoneNumberDriver_thenTrue() {
        doReturn(true)
                .when(driverRepository)
                .existsByPhoneNumber(DriverTestUtil.DEFAULT_PHONE);

        Boolean result = driverService.existByPhoneNumber(DriverTestUtil.DEFAULT_PHONE);

        verify(driverRepository).existsByPhoneNumber(DriverTestUtil.DEFAULT_PHONE);
        verifyNoMoreInteractions(driverRepository);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void givenNonExistingDriver_whenExistByPhoneNumberDriver_thenFalse() {
        doReturn(false)
                .when(driverRepository)
                .existsByPhoneNumber(DriverTestUtil.DEFAULT_PHONE);

        Boolean result = driverService.existByPhoneNumber(DriverTestUtil.DEFAULT_PHONE);

        verify(driverRepository).existsByPhoneNumber(DriverTestUtil.DEFAULT_PHONE);
        verifyNoMoreInteractions(driverRepository);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void givenExistingDriver_whenExistByIdDriver_thenTrue() {
        doReturn(true)
                .when(driverRepository)
                .existsById(DriverTestUtil.DEFAULT_ID);

        Boolean result = driverService.existById(DriverTestUtil.DEFAULT_ID);

        verify(driverRepository).existsById(DriverTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(driverRepository);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void givenNonExistingDriver_whenExistByIdDriver_thenFalse() {
        doReturn(false)
                .when(driverRepository)
                .existsById(DriverTestUtil.DEFAULT_ID);

        Boolean result = driverService.existById(DriverTestUtil.DEFAULT_ID);

        verify(driverRepository).existsById(DriverTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(driverRepository);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void givenExistingDriver_whenChangeAvailableStatus_thenReturnDriverResponse() {
        Driver driver = DriverTestUtil.getDriver();
        DriverResponse driverResponse = DriverTestUtil.getDriverResponseWithChangedAvailableStatus();

        doReturn(Optional.of(driver))
                .when(driverRepository)
                .findById(DriverTestUtil.DEFAULT_ID);
        doReturn(driver)
                .when(driverRepository)
                .save(driver);
        doReturn(driverResponse)
                .when(driverMapper)
                .toResponse(driver);

        DriverResponse result = driverService.changeAvailableStatus(DriverTestUtil.DEFAULT_ID, DriverTestUtil.DEFAULT_AVAILABLE);

        verify(driverRepository).findById(DriverTestUtil.DEFAULT_ID);
        verify(driverRepository).save(driver);
        verify(driverMapper).toResponse(driver);
        assertThat(result).isEqualTo(driverResponse);
    }

    @Test
    void givenNonExistingDriver_whenChangeAvailableStatus_thenThrowException() {
        doReturn(Optional.empty())
                .when(driverRepository)
                .findById(DriverTestUtil.DEFAULT_ID);

        assertThatThrownBy(() -> driverService.changeAvailableStatus(DriverTestUtil.DEFAULT_ID, DriverTestUtil.DEFAULT_AVAILABLE))
                .isInstanceOf(DriverNotFoundException.class);

        verify(driverRepository).findById(DriverTestUtil.DEFAULT_ID);
        verifyNoMoreInteractions(driverRepository);
    }
}
