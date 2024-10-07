package com.tserashkevich.driverservice.utils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.models.Car;
import com.tserashkevich.driverservice.models.Driver;
import com.tserashkevich.driverservice.models.enums.Color;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class CarTestUtil {
    public final Long DEFAULT_ID = 1L;
    public final Long DEFAULT_SECOND_ID = 2L;
    public final String DEFAULT_NUMBER = "dsf32241";
    public final String DEFAULT_SECOND_NUMBER = "loo98543";
    public final String DEFAULT_BRAND = "Audi";
    public final String DEFAULT_SECOND_BRAND = "Mercedes";
    public final String DEFAULT_MODEL = "a4";
    public final String DEFAULT_SECOND_MODEL = "b943";
    public final Color DEFAULT_COLOR = Color.Black;
    public final Color DEFAULT_SECOND_COLOR = Color.White;
    public final String DEFAULT_COLOR_NAME = Color.Black.name();
    public final String DEFAULT_SECOND_COLOR_NAME = Color.White.name();
    public final Integer DEFAULT_PAGE = 0;    public final Driver DEFAULT_DRIVER = DriverTestUtil.getDriver();
    public final Integer DEFAULT_LIMIT = 10;    public final Driver DEFAULT_SECOND_DRIVER = DriverTestUtil.getSecondDriver();
    public final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "id");    public final UUID DEFAULT_DRIVER_ID = DriverTestUtil.getDriver().getId();

    public Car getCar() {
        return Car.builder()
                .id(DEFAULT_ID)
                .number(DEFAULT_NUMBER)
                .brand(DEFAULT_BRAND)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR)
                .driver(DEFAULT_DRIVER)
                .build();
    }    public final UUID DEFAULT_SECOND_DRIVER_ID = DriverTestUtil.getSecondDriver().getId();

    public Car getSecondCar() {
        return Car.builder()
                .id(DEFAULT_SECOND_ID)
                .number(DEFAULT_SECOND_NUMBER)
                .brand(DEFAULT_SECOND_BRAND)
                .model(DEFAULT_SECOND_MODEL)
                .color(DEFAULT_SECOND_COLOR)
                .driver(DEFAULT_SECOND_DRIVER)
                .build();
    }

    public Car getNonSavedCar() {
        return Car.builder()
                .number(DEFAULT_NUMBER)
                .brand(DEFAULT_BRAND)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR)
                .driver(DEFAULT_DRIVER)
                .build();
    }

    public Car getSecondNonSavedCar() {
        return Car.builder()
                .number(DEFAULT_SECOND_NUMBER)
                .brand(DEFAULT_SECOND_BRAND)
                .model(DEFAULT_SECOND_MODEL)
                .color(DEFAULT_SECOND_COLOR)
                .driver(DEFAULT_SECOND_DRIVER)
                .build();
    }

    public List<Car> getCars() {
        return List.of(
                getCar(),
                getSecondCar()
        );
    }

    public List<Car> getNonSavedCars() {
        return List.of(
                getNonSavedCar(),
                getSecondNonSavedCar()
        );
    }

    public CarRequest getCarRequest() {
        return CarRequest.builder()
                .number(DEFAULT_NUMBER)
                .brand(DEFAULT_BRAND)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR_NAME)
                .driver(DriverTestUtil.DEFAULT_ID.toString())
                .build();
    }

    public CarRequest getEditCarRequest() {
        return CarRequest.builder()
                .number(DEFAULT_SECOND_NUMBER)
                .brand(DEFAULT_SECOND_BRAND)
                .model(DEFAULT_SECOND_MODEL)
                .color(DEFAULT_SECOND_COLOR_NAME)
                .driver(DriverTestUtil.DEFAULT_SECOND_ID.toString())
                .build();
    }
//
//    public Car getEditCar() {
//        return Car.builder()
//                .id(DEFAULT_ID)
//                .name(DEFAULT_SECOND_NAME)
//                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
//                .phoneNumber(DEFAULT_SECOND_PHONE)
//                .gender(DEFAULT_SECOND_GENDER)
//                .build();
//    }

    public CarWithoutDriverRequest getCarWithoutDriverRequest() {
        return CarWithoutDriverRequest.builder()
                .number(DEFAULT_NUMBER)
                .brand(DEFAULT_BRAND)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR_NAME)
                .build();
    }

    public CarWithoutDriverRequest getSecondCarWithoutDriverRequest() {
        return CarWithoutDriverRequest.builder()
                .number(DEFAULT_NUMBER)
                .brand(DEFAULT_SECOND_BRAND)
                .model(DEFAULT_SECOND_MODEL)
                .color(DEFAULT_SECOND_COLOR_NAME)
                .build();
    }

    public List<CarWithoutDriverRequest> getCarsWithoutDriverRequest() {
        return List.of(
                getCarWithoutDriverRequest(),
                getSecondCarWithoutDriverRequest()
        );
    }

    public CarResponse getCarResponse() {
        return CarResponse.builder()
                .id(DEFAULT_ID)
                .number(DEFAULT_NUMBER)
                .brand(DEFAULT_BRAND)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR_NAME)
                .driver(DEFAULT_DRIVER_ID)
                .build();
    }

    public CarResponse getSecondCarResponse() {
        return CarResponse.builder()
                .id(DEFAULT_SECOND_ID)
                .number(DEFAULT_SECOND_NUMBER)
                .brand(DEFAULT_SECOND_BRAND)
                .model(DEFAULT_SECOND_MODEL)
                .color(DEFAULT_SECOND_COLOR_NAME)
                .driver(DEFAULT_SECOND_DRIVER_ID)
                .build();
    }

    public CarWithoutDriverResponse getCarWithoutDriverResponse() {
        return CarWithoutDriverResponse.builder()
                .id(DEFAULT_ID)
                .number(DEFAULT_NUMBER)
                .brand(DEFAULT_BRAND)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR_NAME)
                .build();
    }

    public CarWithoutDriverResponse getSecondCarWithoutDriverResponse() {
        return CarWithoutDriverResponse.builder()
                .id(DEFAULT_SECOND_ID)
                .number(DEFAULT_SECOND_NUMBER)
                .brand(DEFAULT_SECOND_BRAND)
                .model(DEFAULT_SECOND_MODEL)
                .color(DEFAULT_SECOND_COLOR_NAME)
                .build();
    }

    public CarResponse getEditCarResponse() {
        return CarResponse.builder()
                .id(DEFAULT_SECOND_ID)
                .number(DEFAULT_SECOND_NUMBER)
                .brand(DEFAULT_SECOND_BRAND)
                .model(DEFAULT_SECOND_MODEL)
                .color(DEFAULT_SECOND_COLOR_NAME)
                .driver(DriverTestUtil.DEFAULT_SECOND_ID)
                .build();
    }

    public List<CarResponse> getCarResponses() {
        return List.of(
                getCarResponse(),
                getSecondCarResponse()
        );
    }

    public List<CarWithoutDriverResponse> getCarsWithoutDriverResponse() {
        return List.of(
                getCarWithoutDriverResponse(),
                getSecondCarWithoutDriverResponse()
        );
    }

    public void updateCar(Car car, CarRequest carRequest) {
        car.setNumber(car.getNumber());
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setColor(Color.valueOf(carRequest.getColor()));
        car.setDriver(DriverTestUtil.getSecondDriver());
    }

    public CarFindAllParams getFindAllParams() {
        return CarFindAllParams.builder()
                .page(DEFAULT_PAGE)
                .limit(DEFAULT_LIMIT)
                .sort(DEFAULT_SORT)
                .build();
    }

    public Predicate getPredicate() {
        return ExpressionUtils.allOf(Expressions.TRUE);
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(DEFAULT_PAGE, DEFAULT_LIMIT, DEFAULT_SORT);
    }

    public Page<Car> getPageOfCars() {
        return new PageImpl<>(getCars());
    }

    public PageResponse<CarResponse> getPageResponse(List<CarResponse> carResponses) {
        Page<Car> driverPage = getPageOfCars();
        return PageResponse.<CarResponse>builder()
                .objectList(carResponses)
                .totalElements(driverPage.getTotalElements())
                .totalPages(driverPage.getTotalPages())
                .build();
    }








}
