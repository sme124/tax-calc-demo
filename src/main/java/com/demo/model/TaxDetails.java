package com.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tax_details")
@NoArgsConstructor
@Getter
@Setter
public class TaxDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;

    private Double shippingAmount;

    private Double taxableAmount;

    private Double amountToCollect; // amount to collect as tax

    private Double rate;

    private Boolean hasNexus; //to get the juridiction details

    private String taxSource; //Origin-based or destination-based sales tax collection.
}
