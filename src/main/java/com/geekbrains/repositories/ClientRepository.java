package com.geekbrains.repositories;

import com.geekbrains.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
    @Query("select a from Client a")
    Page<Client> showAllClient(Pageable pageable);
}