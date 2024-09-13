package com.tserashkevich.ratingservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("ratings")
public class Rating {
    @PrimaryKey
    private UUID id;
    private UUID sourceId;
    private UUID targetId;
    private String comment;
}
