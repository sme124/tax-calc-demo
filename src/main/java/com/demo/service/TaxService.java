package com.demo.service;

import com.demo.dto.TaxCalculationDetails;
import com.taxjar.model.taxes.TaxResponse;

public interface TaxService {

    TaxResponse calculateTax(TaxCalculationDetails taxCalculationDetails);
}
