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
import java.util.Map;

@UtilityClass
public class CarTestUtil {
    public final Long ID = 1L;
    public final Long SECOND_ID = 2L;
    public final Long THIRD_ID = 5L;
    public final Long NON_EXISTING_ID = 100L;
    public final String NUMBER = "dsf32241";
    public final String SECOND_NUMBER = "loo98543";
    public final String NON_EXISTING_NUMBER = "qwer423";
    public final String BRAND = "Audi";
    public final String SECOND_BRAND = "Mercedes";
    public final String MODEL = "a4";
    public final String SECOND_MODEL = "b943";
    public final Color COLOR = Color.Black;
    public final Color SECOND_COLOR = Color.White;
    public final Integer PAGE = 0;
    public final Integer LIMIT = 10;
    public final Sort SORT = Sort.by(Sort.Direction.ASC, "id");    public final Driver DRIVER = DriverTestUtil.getDriver();
    public final String SORT_NAME = "ID_ASC";    public final Driver SECOND_DRIVER = DriverTestUtil.getSecondDriver();
    public final String CAR_NOT_FOUND_MESSAGE = "Машина не найдена";
    public final String DEFAULT_PATH = "/api/v1/cars";

    public Car getCar() {
        return Car.builder()
                .id(ID)
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR)
                .driver(DRIVER)
                .build();
    }

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
                .color(COLOR.name())
                .driver(DriverTestUtil.ID.toString())
                .build();
    }

    public CarRequest getNonExistingCarRequest() {
        return CarRequest.builder()
                .number(NON_EXISTING_NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR.name())
                .driver(DriverTestUtil.ID.toString())
                .build();
    }

    public CarRequest getEditCarRequest() {
        return CarRequest.builder()
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR.name())
                .driver(DriverTestUtil.SECOND_ID.toString())
                .build();
    }

    public CarWithoutDriverRequest getCarWithoutDriverRequest() {
        return CarWithoutDriverRequest.builder()
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR.name())
                .build();
    }

    public CarWithoutDriverRequest getSecondCarWithoutDriverRequest() {
        return CarWithoutDriverRequest.builder()
                .number(NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR.name())
                .build();
    }

    public CarWithoutDriverRequest getThirdCarWithoutDriverRequest() {
        return CarWithoutDriverRequest.builder()
                .number(NON_EXISTING_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR.name())
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
                .color(COLOR.name())
                .driver(DriverTestUtil.ID)
                .build();
    }

    public CarResponse getCreatedCarResponse() {
        return CarResponse.builder()
                .id(NON_EXISTING_ID)
                .number(NON_EXISTING_NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR.name())
                .driver(DriverTestUtil.ID)
                .build();
    }

    public CarResponse getSecondCarResponse() {
        return CarResponse.builder()
                .id(SECOND_ID)
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR.name())
                .driver(DriverTestUtil.SECOND_ID)
                .build();
    }

    public CarWithoutDriverResponse getCarWithoutDriverResponse() {
        return CarWithoutDriverResponse.builder()
                .id(ID)
                .number(NUMBER)
                .brand(BRAND)
                .model(MODEL)
                .color(COLOR.name())
                .build();
    }

    public CarWithoutDriverResponse getSecondCarWithoutDriverResponse() {
        return CarWithoutDriverResponse.builder()
                .id(SECOND_ID)
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR.name())
                .build();
    }

    public CarWithoutDriverResponse getThirdCarWithoutDriverResponse() {
        return CarWithoutDriverResponse.builder()
                .id(THIRD_ID)
                .number(NON_EXISTING_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR.name())
                .build();
    }

    public CarResponse getEditCarResponse() {
        return CarResponse.builder()
                .id(SECOND_ID)
                .number(SECOND_NUMBER)
                .brand(SECOND_BRAND)
                .model(SECOND_MODEL)
                .color(SECOND_COLOR.name())
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

    public Map<String, Object> getFindAllParamsMap() {
        return Map.of(
                "page", PAGE,
                "limit", LIMIT,
                "sort", SORT_NAME
        );
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

    public PageResponse<CarResponse> getPageResponse() {
        Page<Car> driverPage = getPageOfCars();
        return PageResponse.<CarResponse>builder()
                .objectList(getCarResponses())
                .totalElements(driverPage.getTotalElements())
                .totalPages(driverPage.getTotalPages())
                .build();
    }




}
