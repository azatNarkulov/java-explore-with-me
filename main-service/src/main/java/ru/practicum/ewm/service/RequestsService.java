package ru.practicum.ewm.service;

public interface RequestsService {

    void addRequest();

    void getRequests();

    void cancelRequest();

    void getEventRequests();

    void updateStatus();
}
