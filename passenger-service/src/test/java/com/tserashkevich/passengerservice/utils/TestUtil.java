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
import java.util.UUID;

@UtilityClass
public class TestUtil {
    public final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID SECOND_ID = UUID.fromString("22222222-1111-1111-1111-222222222222");
    public final String NAME = "USERNAME1";
    public final String PHONE = "+375292078876";
    public final String SECOND_PHONE = "+375448713245";
    public final Double RATING = 5.0;
    public final Double SECOND_RATING = 8.0;
    public final Integer PAGE = 0;
    public final Integer LIMIT = 10;
    public final Sort SORT = Sort.by(Sort.Direction.ASC, "id");
    private static final String SECOND_NAME = "USERNAME2";
    private final LocalDate BIRTH_DATE = LocalDate.of(2000, 1, 1);
    private final LocalDate SECOND_BIRTH_DATE = LocalDate.of(1999, 5, 23);
    private static final Gender GENDER = Gender.Men;
    private static final Gender SECOND_GENDER = Gender.Women;
    private static final String GENDER_NAME = Gender.Men.name();
    private static final String SECOND_GENDER_NAME = Gender.Women.name();

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
                .gender(GENDER_NAME)
                .build();
    }

    public PassengerRequest getEditPassengerRequest() {
        return PassengerRequest.builder()
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER_NAME)
                .build();
    }

    public PassengerResponse getPassengerResponse() {
        return PassengerResponse.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .phoneNumber(PHONE)
                .gender(GENDER_NAME)
                .avgRating(RATING)
                .build();
    }

    public PassengerResponse getSecondPassengerResponse() {
        return PassengerResponse.builder()
                .id(SECOND_ID)
                .name(SECOND_NAME)
                .birthDate(SECOND_BIRTH_DATE)
                .phoneNumber(SECOND_PHONE)
                .gender(SECOND_GENDER_NAME)
                .avgRating(SECOND_RATING)
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
                getPassengerResponse(),
                getSecondPassengerResponse()
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

    public Predicate getPredicate() {
        return ExpressionUtils.allOf(Expressions.TRUE);
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(PAGE, LIMIT, SORT);
    }

    public Page<Passenger> getPageOfPassengers() {
        return new PageImpl<>(getPassengers());
    }

    public PageResponse<PassengerResponse> getPageResponse(List<PassengerResponse> passengerResponses) {
        Page<Passenger> passengerPage = getPageOfPassengers();
        return PageResponse.<PassengerResponse>builder()
                .objectList(passengerResponses)
                .totalElements(passengerPage.getTotalElements())
                .totalPages(passengerPage.getTotalPages())
                .build();
    }
}
