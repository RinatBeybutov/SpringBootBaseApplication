package com.example.demo.DTO;

public class OkResponse {
    private String status;

    public OkResponse()
    {}

    public OkResponse(String status)
    {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
