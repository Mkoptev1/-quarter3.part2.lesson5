package com.geekbrains.services;

import com.geekbrains.entities.Client;
import com.geekbrains.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl {
    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository _clientRepository) {
        this.clientRepository = _clientRepository;
    }

    @Transactional(readOnly = true)
    public List<Client> getAll() {
        return (List<Client>)clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Client> get(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public void save(Client article) {
        clientRepository.save(article);
    }
}
