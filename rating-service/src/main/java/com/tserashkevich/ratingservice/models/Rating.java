package com.tserashkevich.ratingservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("ratings")
public class Rating {
    @PrimaryKey
    private UUID id;
    @Column("source_id")
    private UUID sourceId;
    @Column("target_id")
    private UUID targetId;
    private Integer rating;
    private String comment;
    @Column("creation_time")
    private LocalDateTime creationTime;
}
