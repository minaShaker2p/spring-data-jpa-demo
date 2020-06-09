package com.mina.springdata.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class Flight {

    private String id;

    private String origin;

    public String getId() {
        return id;
    }

    private String destination;

    private LocalDateTime scheduledTime;


}
