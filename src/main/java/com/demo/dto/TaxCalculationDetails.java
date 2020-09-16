package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxCalculationDetails {

    private String fromCountry;
    private String fromPostalCode;
    @NotNull(message = "Country Iso code is required")
    private String toCountry;
    @NotNull(message = "Postal code where the order would be deliver is required")
    private String toPostalCode;
    @NotNull(message = "State ISO code where the order would be deliver is required")
    private String toState;
    private double amount;
    @NotNull(message = "Shipping amount is required for tax calculation")
    private double shippingAmount;

}
