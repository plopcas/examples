package com.example.examplespringforms.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.examplespringforms.model.ModelA;
import com.example.examplespringforms.model.ModelB;
import com.example.examplespringforms.model.ModelC;
import com.example.examplespringforms.model.ModelD;
import com.example.examplespringforms.service.ModelAService;

@Controller
public class ModelAController {

    private ModelAService modelAService;

    public ModelAController(ModelAService modelAService) {
        this.modelAService = modelAService;
    }

    @GetMapping({ "/", "/model-a-s" })
    public String getListPage(final Model model) {

        List<ModelA> modelAs = modelAService.findAll();

        model.addAttribute("modelAs", modelAs);

        return "list";
    }

    @GetMapping("/model-a-s/{id}")
    public String getItemPage(@PathVariable final UUID id, final Model model) {

        ModelA modelA = modelAService.findById(id);

        model.addAttribute("modelA", modelA);

        return "item";
    }

    @GetMapping("/model-a-s/new")
    public String addNew(final Model model) {

        ModelA modelA = new ModelA();

        model.addAttribute("modelA", modelA);

        return "item";
    }

    @PostMapping("/model-a-s/update")
    public String update(@ModelAttribute ModelA modelA) {
        modelAService.update(modelA);
        return "redirect:/model-a-s";
    }

    @PostMapping("/model-a-s/create")
    public String create(@ModelAttribute ModelA modelA) {
        modelAService.create(modelA);
        return "redirect:/model-a-s";
    }

    @GetMapping("/model-a-s/{id}/delete")
    public String delete(@PathVariable final UUID id) {
        modelAService.delete(id);
        return "redirect:/model-a-s";
    }

    // These are meant to be called by AJAX so the return value is not used ///////

    @PostMapping("/model-a-s/model-b-s/new")
    public String addNewModelBToModelA(@ModelAttribute final ModelA modelA) {
        modelA.getModelBs().add(new ModelB());
        return "item";
    }

    @PostMapping("/model-a-s/model-b-s/delete")
    public String deleteModelBFromModelA(@RequestParam("index") final int index,
            @ModelAttribute final ModelA modelA) {
        modelA.getModelBs().remove(index);
        return "item";
    }

    @PostMapping("/model-a-s/model-c-s/new")
    public String addNewModelCToModelA(@ModelAttribute final ModelA modelA) {
        modelA.getModelCs().add(new ModelC());
        return "item";
    }

    @PostMapping("/model-a-s/model-c-s/delete")
    public String deleteModelCFromModelA(@RequestParam("index") final int index, @ModelAttribute final ModelA modelA) {
        modelA.getModelCs().remove(index);
        return "item";
    }

    @PostMapping("/model-a-s/model-d-s/new")
    public String addModelDToModelA(@RequestParam("index") final int index, @ModelAttribute final ModelA modelA) {
        modelA.getModelCs().get(index).getModelDs().add(new ModelD());
        return "item";
    }

    @PostMapping("/model-a-s/model-d-s/delete")
    public String deleteModelDFromModelA(@RequestParam("index") final int index,
            @RequestParam("index2") final int index2, @ModelAttribute final ModelA modelA) {
        modelA.getModelCs().get(index).getModelDs().remove(index2);
        return "item";
    }
}
