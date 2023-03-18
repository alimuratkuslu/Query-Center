package com.bizu.querycenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Employee id
    // Aynı rapora birkaç kişinin yetkisi olabilir farklı düşün
    private Boolean isOwner;
    private Boolean isRead;
    private Boolean isWrite;
    private Boolean isRun;


}
