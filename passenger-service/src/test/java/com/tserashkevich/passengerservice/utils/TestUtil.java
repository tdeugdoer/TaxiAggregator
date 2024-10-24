package com.tserashkevich.passengerservice.utils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.tserashkevich.passengerservice.dtos.FindAllParams;
import com.tserashkevich.passengerservice.dtos.PageResponse;
import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.models.Passenger;
import com.tserashkevich.passengerservice.models.enums.Gender;
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
public class TestUtil {
    public final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID SECOND_ID = UUID.fromString("0ef7de40-7c91-46b7-8ecb-d68f7eb28ed5");
    public final UUID NON_EXISTING_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public final String NAME = "John Doe";
    public final String SECOND_NAME = "Peter Jones";
    public final String WRONG_NAME = "";
    public final String PHONE = "+375297435874";
    public final String SECOND_PHONE = "+375331675879";
    public final String NON_EXISTING_PHONE = "+375297415874";
    public final String WRONG_PHONE = "+3752974fsd5874";
    public final Sort SORT = Sort.by(Sort.Direction.ASC, "id");
    public final String SORT_NAME = "ID_ASC";
    public final LocalDate BIRTH_DATE = LocalDate.of(2000, 1, 1);
    public final LocalDate SECOND_BIRTH_DATE = LocalDate.of(1980, 10, 20);
    public final LocalDate WRONG_BIRTH_DATE = LocalDate.of(2999, 5, 23);
    public final Gender GENDER = Gender.Men;
    public final Gender SECOND_GENDER = Gender.Women;
    public final String WRONG_GENDER = "Gender";
    public final Double RATING = 0.0;
    public final Double SECOND_RATING = 3.0;
    public final Integer PAGE = 0;
    public final Integer LIMIT = 10;
    public final String PASSENGER_NOT_FOUND_MESSAGE = "Пассажир не найден";
    public final String DEFAULT_PATH = "/api/v1/passengers";
    public final String AVG_REQUEST = "/avg/";
    public final String AVG_REQUEST_WITH_UUID = "/avg/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    public Passenger getPassenger() {
        return Passenger.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER)
                .build();
    }

    public Passenger getSecondPassenger() {
        return Passenger.builder()
                .id(SECOND_ID)
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER)
                .build();
    }

    public Passenger getNonSavedPassenger() {
        return Passenger.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER)
                .build();
    }

    public List<Passenger> getPassengers() {
        return List.of(
                getPassenger(),
                getSecondPassenger()
        );
    }

    public PassengerRequest getPassengerRequest() {
        return PassengerRequest.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER.name())
                .build();
    }

    public PassengerRequest getPassengerRequest(String username, String phone) {
        return PassengerRequest.builder()
                .name(username)
                .birthDate(BIRTH_DATE)
                .phoneNumber(phone)
                .gender(GENDER.name())
                .build();
    }

    public PassengerRequest getNonExistingPassengerRequest() {
        return PassengerRequest.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(NON_EXISTING_PHONE)
                .gender(GENDER.name())
                .build();
    }

    public PassengerRequest getInvalidPassengerRequest() {
        return PassengerRequest.builder()
                .name(WRONG_NAME)
                .birthDate(WRONG_BIRTH_DATE)
                .phoneNumber(WRONG_PHONE)
                .gender(WRONG_GENDER)
                .build();
    }

    public PassengerRequest getEditPassengerRequest() {
        return PassengerRequest.builder()
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER.name())
                .build();
    }

    public PassengerResponse getPassengerResponse() {
        return PassengerResponse.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER.name())
                .avgRating(RATING)
                .build();
    }

    public PassengerResponse getSecondPassengerResponse() {
        return PassengerResponse.builder()
                .id(SECOND_ID)
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER.name())
                .avgRating(SECOND_RATING)
                .build();
    }

    public PassengerResponse getCreatedPassengerResponse() {
        return PassengerResponse.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(NON_EXISTING_PHONE)
                .gender(GENDER.name())
                .avgRating(RATING)
                .build();
    }

    public PassengerResponse getEditPassengerResponse() {
        return PassengerResponse.builder()
                .id(ID)
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER.name())
                .avgRating(SECOND_RATING)
                .build();
    }

    public List<PassengerResponse> getPassengerResponses() {
        return List.of(
                getSecondPassengerResponse(),
                getPassengerResponse()
        );
    }

    public void updatePassenger(Passenger passenger, PassengerRequest passengerRequest) {
        passenger.setBirthDate(passengerRequest.getBirthDate());
        passenger.setGender(Gender.valueOf(passengerRequest.getGender()));
        passenger.setName(passengerRequest.getName());
        passenger.setPhoneNumber(passengerRequest.getPhoneNumber());
    }

    public FindAllParams getFindAllParams() {
        return FindAllParams.builder()
                .page(TestUtil.PAGE)
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

    public Page<Passenger> getPageOfPassengers() {
        return new PageImpl<>(getPassengers());
    }

    public PageResponse<PassengerResponse> getPageResponse() {
        Page<Passenger> passengerPage = getPageOfPassengers();
        return PageResponse.<PassengerResponse>builder()
                .objectList(getPassengerResponses())
                .totalElements(passengerPage.getTotalElements())
                .totalPages(passengerPage.getTotalPages())
                .build();
    }
}
