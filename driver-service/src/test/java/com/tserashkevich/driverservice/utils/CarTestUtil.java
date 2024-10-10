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
    public final Long ID = 1L;
    public final Long SECOND_ID = 2L;
    public final String NUMBER = "dsf32241";
    public final String SECOND_NUMBER = "loo98543";
    public final String BRAND = "Audi";
    public final String SECOND_BRAND = "Mercedes";
    public final String MODEL = "a4";
    public final String SECOND_MODEL = "b943";
    public final Color COLOR = Color.Black;
    public final Color SECOND_COLOR = Color.White;
    public final String COLOR_NAME = Color.Black.name();
    public final String SECOND_COLOR_NAME = Color.White.name();
    public final Integer PAGE = 0;    public final Driver DRIVER = DriverTestUtil.getDriver();
    public final Integer LIMIT = 10;    public final Driver SECOND_DRIVER = DriverTestUtil.getSecondDriver();
    public final Sort SORT = Sort.by(Sort.Direction.ASC, "id");    public final UUID DRIVER_ID = DriverTestUtil.getDriver().getId();

    public Car getCar() {
        return Car.builder()
                .id(ID)
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR)
                .driver(DRIVER)
                .build();
    }    public final UUID SECOND_DRIVER_ID = DriverTestUtil.getSecondDriver().getId();

    public Car getSecondCar() {
        return Car.builder()
                .id(SECOND_ID)
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR)
                .driver(SECOND_DRIVER)
                .build();
    }

    public Car getNonSavedCar() {
        return Car.builder()
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR)
                .driver(DRIVER)
                .build();
    }

    public Car getSecondNonSavedCar() {
        return Car.builder()
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR)
                .driver(SECOND_DRIVER)
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
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR_NAME)
                .driver(DriverTestUtil.ID.toString())
                .build();
    }

    public CarRequest getEditCarRequest() {
        return CarRequest.builder()
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR_NAME)
                .driver(DriverTestUtil.SECOND_ID.toString())
                .build();
    }
//
//    public Car getEditCar() {
//        return Car.builder()
//                .id(ID)
//                .name(SECOND_NAME)
//                .birthDate(SECOND_BIRTH_DATE)
//                .phoneNumber(SECOND_PHONE)
//                .gender(SECOND_GENDER)
//                .build();
//    }

    public CarWithoutDriverRequest getCarWithoutDriverRequest() {
        return CarWithoutDriverRequest.builder()
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR_NAME)
                .build();
    }

    public CarWithoutDriverRequest getSecondCarWithoutDriverRequest() {
        return CarWithoutDriverRequest.builder()
                .number(NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR_NAME)
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
                .id(ID)
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR_NAME)
                .driver(DRIVER_ID)
                .build();
    }

    public CarResponse getSecondCarResponse() {
        return CarResponse.builder()
                .id(SECOND_ID)
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR_NAME)
                .driver(SECOND_DRIVER_ID)
                .build();
    }

    public CarWithoutDriverResponse getCarWithoutDriverResponse() {
        return CarWithoutDriverResponse.builder()
                .id(ID)
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR_NAME)
                .build();
    }

    public CarWithoutDriverResponse getSecondCarWithoutDriverResponse() {
        return CarWithoutDriverResponse.builder()
                .id(SECOND_ID)
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR_NAME)
                .build();
    }

    public CarResponse getEditCarResponse() {
        return CarResponse.builder()
                .id(SECOND_ID)
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR_NAME)
                .driver(DriverTestUtil.SECOND_ID)
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
                .page(PAGE)
                .limit(LIMIT)
                .sort(SORT)
                .build();
    }

    public Predicate getPredicate() {
        return ExpressionUtils.allOf(Expressions.TRUE);
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(PAGE, LIMIT, SORT);
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
