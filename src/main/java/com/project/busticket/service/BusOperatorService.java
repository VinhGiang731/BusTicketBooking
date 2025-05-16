package com.project.busticket.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.project.busticket.dto.request.busoperator.BusOperatorRequest;
import com.project.busticket.dto.response.BusOperatorResponse;
import com.project.busticket.entity.BusOperator;
import com.project.busticket.exception.Appexception;
import com.project.busticket.exception.ErrorCode;
import com.project.busticket.mapper.BusOperatorMapper;
import com.project.busticket.repository.BusOperatorRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BusOperatorService {
    BusOperatorRepository busOperatorRepository;
    BusOperatorMapper busOperatorMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public BusOperator createBusOperator(BusOperatorRequest request) {
        if (busOperatorRepository.existsByBusOperatorName(request.getBusOperatorName()))
            throw new Appexception(ErrorCode.BUS_EXISTED);

        BusOperator bus = busOperatorMapper.toBusOperator(request);

        return busOperatorRepository.save(bus);
    }

    public List<BusOperatorResponse> getBusOperator() {
        return busOperatorRepository.findAll().stream().map(busOperatorMapper::toBusOperatorResponse).toList();
    }

    public BusOperatorResponse getBusOperatorById(String id) {
        return busOperatorMapper.toBusOperatorResponse(
                busOperatorRepository.findById(id).orElseThrow(() -> new Appexception(ErrorCode.BUS_UN_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BusOperatorResponse updateBusOperator(String id, BusOperatorRequest request) {

        BusOperator bus = busOperatorRepository.findById(id)
                .orElseThrow(() -> new Appexception(ErrorCode.BUS_UN_EXISTED));

        busOperatorMapper.updateInfo(bus, request);

        return busOperatorMapper.toBusOperatorResponse(busOperatorRepository.save(bus));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBusOperator(String id) {
        if (!busOperatorRepository.existsById(id)) {
            return "Bus Operator does not exist";
        }

        else {
            busOperatorRepository.deleteById(id);
            return "Bus Operator has been deleted";
        }
    }

    public String urlImgBusOperator(String id) {
        BusOperatorResponse response = busOperatorMapper.toBusOperatorResponse(
                busOperatorRepository.findById(id).orElseThrow(() -> new Appexception(ErrorCode.BUS_UN_EXISTED)));

        return response.getImgUrl();
    }

    public String busOperatorName(String id) {
        BusOperatorResponse response = busOperatorMapper.toBusOperatorResponse(
                busOperatorRepository.findById(id).orElseThrow(() -> new Appexception(ErrorCode.BUS_UN_EXISTED)));

        return response.getBusOperatorName();
    }
}
