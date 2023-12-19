package com.qiu.service;

public interface TimeService {
    String getCurrentFormattedDateTime();
    String getCurrentMonth(String dateTimeString);
    String getNextMonth(String dateTimeString);

    String convertTimeCodeToChineseDescription(String timeCode);

    String getPreviousMonth(String dateTimeString);
}
