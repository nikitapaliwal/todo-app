package com.todo.domain;

import java.time.LocalDateTime;

public class TimePeriod {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public TimePeriod(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public boolean contains(LocalDateTime time) {
        if (time == null) {
            return false;
        }
        return (time.isEqual(from) || time.isAfter(from)) &&
               (time.isEqual(to) || time.isBefore(to));
    }

    public static TimePeriod allTime() {
        return new TimePeriod(LocalDateTime.MIN, LocalDateTime.MAX);
    }
}
