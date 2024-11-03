package com.xplor.read_excel.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "lane_rates")
public class CustomLaneRate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JacksonXmlProperty(localName = "origin_city")
    @Column(name = "origin_city")
    private String originCity;

    @JacksonXmlProperty(localName = "origin_state")
    @Column(name = "origin_state")
    private String originState;

    @JacksonXmlProperty(localName = "destination_city")
    @Column(name = "destination_city")
    private String destinationCity;

    @JacksonXmlProperty(localName = "destination_state")
    @Column(name = "destination_state")
    private String destinationState;

    @JacksonXmlProperty(localName = "rate")
    @Column(name = "rate")
    private Double rate;

}
