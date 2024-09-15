package com.tserashkevich.ratingservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryPredicate {
    private final List<CriteriaDefinition> criteriaDefinitions = new ArrayList<>();
    private Pageable pageable = null;

    public static QueryPredicate builder() {
        return new QueryPredicate();
    }

    public QueryPredicate add(Object object, CriteriaDefinition criteriaDefinition) {
        if (object != null) {
            criteriaDefinitions.add(criteriaDefinition);
        }
        return this;
    }

    public QueryPredicate with(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }

    public Query build() {
        return Query.query(criteriaDefinitions).pageRequest(pageable).withAllowFiltering();
    }
}
