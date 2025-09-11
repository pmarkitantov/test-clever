package com.example.test_clever.legacy.dto;

public class ClientDto {
    private String guid;
    private String agency;

    public ClientDto() {
    }

    public ClientDto(String guid, String agency) {
        this.guid = guid;
        this.agency = agency;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    @Override
    public String toString() {
        return "ClientDto{guid='" + guid + "', agency='" + agency + "'}";
    }
}