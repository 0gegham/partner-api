package com.sparkers.partner.service.impl;

import com.sparkers.partner.model.Partner;
import com.sparkers.partner.repostiory.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartnerServiceImplTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    private Partner partner;

    @BeforeEach
    void setUp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        partner = new Partner(1L, "randomName", "xxxx",
                "fr_FR", zonedDateTime);
    }

    @Test
    void save() {
        when(partnerRepository.save(partner)).thenReturn(partner);
        Partner partner = partnerService.save(this.partner);
        assertEquals(this.partner, partner);
        verify(partnerRepository, times(1)).save(partner);
    }

    @Test
    void getAll() {
        List<Partner> partners = List.of(partner);
        Page<Partner> partnerPage = new PageImpl<>(partners);

        when(partnerRepository.findAll(any(Pageable.class))).thenReturn(partnerPage);
        Page<Partner> all = partnerService.getAll(PageRequest.of(0, 1));
        assertTrue(all.getSize() > 0);
    }

    @Test
    void getById() {
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partner));
        Partner partnerById = partnerService.getById(1L);
        assertEquals(partner, partnerById);
    }

    @Test
    void modify() {
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partner));
        when(partnerRepository.save(partner)).thenReturn(partner);
        Partner modifiedPartner = partnerService.modify(1L, partner);
        assertEquals(partner, modifiedPartner);
    }

    @Test
    void removeById() {
        partnerService.removeById(1L);
        verify(partnerRepository, times(1)).deleteById(1L);
    }
}
