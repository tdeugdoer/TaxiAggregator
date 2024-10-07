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
    public final UUID DEFAULT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID DEFAULT_SECOND_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public final String DEFAULT_NAME = "USERNAME1";
    public static final String DEFAULT_SECOND_NAME = "USERNAME2";
    public final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(2000, 1, 1);
    public final LocalDate DEFAULT_SECOND_BIRTH_DATE = LocalDate.of(1999, 5, 23);
    public final String DEFAULT_PHONE = "+375292078876";
    public final String DEFAULT_SECOND_PHONE = "+375448713245";
    public static final Gender DEFAULT_GENDER = Gender.Men;
    public static final Gender DEFAULT_SECOND_GENDER = Gender.Women;
    public static final String DEFAULT_GENDER_NAME = Gender.Men.name();
    public static final String DEFAULT_SECOND_GENDER_NAME = Gender.Women.name();
    public static final Boolean DEFAULT_AVAILABLE = true;
    public static final Boolean DEFAULT_SECOND_AVAILABLE = false;
    public final Double DEFAULT_RATING = 5.0;
    public final Double DEFAULT_SECOND_RATING = 8.0;
    public final Integer DEFAULT_PAGE = 0;
    public final Integer DEFAULT_LIMIT = 10;
    public final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "id");

    public Driver getDriver() {
        return Driver.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER)
                .available(DEFAULT_AVAILABLE)
                .cars(CarTestUtil.getCars())
                .build();
    }

    public Driver getSecondDriver() {
        return Driver.builder()
                .id(DEFAULT_SECOND_ID)
                .name(DEFAULT_SECOND_NAME)
                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
                .phoneNumber(DEFAULT_SECOND_PHONE)
                .gender(DEFAULT_SECOND_GENDER)
                .available(DEFAULT_SECOND_AVAILABLE)
                .cars(CarTestUtil.getCars())
                .build();
    }

    public Driver getNonSavedDriver() {
        return Driver.builder()
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER)
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
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER_NAME)
                .cars(CarTestUtil.getCarsWithoutDriverRequest())
                .build();
    }

    public DriverRequest getDriverRequestWithoutCars() {
        return DriverRequest.builder()
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER_NAME)
                .build();
    }

    public DriverUpdateRequest getDriverUpdateRequest() {
        return DriverUpdateRequest.builder()
                .name(DEFAULT_SECOND_NAME)
                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
                .phoneNumber(DEFAULT_SECOND_PHONE)
                .gender(DEFAULT_SECOND_GENDER_NAME)
                .build();
    }

    public DriverResponse getDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER_NAME)
                .available(DEFAULT_AVAILABLE)
                .cars(CarTestUtil.getCarsWithoutDriverResponse())
                .avgRating(DEFAULT_RATING)
                .build();
    }

    public DriverResponse getDriverResponseWithChangedAvailableStatus() {
        return DriverResponse.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER_NAME)
                .available(DEFAULT_AVAILABLE)
                .cars(CarTestUtil.getCarsWithoutDriverResponse())
                .avgRating(DEFAULT_RATING)
                .build();
    }

    public DriverResponse getSecondDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_SECOND_ID)
                .name(DEFAULT_SECOND_NAME)
                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
                .phoneNumber(DEFAULT_SECOND_PHONE)
                .gender(DEFAULT_SECOND_GENDER_NAME)
                .available(DEFAULT_SECOND_AVAILABLE)
                .cars(CarTestUtil.getCarsWithoutDriverResponse())
                .avgRating(DEFAULT_SECOND_RATING)
                .build();
    }

    public DriverResponse getEditDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_SECOND_NAME)
                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
                .phoneNumber(DEFAULT_SECOND_PHONE)
                .gender(DEFAULT_SECOND_GENDER.name())
                .available(DEFAULT_AVAILABLE)
                .cars(CarTestUtil.getCarsWithoutDriverResponse())
                .avgRating(DEFAULT_RATING)
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
                .page(DriverTestUtil.DEFAULT_PAGE)
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
