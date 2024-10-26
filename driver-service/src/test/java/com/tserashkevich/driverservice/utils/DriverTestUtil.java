package com.tserashkevich.driverservice.utils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.models.Driver;
import com.tserashkevich.driverservice.models.enums.Gender;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class DriverTestUtil {
    public final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID SECOND_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public final UUID NON_EXISTING_ID = UUID.fromString("99999999-9999-9999-9999-999999999999");
    public final String NAME = "John Doe";
    public static final String SECOND_NAME = "Peter Jones";
    public final String WRONG_NAME = "";
    public final LocalDate BIRTH_DATE = LocalDate.of(2000, 1, 1);
    public final LocalDate SECOND_BIRTH_DATE = LocalDate.of(1999, 5, 23);
    public final LocalDate WRONG_BIRTH_DATE = LocalDate.of(2999, 5, 23);
    public final String PHONE = "+375297435874";
    public final String SECOND_PHONE = "+375331675879";
    public final String NON_EXISTING_PHONE = "+375541675201";
    public final String WRONG_PHONE = "+3752974fsd5874";
    public static final Gender GENDER = Gender.Men;
    public static final Gender SECOND_GENDER = Gender.Women;
    public final String WRONG_GENDER = "Gender";
    public static final Boolean AVAILABLE = true;
    public static final Boolean NON_AVAILABLE = false;
    public final Double RATING = 0.0;
    public final Double SECOND_RATING = 8.0;
    public final Integer PAGE = 0;
    public final Integer LIMIT = 10;
    public final Sort SORT = Sort.by(Sort.Direction.ASC, "id");
    public final String SORT_NAME = "ID_ASC";
    public final String DRIVER_NOT_FOUND_MESSAGE = "Водитель не найден";
    public final String DEFAULT_PATH = "/api/v1/drivers";
    public final String AVG_REQUEST = "/avg/";
    public final String AVG_REQUEST_WITH_UUID = "/avg/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    public Driver getDriver() {
        return Driver.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER)
                .available(AVAILABLE)
                .cars(CarTestUtil.getCars())
                .build();
    }

    public Driver getSecondDriver() {
        return Driver.builder()
                .id(SECOND_ID)
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER)
                .available(NON_AVAILABLE)
                .cars(CarTestUtil.getCars())
                .build();
    }

    public Driver getNonSavedDriver() {
        return Driver.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER)
                .build();
    }

    public List<Driver> getDrivers() {
        return List.of(
                getDriver(),
                getSecondDriver()
        );
    }

    public DriverRequest getDriverRequest() {
        return DriverRequest.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER.name())
                .cars(CarTestUtil.getCarsWithoutDriverRequest())
                .build();
    }

    public DriverRequest getNonExistingDriverRequest() {
        return DriverRequest.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(NON_EXISTING_PHONE)
                .gender(GENDER.name())
                .cars(List.of(CarTestUtil.getThirdCarWithoutDriverRequest()))
                .build();
    }

    public DriverRequest getInvalidPassengerRequest() {
        return DriverRequest.builder()
                .name(WRONG_NAME)
                .birthDate(WRONG_BIRTH_DATE)
                .phoneNumber(WRONG_PHONE)
                .gender(WRONG_GENDER)
                .build();
    }

    public DriverRequest getDriverRequestWithoutCars() {
        return DriverRequest.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER.name())
                .build();
    }

    public DriverUpdateRequest getDriverUpdateRequest() {
        return DriverUpdateRequest.builder()
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER.name())
                .build();
    }

    public DriverResponse getDriverResponse() {
        return DriverResponse.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER.name())
                .available(AVAILABLE)
                .cars(List.of(CarTestUtil.getCarWithoutDriverResponse()))
                .avgRating(RATING)
                .build();
    }

    public DriverResponse getDriverResponseWithFalseAvailable() {
        return DriverResponse.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER.name())
                .available(NON_AVAILABLE)
                .cars(List.of(CarTestUtil.getCarWithoutDriverResponse()))
                .avgRating(RATING)
                .build();
    }

    public DriverResponse getCreatedCarResponse() {
        return DriverResponse.builder()
                .id(NON_EXISTING_ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(NON_EXISTING_PHONE)
                .gender(GENDER.name())
                .available(AVAILABLE)
                .avgRating(RATING)
                .cars(List.of(CarTestUtil.getThirdCarWithoutDriverResponse()))
                .build();
    }

    public DriverResponse getDriverResponseWithChangedAvailableStatus() {
        return DriverResponse.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER.name())
                .available(AVAILABLE)
                .cars(List.of(CarTestUtil.getCarWithoutDriverResponse()))
                .avgRating(RATING)
                .build();
    }

    public DriverResponse getSecondDriverResponse() {
        return DriverResponse.builder()
                .id(SECOND_ID)
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER.name())
                .available(NON_AVAILABLE)
                .cars(List.of(CarTestUtil.getSecondCarWithoutDriverResponse()))
                .avgRating(SECOND_RATING)
                .build();
    }

    public DriverResponse getEditDriverResponse() {
        return DriverResponse.builder()
                .id(ID)
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER.name())
                .available(AVAILABLE)
                .cars(CarTestUtil.getCarsWithoutDriverResponse())
                .avgRating(RATING)
                .build();
    }

    public List<DriverResponse> getDriverResponses() {
        return List.of(
                getDriverResponse(),
                getSecondDriverResponse()
        );
    }

    public void updateDriver(Driver driver, DriverUpdateRequest driverUpdateRequest) {
        driver.setBirthDate(driverUpdateRequest.getBirthDate());
        driver.setGender(Gender.valueOf(driverUpdateRequest.getGender()));
        driver.setName(driverUpdateRequest.getName());
        driver.setPhoneNumber(driverUpdateRequest.getPhoneNumber());
    }

    public DriverFindAllParams getFindAllParams() {
        return DriverFindAllParams.builder()
                .page(DriverTestUtil.PAGE)
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

    public Page<Driver> getPageOfDrivers() {
        return new PageImpl<>(getDrivers());
    }

    public PageResponse<DriverResponse> getPageResponse() {
        Page<Driver> driverPage = getPageOfDrivers();
        return PageResponse.<DriverResponse>builder()
                .objectList(getDriverResponses())
                .totalElements(driverPage.getTotalElements())
                .totalPages(driverPage.getTotalPages())
                .build();
    }
}
