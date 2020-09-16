package com.demo.reposiroty;

import com.demo.model.TaxDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxDetailRepository extends JpaRepository<TaxDetails, Long> {
}
