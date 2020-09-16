package com.demo.reposiroty;

import com.demo.model.Jurisdictions;
import com.demo.model.TaxDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JurisdictionRepository extends JpaRepository<Jurisdictions, Long> {
}
