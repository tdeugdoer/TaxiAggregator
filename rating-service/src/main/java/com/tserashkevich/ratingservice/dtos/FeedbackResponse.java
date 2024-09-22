package com.tserashkevich.ratingservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FeedbackResponse {
    private final List<Feedback> feedbacks;
}
