package com.tserashkevich.ratingservice.unit;

import com.tserashkevich.ratingservice.dtos.*;
import com.tserashkevich.ratingservice.exceptions.RatingExistException;
import com.tserashkevich.ratingservice.exceptions.RatingNotFoundException;
import com.tserashkevich.ratingservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.ratingservice.mappers.RatingMapper;
import com.tserashkevich.ratingservice.models.Rating;
import com.tserashkevich.ratingservice.repositories.RatingRepository;
import com.tserashkevich.ratingservice.service.impl.RatingServiceImpl;
import com.tserashkevich.ratingservice.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceUnitTest {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private RatingMapper ratingMapper;
    @Mock
    private CassandraTemplate cassandraTemplate;
    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    void givenNonExistingRating_whenCreateRatingByRequest_thenCreateNewRating() {
        RatingRequest ratingRequest = TestUtil.getRatingRequest();
        Rating rating = TestUtil.getNonSavedRating();
        RatingResponse ratingResponse = TestUtil.getRatingResponse();

        doReturn(rating)
                .when(ratingMapper)
                .toModel(ratingRequest);
        doReturn(0)
                .when(ratingRepository)
                .existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId());
        doAnswer(invocation -> null)
                .when(ratingRepository)
                .save(rating);
        doReturn(ratingResponse)
                .when(ratingMapper)
                .toRatingResponse(rating);

        RatingResponse result = ratingService.create(ratingRequest);

        rating.setCreationTime(result.getCreationTime());
        rating.setId(result.getId());

        verify(ratingMapper).toModel(ratingRequest);
        verify(ratingRepository).existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId());
        verify(ratingRepository).save(rating);
        verify(ratingMapper).toRatingResponse(rating);
        assertThat(result).isEqualTo(ratingResponse);
    }

    @Test
    void givenExistingRating_whenCreateRatingByRequest_thenThrowException() {
        RatingRequest ratingRequest = TestUtil.getRatingRequest();
        Rating rating = TestUtil.getNonSavedRating();

        doReturn(rating)
                .when(ratingMapper)
                .toModel(ratingRequest);
        doReturn(Integer.MAX_VALUE)
                .when(ratingRepository)
                .existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId());

        assertThatThrownBy(() -> ratingService.create(ratingRequest))
                .isInstanceOf(RatingExistException.class);

        verify(ratingMapper).toModel(ratingRequest);
        verify(ratingRepository).existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId());
        verifyNoMoreInteractions(ratingRepository);
    }

    @Test
    void givenNonExistingRating_whenCreateRatingByEvent_thenThrowException() {
        RatingCreateEvent ratingCreateEvent = TestUtil.getRatingCreateEvent();
        Rating rating = TestUtil.getNonSavedRating();

        doReturn(rating)
                .when(ratingMapper)
                .toModel(ratingCreateEvent);
        doReturn(0)
                .when(ratingRepository)
                .existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId());
        doAnswer(invocation -> null)
                .when(ratingRepository)
                .save(rating);

        ratingService.create(ratingCreateEvent);

        verify(ratingMapper).toModel(ratingCreateEvent);
        verify(ratingRepository).existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId());
        verify(ratingRepository).save(rating);
    }


    @Test
    void givenExistingRating_whenCreateRatingByEvent_thenThrowException() {
        RatingCreateEvent ratingCreateEvent = TestUtil.getRatingCreateEvent();
        Rating rating = TestUtil.getNonSavedRating();

        doReturn(rating)
                .when(ratingMapper)
                .toModel(ratingCreateEvent);
        doReturn(Integer.MAX_VALUE)
                .when(ratingRepository)
                .existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId());

        ratingService.create(ratingCreateEvent);

        verify(ratingMapper).toModel(ratingCreateEvent);
        verify(ratingRepository).existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId());
        verifyNoMoreInteractions(ratingRepository);
    }

    @Test
    void givenExistingRating_whenEditRating_thenUpdateRating() {
        RatingRequest ratingRequest = TestUtil.getEditRatingRequest();
        Rating rating = TestUtil.getRating();
        RatingResponse ratingResponse = TestUtil.getEditRatingResponse();

        doReturn(Optional.of(rating))
                .when(ratingRepository)
                .findById(TestUtil.ID);
        doAnswer(invocation -> {
            TestUtil.updateRating(rating, ratingRequest);
            return null;
        }).when(ratingMapper).updateModel(rating, ratingRequest);
        doReturn(rating)
                .when(ratingRepository)
                .save(rating);
        doReturn(ratingResponse)
                .when(ratingMapper)
                .toRatingResponse(rating);

        RatingResponse result = ratingService.update(TestUtil.ID, ratingRequest);

        verify(ratingRepository).findById(TestUtil.ID);
        verify(ratingMapper).updateModel(rating, ratingRequest);
        verify(ratingRepository).save(rating);
        verify(ratingMapper).toRatingResponse(rating);
        assertThat(result).isEqualTo(ratingResponse);
    }

    @Test
    void givenNonExistingRating_whenEditRating_thenThrowException() {
        RatingRequest ratingRequest = TestUtil.getRatingRequest();

        doReturn(Optional.empty())
                .when(ratingRepository)
                .findById(TestUtil.ID);

        assertThatThrownBy(() -> ratingService.update(TestUtil.ID, ratingRequest))
                .isInstanceOf(RatingNotFoundException.class);

        verify(ratingRepository).findById(TestUtil.ID);
        verifyNoMoreInteractions(ratingRepository);
    }

    @Test
    void givenExistingRating_whenDeleteRating_thenDeleteRating() {
        Rating rating = TestUtil.getRating();

        doReturn(Optional.of(rating))
                .when(ratingRepository)
                .findById(rating.getId());
        doNothing()
                .when(ratingRepository)
                .delete(rating);

        ratingService.delete(rating.getId());

        verify(ratingRepository).findById(rating.getId());
        verify(ratingRepository).delete(rating);
    }

    @Test
    void givenNonExistingRating_whenDeleteRating_thenThrowException() {
        doReturn(Optional.empty())
                .when(ratingRepository)
                .findById(TestUtil.ID);

        assertThatThrownBy(() -> ratingService.delete(TestUtil.ID))
                .isInstanceOf(RatingNotFoundException.class);

        verify(ratingRepository).findById(TestUtil.ID);
        verifyNoMoreInteractions(ratingRepository);
    }

    @Test
    void givenValidParams_whenFindRatings_thenReturnPageOfRatingResponse() {
        FindAllParams findAllParams = TestUtil.getFindAllParams();
        Query query = TestUtil.getQuery();
        Slice<Rating> ratingSlice = TestUtil.getSliceOfRatings();
        List<RatingResponse> ratingResponses = TestUtil.getRatingResponses();
        PageResponse<RatingResponse> ratingPageResponse = TestUtil.getPageResponse(ratingResponses);

        doReturn(ratingSlice)
                .when(cassandraTemplate)
                .slice(query, Rating.class);
        doReturn(ratingResponses)
                .when(ratingMapper)
                .toRatingResponses(ratingSlice.getContent());

        PageResponse<RatingResponse> result = ratingService.findAll(findAllParams);

        verify(cassandraTemplate).slice(query, Rating.class);
        verify(ratingMapper).toRatingResponses(ratingSlice.getContent());
        assertThat(result).isEqualTo(ratingPageResponse);
    }

    @Test
    void givenExistingRating_whenFindByIdRating_thenReturnRatingResponse() {
        Rating rating = TestUtil.getRating();
        RatingResponse ratingResponse = TestUtil.getRatingResponse();

        doReturn(Optional.of(rating))
                .when(ratingRepository)
                .findById(rating.getId());
        doReturn(ratingResponse)
                .when(ratingMapper)
                .toRatingResponse(rating);

        ratingService.findById(rating.getId());

        verify(ratingRepository).findById(rating.getId());
    }

    @Test
    void givenNonExistingRating_whenFindByIdRating_thenThrowException() {
        doReturn(Optional.empty())
                .when(ratingRepository)
                .findById(TestUtil.ID);

        assertThatThrownBy(() -> ratingService.findById(TestUtil.ID))
                .isInstanceOf(RatingNotFoundException.class);

        verify(ratingRepository).findById(TestUtil.ID);
        verifyNoMoreInteractions(ratingRepository);
    }

    @Test
    void givenTargetId_whenFindAvgRating_thenReturnDouble() {
        doReturn(TestUtil.DOUBLE_RATING)
                .when(ratingRepository)
                .countTargetIdAvgRating(TestUtil.TARGET_ID);

        Double result = ratingService.findAvgRating(TestUtil.TARGET_ID);

        verify(ratingRepository).countTargetIdAvgRating(TestUtil.TARGET_ID);
        assertThat(result).isEqualTo(TestUtil.DOUBLE_RATING);
    }

    @Test
    void givenRatingId_whenFindFeedbacks_thenReturnFeedbacks() {
        List<Rating> ratings = TestUtil.getRatings();
        List<Feedback> feedbacks = TestUtil.getFeedbacks();

        doReturn(ratings)
                .when(ratingRepository)
                .findAllByTargetId(TestUtil.TARGET_ID);
        doReturn(feedbacks)
                .when(ratingMapper)
                .toFeedbacks(ratings);

        List<Feedback> result = ratingService.findFeedbacks(TestUtil.TARGET_ID);

        verify(ratingRepository).findAllByTargetId(TestUtil.TARGET_ID);
        verify(ratingMapper).toFeedbacks(ratings);
        assertThat(result).isEqualTo(feedbacks);
    }
}
