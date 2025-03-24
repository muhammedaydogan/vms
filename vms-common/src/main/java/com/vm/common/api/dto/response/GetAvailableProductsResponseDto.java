package com.vm.common.api.dto.response;

import com.vm.common.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAvailableProductsResponseDto {
    Map<Product, Integer> productStock;
}
