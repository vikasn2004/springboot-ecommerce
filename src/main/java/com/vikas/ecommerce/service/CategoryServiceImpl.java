package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.CategoryRequestDTO;
import com.vikas.ecommerce.DTO.CategoryResponseDTO;
import com.vikas.ecommerce.entities.Category;
import com.vikas.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {
        if (categoryRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Category already exists");
        }
        Category category = modelMapper.map(request, Category.class);
        categoryRepository.save(category);
        log.info("Created category with name {}", category.getName());
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(i-> modelMapper.map(i,CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Category not found"));
        categoryRepository.delete(category);
        log.info("Deleted category with id {}", id);

    }
}
