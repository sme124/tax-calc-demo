package com.demo.service;

import com.demo.config.TaxJarConfig;
import com.demo.dto.TaxCalculationDetails;
import com.demo.exception.error.CustomParameterizedException;
import com.demo.model.Jurisdictions;
import com.demo.model.TaxDetails;
import com.demo.reposiroty.JurisdictionRepository;
import com.demo.reposiroty.TaxDetailRepository;
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.rates.RateResponse;
import com.taxjar.model.taxes.TaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TaxCalculationServiceImpl implements TaxService {

    @Autowired
    private TaxJarConfig taxJarConfig;
    @Autowired
    private JurisdictionRepository jurisdictionRepository;
    @Autowired
    private TaxDetailRepository taxDetailRepository;

    @Override
    public TaxResponse calculateTax(TaxCalculationDetails taxData) {
        Taxjar client = taxJarConfig.getClient();

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("from_country", taxData.getFromCountry());
            params.put("from_zip", taxData.getFromPostalCode());
            params.put("to_country", taxData.getToCountry());
            params.put("to_zip", taxData.getToPostalCode());
            params.put("to_state", taxData.getToState());
            params.put("amount", taxData.getAmount());
            params.put("shipping", taxData.getShippingAmount());

            List<Map> nexusAddresses = new ArrayList();
            Map<String, Object> nexusAddress = new HashMap<>();
            nexusAddress.put("country", taxData.getToCountry());
            nexusAddress.put("zip", taxData.getToPostalCode());
            nexusAddress.put("state", taxData.getToState());
            nexusAddresses.add(nexusAddress);

            params.put("nexus_addresses", nexusAddresses);
            TaxResponse response = client.taxForOrder(params);
            mapToJurisdiction(response);
            mapToTaxDetails(response);
            return response;
        } catch (TaxjarException e) {
            throw new CustomParameterizedException(e, e.getMessage(), e.getStatusCode());
        }
    }

    public RateResponse getRates(Taxjar client, TaxCalculationDetails taxData) {

        try {
            RateResponse res = client.ratesForLocation("90404-3370");
           // RateResponse res = client.ratesForLocation(taxData.getToPostalCode());

         /*   Map<String, String> params = new HashMap<>();
            params.put("city", "Santa Monica");
            params.put("state", "CA");
            params.put("country", "US");
            RateResponse res = client.ratesForLocation("90404", params);*/
         return res;

        } catch (TaxjarException e) {
            throw new CustomParameterizedException(e, "Error while fetching the rate for tax calculations", e.getStatusCode());
        }
    }

    private void mapToJurisdiction(TaxResponse response) {
        Jurisdictions jurisdictions = new Jurisdictions();
        com.taxjar.model.taxes.Jurisdictions jurisdictionsResponse = response.tax.getJurisdictions();
        jurisdictions.setCity(jurisdictionsResponse.getCity());
        jurisdictions.setCountryIsoCode(jurisdictionsResponse.getCountry());
        jurisdictions.setState(jurisdictionsResponse.getState());
        jurisdictions.setCounty(jurisdictionsResponse.getCounty());
        jurisdictionRepository.save(jurisdictions);
    }

    private void mapToTaxDetails(TaxResponse response) {
        TaxDetails taxDetails = new TaxDetails();
        taxDetails.setAmountToCollect(response.tax.getAmountToCollect().doubleValue());
        taxDetails.setShippingAmount(response.tax.getShipping().doubleValue());
        taxDetails.setTaxableAmount(response.tax.getTaxableAmount().doubleValue());
        taxDetails.setTotalAmount(response.tax.getOrderTotalAmount().doubleValue());
        taxDetails.setRate(Double.valueOf(response.tax.getRate()));
        taxDetails.setHasNexus(response.tax.getHasNexus());
        taxDetails.setTaxSource(response.tax.getTaxSource());
        taxDetailRepository.save(taxDetails);
    }
}
