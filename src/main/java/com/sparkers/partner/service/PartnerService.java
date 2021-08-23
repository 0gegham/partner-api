package com.sparkers.partner.service;

import com.sparkers.partner.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartnerService {
    Partner save(Partner partner);
    Page<Partner> getAll(Pageable pageable);
    Partner getById(Long id);
    Partner modify(Long id, Partner partner);
    void removeById(Long id);
}
