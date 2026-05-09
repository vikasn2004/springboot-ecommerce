package com.vikas.ecommerce.service;


import com.vikas.ecommerce.DTO.CategoryRequestDTO;
import com.vikas.ecommerce.DTO.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO request);
    List<CategoryResponseDTO> getAllCategories();
    void deleteCategory(Long id);
}
