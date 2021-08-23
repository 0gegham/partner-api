package com.sparkers.partner.repostiory;

import com.sparkers.partner.model.Partner;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PartnerRepository extends PagingAndSortingRepository<Partner, Long> {
}
