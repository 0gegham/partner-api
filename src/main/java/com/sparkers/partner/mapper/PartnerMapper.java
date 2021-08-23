package com.sparkers.partner.mapper;

import com.sparkers.partner.dto.PartnerDto;
import com.sparkers.partner.model.Partner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerMapper {
    Partner dtoToEntity(PartnerDto dto);
    PartnerDto entityToDto(Partner entity);
}
