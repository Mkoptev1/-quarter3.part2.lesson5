package com.geekbrains.repositories;

import com.geekbrains.entities.Ware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WareRepository extends PagingAndSortingRepository<Ware, Long> {
    @Query("select a from Ware a where a.ware_cost = " +
        "(" +
            "select Max(b.ware_cost) " +
            "from Ware b" +
        ")"
    ) List<Ware> findMaxPrice();

    @Query(
        "select a " +
        "from Ware a " +
        "where a.ware_cost = " +
        "(" +
            "select Min(b.ware_cost) " +
            "from Ware b" +
        ")"
    ) List<Ware> findMinPrice();

    @Query(
        "select a " +
        "from Ware a " +
        "where a.ware_cost in " +
        "(" +
        "   select Min(b.ware_cost) " +
            "from Ware b" +
        ") " +
        " or a.ware_cost in" +
        "(" +
            "select Max(b.ware_cost) " +
            "from Ware b" +
        ")"
    ) List<Ware> findMinOrMaxPrice();

    @Query("select a from Ware a")
    Page<Ware> showAllWare(Pageable pageable);
}