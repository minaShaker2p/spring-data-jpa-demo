package com.mina.springdata.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
public class Flight {

    @Id
    @GeneratedValue
    private Long id;

    private String origin;

    public Long getId() {
        return id;
    }

    private String destination;

    private LocalDateTime scheduledTime;


}
