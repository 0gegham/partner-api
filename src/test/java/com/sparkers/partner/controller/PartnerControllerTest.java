package com.sparkers.partner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.sparkers.partner.dto.PartnerDto;
import com.sparkers.partner.mapper.PartnerMapper;
import com.sparkers.partner.model.Partner;
import com.sparkers.partner.service.PartnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PartnerControllerTest {

    @Mock
    private PartnerService partnerService;

    @Mock
    private PartnerMapper partnerMapper;

    @InjectMocks
    private PartnerController partnerController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Partner partner;
    private PartnerDto partnerDto;

    @BeforeEach
    void setUp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        partner = new Partner(1L, "randomName", "xxxx",
                "fr_FR", zonedDateTime);
        partnerDto = new PartnerDto(partner.getId(), partner.getName(), partner.getReference(),
                partner.getLocale(), zonedDateTime);

        objectMapper = JsonMapper.builder()
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();
        mockMvc = MockMvcBuilders.standaloneSetup(partnerController).build();
    }

    @Test
    void save() throws Exception {
        when(partnerMapper.dtoToEntity(any(PartnerDto.class))).thenReturn(partner);
        when(partnerService.save(any(Partner.class))).thenReturn(partner);
        when(partnerMapper.entityToDto(any(Partner.class))).thenReturn(partnerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/partners")
                        .content(objectMapper.writeValueAsString(partnerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(partnerDto)))
                .andExpect(content().contentType("application/json"));

        verify(partnerService, times(1)).save(partner);
    }

    @Test
    void getAll() throws Exception {
        List<Partner> partners = List.of(partner);
        Page<Partner> partnerPage = new PageImpl<>(partners);

        when(partnerMapper.entityToDto(partner)).thenReturn(partnerDto);
        when(partnerService.getAll(any(Pageable.class))).thenReturn(partnerPage);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from", String.valueOf(0));
        params.add("size", String.valueOf(10));

        mockMvc.perform(get("/partners")
                        .params(params)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        when(partnerService.getById(1L)).thenReturn(partner);
        when(partnerMapper.entityToDto(partner)).thenReturn(partnerDto);

        mockMvc.perform(get("/partners/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(partnerDto)))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void modify() throws Exception {
        when(partnerMapper.dtoToEntity(any(PartnerDto.class))).thenReturn(partner);
        when(partnerService.modify(1L, partner)).thenReturn(partner);
        when(partnerMapper.entityToDto(any(Partner.class))).thenReturn(partnerDto);

        mockMvc.perform(put("/partners/1")
                    .content(objectMapper.writeValueAsString(partnerDto))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(partnerDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void removeById() throws Exception {
        mockMvc.perform(delete("/partners/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(partnerService, times(1)).removeById(1L);
    }
}