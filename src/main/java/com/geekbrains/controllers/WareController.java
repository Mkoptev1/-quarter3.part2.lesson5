package com.geekbrains.controllers;

import com.geekbrains.entities.Ware;
import com.geekbrains.services.WareServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ware")
public class WareController {
    private WareServiceImpl wareServiceImpl;
    private String templateFolder = "ware/";

    @Autowired
    public void setWareServiceImpl(WareServiceImpl wareServiceImpl) {
        this.wareServiceImpl = wareServiceImpl;
    }

    // Список товаров
    // http://localhost:8189/app/ware
    @GetMapping("/")
    public String getWareList(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber) {
        // Пагинация начинается с 0 элемента
        if (pageNumber > 0) {
            pageNumber --;
        }

        Pageable firstPageWithFiveElements = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.ASC,"ware_id"));
        Page<Ware> wareList = wareServiceImpl.showAllWare(firstPageWithFiveElements);
        model.addAttribute("ware", wareList);
        return templateFolder + "ware-list";
    }

    // Форма добавления товара
    // http://localhost:8189/app/ware/add-ware
    @GetMapping("/add-ware")
    public String newWare(Model model) {
        Ware ware = new Ware();
        model.addAttribute("ware", ware);
        return templateFolder + "add-ware";
    }

    // Сохранение товара
    // http://localhost:8189/app/ware/save-ware
    @PostMapping("/save-ware")
    public String saveWare(@ModelAttribute("ware") Ware ware) {
        wareServiceImpl.save(ware);
        return "redirect:/ware/";
    }

    // Удаление товара
    // http://localhost:8189/app/ware/del-ware/1
    @GetMapping("/del-ware/{ware_id}")
    public String delWare(@PathVariable(name = "ware_id") Long ware_id) {
        wareServiceImpl.delete(ware_id);
        return "redirect:/ware/";
    }

    // Форма редактирования товара
    // http://localhost:8189/app/ware/edit-ware/1
    @GetMapping("/edit-ware/{ware_id}")
    public String editWare(Model model, @PathVariable(name = "ware_id") Long ware_id) {
        Optional<Ware> editWare = Optional.of(new Ware());
        editWare = wareServiceImpl.get(ware_id);
        model.addAttribute("ware", editWare);
        return templateFolder + "edit-ware";
    }

    // Фильтрация товаров
    // http://localhost:8189/app/ware/ware-list/1
    @GetMapping(value="/ware-list/{filter}")
    public String filterWare(Model uiModel, @PathVariable(value="filter") Long filter_id) {
        List<Ware> wareList = wareServiceImpl.findMaxPrice(filter_id);
        uiModel.addAttribute("ware", wareList);
        return templateFolder + "ware-list";
    }

    // Поиск товара по клиенту
    // http://localhost:8189/app/ware/search-ware-by-client-form
    @GetMapping("/search-ware-by-client-form")
    public String searchWareByClientForm() {
        return "search-ware-by-client-form";
    }

    // Результат поиска товара по клиенту
    @GetMapping(path="/search-ware-by-client-result")
    public String searchWareResult(@RequestParam("id") long clientId, Model model) {
        Optional<Ware> ware = Optional.of(new Ware());
        ware = wareServiceImpl.get(clientId);
        model.addAttribute("ware", ware);
        return templateFolder + "search-ware-by-client-result";
    }
}