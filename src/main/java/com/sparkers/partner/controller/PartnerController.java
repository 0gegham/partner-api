package com.sparkers.partner.controller;

import com.sparkers.partner.dto.PartnerDto;
import com.sparkers.partner.mapper.PartnerMapper;
import com.sparkers.partner.model.Partner;
import com.sparkers.partner.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/partners")
public class PartnerController {

    private final PartnerService partnerService;
    private final PartnerMapper partnerMapper;

    @Operation(summary = "Create new partner")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PartnerDto save(@Valid @RequestBody PartnerDto partnerDto) {
        final Partner partner = partnerService.save(partnerMapper.dtoToEntity(partnerDto));
        return partnerMapper.entityToDto(partner);
    }

    @GetMapping
    @Operation(summary = "Get all partners")
    public Page<PartnerDto> getAll(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return partnerService.getAll(PageRequest.of(from, size)).map(partnerMapper::entityToDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get partner by id")
    public PartnerDto getById(@PathVariable(value = "id") Long id) {
        return partnerMapper.entityToDto(partnerService.getById(id));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Modify partner by id")
    public PartnerDto modify(@PathVariable(value = "id") Long id,
                             @RequestBody PartnerDto partnerDto) {
        final Partner partner = partnerMapper.dtoToEntity(partnerDto);
        return partnerMapper.entityToDto(partnerService.modify(id, partner));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete partner by id")
    public void removeById(@PathVariable(value = "id") Long id) {
        partnerService.removeById(id);
    }
}
