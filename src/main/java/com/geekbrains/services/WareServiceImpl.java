package com.geekbrains.services;

import com.geekbrains.entities.Ware;
import com.geekbrains.repositories.WareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WareServiceImpl{
    private WareRepository wareRepository;

    @Autowired
    public void setWareRepository(WareRepository _wareRepository) {
        this.wareRepository = _wareRepository;
    }


    @Transactional(readOnly = true)
    public Page<Ware> getAll(Pageable pageable) {
        return (Page<Ware>) wareRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Ware> get(Long id) {
        return wareRepository.findById(id);
    }

    @Transactional
    public void save(Ware ware) {
        wareRepository.save(ware);
    }

    @Transactional
    public void delete(Long ware_id) {
        wareRepository.deleteById(ware_id);
    }

    @Transactional
    public List<Ware> findMaxPrice(Long _filter_id) {
        // Фильтр по минимальной цене
        if (_filter_id == 1L) {
            return (List<Ware>) wareRepository.findMinPrice();
        // Фильтр по максимальной цене
        } else if (_filter_id == 2L) {
            return (List<Ware>) wareRepository.findMaxPrice();
        // Фильтр по минимальной и максимальной ценам
        } else if (_filter_id == 3L) {
            return (List<Ware>) wareRepository.findMinOrMaxPrice();
        // Вывод всех товаров
        } else {
            return (List<Ware>) wareRepository.findAll();
        }
    }

    @Transactional
    public Page<Ware> showAllWare(Pageable pageable) {
        return (Page<Ware>) wareRepository.showAllWare(pageable);
    }
}