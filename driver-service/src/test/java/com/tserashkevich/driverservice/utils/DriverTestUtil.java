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
import java.util.UUID;

@UtilityClass
public class DriverTestUtil {
    public final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID SECOND_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public final String NAME = "USERNAME1";
    public static final String SECOND_NAME = "USERNAME2";
    public final LocalDate BIRTH_DATE = LocalDate.of(2000, 1, 1);
    public final LocalDate SECOND_BIRTH_DATE = LocalDate.of(1999, 5, 23);
    public final String PHONE = "+375292078876";
    public final String SECOND_PHONE = "+375448713245";
    public static final Gender GENDER = Gender.Men;
    public static final Gender SECOND_GENDER = Gender.Women;
    public static final String GENDER_NAME = Gender.Men.name();
    public static final String SECOND_GENDER_NAME = Gender.Women.name();
    public static final Boolean AVAILABLE = true;
    public static final Boolean SECOND_AVAILABLE = false;
    public final Double RATING = 5.0;
    public final Double SECOND_RATING = 8.0;
    public final Integer PAGE = 0;
    public final Integer LIMIT = 10;
    public final Sort SORT = Sort.by(Sort.Direction.ASC, "id");

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
                .available(SECOND_AVAILABLE)
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
                .gender(GENDER_NAME)
                .cars(CarTestUtil.getCarsWithoutDriverRequest())
                .build();
    }

    public DriverRequest getDriverRequestWithoutCars() {
        return DriverRequest.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER_NAME)
                .build();
    }

    public DriverUpdateRequest getDriverUpdateRequest() {
        return DriverUpdateRequest.builder()
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER_NAME)
                .build();
    }

    public DriverResponse getDriverResponse() {
        return DriverResponse.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER_NAME)
                .available(AVAILABLE)
                .cars(CarTestUtil.getCarsWithoutDriverResponse())
                .avgRating(RATING)
                .build();
    }

    public DriverResponse getDriverResponseWithChangedAvailableStatus() {
        return DriverResponse.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER_NAME)
                .available(AVAILABLE)
                .cars(CarTestUtil.getCarsWithoutDriverResponse())
                .avgRating(RATING)
                .build();
    }

    public DriverResponse getSecondDriverResponse() {
        return DriverResponse.builder()
                .id(SECOND_ID)
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER_NAME)
                .available(SECOND_AVAILABLE)
                .cars(CarTestUtil.getCarsWithoutDriverResponse())
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

    public Predicate getPredicate() {
        return ExpressionUtils.allOf(Expressions.TRUE);
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(PAGE, LIMIT, SORT);
    }

    public Page<Driver> getPageOfDrivers() {
        return new PageImpl<>(getDrivers());
    }

    public PageResponse<DriverResponse> getPageResponse(List<DriverResponse> driverResponses) {
        Page<Driver> driverPage = getPageOfDrivers();
        return PageResponse.<DriverResponse>builder()
                .objectList(driverResponses)
                .totalElements(driverPage.getTotalElements())
                .totalPages(driverPage.getTotalPages())
                .build();
    }
}
