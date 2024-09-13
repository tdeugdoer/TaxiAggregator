package com.tserashkevich.rideservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryPredicate {
    private final Query query = new Query();

    public static QueryPredicate builder() {
        return new QueryPredicate();
    }

    public <T> QueryPredicate add(T object, CriteriaDefinition criteriaDefinition) {
        if (object != null) {
            query.addCriteria(criteriaDefinition);
        }
        return this;
    }

    public QueryPredicate with(Pageable pageable) {
        this.query.with(pageable);
        return this;
    }

    public Query build() {
        return query;
    }

}
