package com.itsoft.jobs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScheduleType {
    IMMEDIATE(0), P1H(1), P2H(2), P6H(6), P12H(12), P2S(2);   // P1H stands for periodic task every 1 hour, P2S - every 2 seconds

    int timeUnits;
}
