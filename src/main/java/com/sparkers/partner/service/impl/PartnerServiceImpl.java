package com.sparkers.partner.service.impl;

import com.sparkers.partner.exception.NotFoundException;
import com.sparkers.partner.model.Partner;
import com.sparkers.partner.repostiory.PartnerRepository;
import com.sparkers.partner.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    @Override
    @Transactional(readOnly = false)
    public Partner save(Partner partner) {
        if (!isCorrectLocale(partner.getLocale())) {
            throw new NotFoundException("Incorrect/Not available locale: " + partner.getLocale() + " not found");
        }

        log.info("Create a new partner");
        return partnerRepository.save(partner);
    }

    @Override
    public Page<Partner> getAll(Pageable pageable) {
        log.info("Get all partners. Start " + pageable.getOffset() + ". Page size " + pageable.getPageSize());
        return partnerRepository.findAll(pageable);
    }

    @Override
    public Partner getById(Long id) {
        log.info("Get partner by id " + id);
        return partnerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Partner with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = false)
    public Partner modify(Long id, Partner partner) {
        log.info("Modify partner by id " + id);
        Partner partnerById = getById(id);
        return save(partnerById);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeById(Long id) {
        log.info("Delete partner by id " + id);
        partnerRepository.deleteById(id);
    }

    private boolean isCorrectLocale(String locale) {
        return LocaleUtils.isAvailableLocale(LocaleUtils.toLocale(locale));
    }
}
