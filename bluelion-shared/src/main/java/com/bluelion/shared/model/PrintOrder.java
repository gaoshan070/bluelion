package com.bluelion.shared.model;

import lombok.Data;

import java.util.Date;

@Data
public class PrintOrder {

    private Integer id;
    private Date printDate;
    private String plateNumber;
    private Integer serviceId;
    private String serviceName;
    private String nextService;
    private Integer mid;

}
