package com.geekbrains.controllers;

import com.geekbrains.entities.Client;
import com.geekbrains.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientsController {
    private ClientServiceImpl clientServiceImpl;
    private String templateFolder = "client/";

    @Autowired
    public void setClientServiceImpl(ClientServiceImpl _clientServiceImpl) {
        this.clientServiceImpl = _clientServiceImpl;
    }

    // Список клиентов
    // http://localhost:8189/app/сlient
    @GetMapping("/")
    public String getClientList(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber) {
        // Пагинация начинается с 0 элемента
        if (pageNumber > 0) {
            pageNumber --;
        }

        Pageable firstPageWithFiveElements = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.ASC,"client_id"));
        Page<Client> clientList = clientServiceImpl.showAllClient(firstPageWithFiveElements);
        model.addAttribute("client", clientList);
        return templateFolder + "client-list";
    }

    // Форма добавления клиента
    // http://localhost:8189/app/client/add-client
    @GetMapping("/add-client")
    public String newClient(Model model) {
        Client addClient = new Client();
        model.addAttribute("client", addClient);
        return templateFolder + "add-client";
    }

    // Сохранение клиента
    // http://localhost:8189/app/client/save-client
    @PostMapping("/save-client")
    public String saveClient(@ModelAttribute("client") Client client) {
        clientServiceImpl.save(client);
        return "redirect:/client/";
    }

    // Удаление клиента
    // http://localhost:8189/app/client/del-client/1
    @GetMapping("/del-client/{client_id}")
    public String delClient(@PathVariable(name = "client_id") Long client_id) {
        clientServiceImpl.delete(client_id);
        return "redirect:/client/";
    }

    // Форма редактирования клиента
    // http://localhost:8189/app/client/edit-client/1
    @GetMapping("/edit-client/{client_id}")
    public String editClient(Model model, @PathVariable(name = "client_id") Long client_id) {
        Optional<Client> editClient = Optional.of(new Client());
        editClient = clientServiceImpl.get(client_id);
        model.addAttribute("client", editClient);
        return templateFolder + "edit-client";
    }
}