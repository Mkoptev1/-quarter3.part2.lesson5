package com.geekbrains.controllers;

import com.geekbrains.entities.Client;
import com.geekbrains.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientsController {
    private ClientServiceImpl clientServiceImpl;

    @Autowired
    public void setClientServiceImpl(ClientServiceImpl _clientServiceImpl) {
        this.clientServiceImpl = _clientServiceImpl;
    }

    // Список клиентов
    // http://localhost:8189/app/clients/client-list
    @RequestMapping("/client-list")
    public String getClientList(Model model) {
        List<Client> clientList = clientServiceImpl.getAll();
        model.addAttribute("clients", clientList);
        return "client-list";
    }

    // Форма добавления клиента
    // http://localhost:8189/app/clients/add-client
    @RequestMapping("/add-client")
    public String newClient(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "add-client";
    }

    // Сохранение клиента
    // http://localhost:8189/app/clients/save-client
    @RequestMapping("/save-client")
    public String saveClient(@ModelAttribute("client") Client client) {
        clientServiceImpl.save(client);
        return "redirect:client-list";
    }

    // Форма удаления клиента
    // http://localhost:8189/app/clients/del-client-form
    @RequestMapping("/del-client-form")
    public String delClient() {
        return "del-client-form";
    }

    // Сохранение удаленного клиента
    // http://localhost:8189/app/clients/save-del-client
    @RequestMapping("/save-del-client")
    public String saveDelClient(@ModelAttribute("client") Client client) {
        //clientService.saveWare(client);
        return "redirect:client-list";
    }

    // Поиск клиента по товару
    // http://localhost:8189/app/clients/search-client-by-ware-form
    @RequestMapping("/search-client-by-ware-form")
    public String searchClientByWareForm() {
        return "search-client-by-ware-form";
    }

    // Результат поиска клиента по товару
    @RequestMapping(path="/search-client-by-ware-result", method=RequestMethod.GET)
    public String searchWareResult(@RequestParam("id") long clientId, Model model) {
        Optional<Client> client = Optional.of(new Client());
        client = clientServiceImpl.get(clientId);
        model.addAttribute("client", client);
        return "search-client-by-ware-result";
    }
/*
    // Отчет по заказам
    // http://localhost:8189/app/clients/rep-form-client-ware
    @RequestMapping("/rep-form-client-ware")
    public String geClientWareList(Model model) {
        List<ClientWareLink> clientWareLinkList = clientsOrderServiceImpl.getClientsOrderList();
        model.addAttribute("clientsOrder", clientWareLinkList);
        return "rep-form-client-ware";
    }
 */
}