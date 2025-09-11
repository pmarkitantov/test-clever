package com.example.test_clever.legacy.dto;

public class NoteDto {
    private String clientGuid;
    private String agency;
    private String comments;
    private String createdDateTime;
    private String modifiedDateTime;
    private String loggedUser;

    public NoteDto() {
    }

    public NoteDto(String clientGuid, String agency, String comments,
                   String createdDateTime, String modifiedDateTime, String loggedUser) {
        this.clientGuid = clientGuid;
        this.agency = agency;
        this.comments = comments;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.loggedUser = loggedUser;
    }

    public String getClientGuid() {
        return clientGuid;
    }

    public void setClientGuid(String clientGuid) {
        this.clientGuid = clientGuid;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(String modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    @Override
    public String toString() {
        return "NoteDto{clientGuid='" + clientGuid + "', agency='" + agency + "', comments='" + comments +
                "', createdDateTime='" + createdDateTime + "', modifiedDateTime='" + modifiedDateTime +
                "', loggedUser='" + loggedUser + "'}";
    }
}