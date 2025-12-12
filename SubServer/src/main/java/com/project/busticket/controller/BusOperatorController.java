package com.project.busticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.busticket.dto.request.busoperator.BusOperatorRequest;
import com.project.busticket.dto.response.ApiResponse;
import com.project.busticket.dto.response.BusOperatorResponse;
import com.project.busticket.entity.BusOperator;
import com.project.busticket.service.BusOperatorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/busoperator")
public class BusOperatorController {
    @Autowired
    BusOperatorService service;

    @PostMapping
    ApiResponse<BusOperator> createBusOperator(@RequestBody @Valid BusOperatorRequest request) {
        ApiResponse<BusOperator> apiResponse = new ApiResponse<>();

        apiResponse.setResult(service.createBusOperator(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<BusOperatorResponse>> getListBusOpe() {
        return ApiResponse.<List<BusOperatorResponse>>builder()
                .result(service.getBusOperator())
                .build();
    }

    @GetMapping("/img/{id}")
    String getImg(@PathVariable String id) {
        return service.urlImgBusOperator(id);
    }

    @GetMapping("/name/{id}")
    String getBusOperatorName(@PathVariable String id) {
        return service.busOperatorName(id);
    }

    @PutMapping("/update/{id}")
    ApiResponse<BusOperatorResponse> updateBusOperator(@PathVariable String id,
            @RequestBody @Valid BusOperatorRequest request) {
        return ApiResponse.<BusOperatorResponse>builder()
                .result(service.updateBusOperator(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteBusOpe(@PathVariable String id) {
        return ApiResponse.<String>builder()
                .result(service.deleteBusOperator(id))
                .build();
    }
}
