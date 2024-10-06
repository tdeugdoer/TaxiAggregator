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
    public final UUID DEFAULT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID DEFAULT_SECOND_ID = UUID.fromString("22222222-1111-1111-1111-222222222222");
    public final String DEFAULT_NAME = "USERNAME1";
    public final String DEFAULT_PHONE = "+375292078876";
    public final String DEFAULT_SECOND_PHONE = "+375448713245";
    public final Double DEFAULT_RATING = 5.0;
    public final Double DEFAULT_SECOND_RATING = 8.0;
    public final Integer DEFAULT_PAGE = 0;
    public final Integer DEFAULT_LIMIT = 10;
    public final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "id");
    private static final String DEFAULT_SECOND_NAME = "USERNAME2";
    private final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(2000, 1, 1);
    private final LocalDate DEFAULT_SECOND_BIRTH_DATE = LocalDate.of(1999, 5, 23);
    private static final Gender DEFAULT_GENDER = Gender.Men;
    private static final Gender DEFAULT_SECOND_GENDER = Gender.Women;
    private static final String DEFAULT_GENDER_NAME = Gender.Men.name();
    private static final String DEFAULT_SECOND_GENDER_NAME = Gender.Women.name();

    public Passenger getPassenger() {
        return Passenger.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER)
                .build();
    }

    public Passenger getSecondPassenger() {
        return Passenger.builder()
                .id(DEFAULT_SECOND_ID)
                .name(DEFAULT_SECOND_NAME)
                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
                .phoneNumber(DEFAULT_SECOND_PHONE)
                .gender(DEFAULT_SECOND_GENDER)
                .build();
    }

    public Passenger getNonSavedPassenger() {
        return Passenger.builder()
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER)
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
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER_NAME)
                .build();
    }

    public PassengerRequest getEditPassengerRequest() {
        return PassengerRequest.builder()
                .name(DEFAULT_SECOND_NAME)
                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
                .phoneNumber(DEFAULT_SECOND_PHONE)
                .gender(DEFAULT_SECOND_GENDER_NAME)
                .build();
    }

    public PassengerResponse getPassengerResponse() {
        return PassengerResponse.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .phoneNumber(DEFAULT_PHONE)
                .gender(DEFAULT_GENDER_NAME)
                .avgRating(DEFAULT_RATING)
                .build();
    }

    public PassengerResponse getSecondPassengerResponse() {
        return PassengerResponse.builder()
                .id(DEFAULT_SECOND_ID)
                .name(DEFAULT_SECOND_NAME)
                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
                .phoneNumber(DEFAULT_SECOND_PHONE)
                .gender(DEFAULT_SECOND_GENDER_NAME)
                .avgRating(DEFAULT_SECOND_RATING)
                .build();
    }

    public PassengerResponse getEditPassengerResponse() {
        return PassengerResponse.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_SECOND_NAME)
                .birthDate(DEFAULT_SECOND_BIRTH_DATE)
                .phoneNumber(DEFAULT_SECOND_PHONE)
                .gender(DEFAULT_SECOND_GENDER.name())
                .avgRating(DEFAULT_SECOND_RATING)
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
                .page(TestUtil.DEFAULT_PAGE)
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
