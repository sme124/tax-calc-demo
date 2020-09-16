package com.demo.controller;

import com.demo.dto.TaxCalculationDetails;
import com.demo.service.TaxService;
import com.taxjar.model.taxes.TaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tax-calculations")
public class TaxCalculateController {

    @Autowired
    private TaxService taxService;

    @PostMapping
    public ResponseEntity<TaxResponse> calculateTax(@RequestBody @Valid TaxCalculationDetails taxCalculationDetails) {
        return ResponseEntity.ok().body(taxService.calculateTax(taxCalculationDetails));
    }
}
