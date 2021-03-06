package com.spring.service.Implementations;

import com.spring.model.Brand;
import com.spring.repository.BrandRepository;
import com.spring.service.interfaces.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand findByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand) {
        brand.setUpdated(new Date());
        return save(brand);
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(UUID id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(UUID id) {
        brandRepository.deleteById(id);
    }
}
