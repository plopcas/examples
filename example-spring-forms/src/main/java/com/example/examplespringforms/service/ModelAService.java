package com.example.examplespringforms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.examplespringforms.model.ModelA;
import com.example.examplespringforms.model.ModelB;
import com.example.examplespringforms.model.ModelC;
import com.example.examplespringforms.model.ModelD;

@Service
public class ModelAService {

    Map<UUID, ModelA> modelAs;

    public ModelAService() {

        List<ModelD> modelDs1 = new ArrayList<>();
        modelDs1.add(new ModelD(UUID.randomUUID(), "FooD"));
        modelDs1.add(new ModelD(UUID.randomUUID(), "BarD"));

        List<ModelC> modelCs1 = new ArrayList<>();
        modelCs1.add(new ModelC(UUID.randomUUID(), "FooC", modelDs1));
        modelCs1.add(new ModelC(UUID.randomUUID(), "BarC", new ArrayList<>()));

        List<ModelB> modelBs1 = new ArrayList<>();
        modelBs1.add(new ModelB(UUID.randomUUID(), "FooB"));
        modelBs1.add(new ModelB(UUID.randomUUID(), "BarB"));

        modelAs = new HashMap<>();
        UUID id = UUID.randomUUID();
        modelAs.put(id, new ModelA(id, true, "FooA", modelBs1, modelCs1));
    }

    public List<ModelA> findAll() {
        return new ArrayList<>(modelAs.values());
    }

    public ModelA findById(UUID id) {
        return modelAs.get(id);
    }

    public void update(ModelA modelA) {
        modelAs.put(modelA.getId(), modelA);
    }

    public UUID create(ModelA modelA) {
        modelA.setId(UUID.randomUUID());
        modelAs.put(modelA.getId(), modelA);
        return modelA.getId();
    }

    public void delete(UUID id) {
        modelAs.remove(id);
    }

}
