package com.example.examplespringforms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModelC {

    public UUID id;

    public String textC;

    public List<ModelD> modelDs = new ArrayList<>();

    public ModelC() {
    }

    public ModelC(UUID id, String textC, List<ModelD> modelDs) {
        this.id = id;
        this.textC = textC;
        this.modelDs = modelDs;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTextC() {
        return textC;
    }

    public void setTextC(String textC) {
        this.textC = textC;
    }

    public List<ModelD> getModelDs() {
        return modelDs;
    }

    public void setModelDs(List<ModelD> modelDs) {
        this.modelDs = modelDs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modelDs == null) ? 0 : modelDs.hashCode());
        result = prime * result + ((textC == null) ? 0 : textC.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ModelC other = (ModelC) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (modelDs == null) {
            if (other.modelDs != null)
                return false;
        } else if (!modelDs.equals(other.modelDs))
            return false;
        if (textC == null) {
            if (other.textC != null)
                return false;
        } else if (!textC.equals(other.textC))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ModelC [id=" + id + ", textC=" + textC + ", modelDs=" + modelDs + "]";
    }

}
