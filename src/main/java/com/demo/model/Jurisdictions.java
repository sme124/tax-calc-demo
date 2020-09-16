package com.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "juridiction")
@NoArgsConstructor
@Getter
@Setter
public class Jurisdictions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String county;

    private String state;

    private String city;

    private String countryIsoCode;
}
