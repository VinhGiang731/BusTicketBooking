package com.project.busticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.project.busticket.dto.request.busoperator.BusOperatorRequest;
import com.project.busticket.dto.response.BusOperatorResponse;
import com.project.busticket.entity.BusOperator;

@Mapper(componentModel = "spring")
public interface BusOperatorMapper {
    BusOperator toBusOperator(BusOperatorRequest request);

    BusOperatorResponse toBusOperatorResponse(BusOperator busOperator);

    void updateInfo(@MappingTarget BusOperator busOperator, BusOperatorRequest request);
}
