package com.vm.vending.application.mapper;

import com.vm.common.api.dto.request.GetAvailableProductsRequestDto;
import com.vm.common.api.dto.response.GetAvailableProductsResponseDto;
import com.vm.common.domain.model.Product;
import com.vm.vending.application.command.GetAvailableProductsCommand;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Map;

public class DtoToCommandMapper {
    public static GetAvailableProductsCommand toCommand(GetAvailableProductsRequestDto command) {
        return new GetAvailableProductsCommand();
    }

    public static GetAvailableProductsResponseDto toDto(Map<Product, Integer> productStock) {
        return new GetAvailableProductsResponseDto(productStock);
    }
}
