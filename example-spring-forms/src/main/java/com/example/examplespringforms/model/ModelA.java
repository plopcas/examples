package com.example.examplespringforms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModelA {

    public UUID id;

    public Boolean flagA;

    public String textA;

    public List<ModelB> modelBs = new ArrayList<>();

    public List<ModelC> modelCs = new ArrayList<>();

    public ModelA() {
    }

    public ModelA(UUID id, Boolean flagA, String textA, List<ModelB> modelBs, List<ModelC> modelCs) {
        super();
        this.id = id;
        this.flagA = flagA;
        this.textA = textA;
        this.modelBs = modelBs;
        this.modelCs = modelCs;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getFlagA() {
        return flagA;
    }

    public void setFlagA(Boolean flagA) {
        this.flagA = flagA;
    }

    public String getTextA() {
        return textA;
    }

    public void setTextA(String textA) {
        this.textA = textA;
    }

    public List<ModelB> getModelBs() {
        return modelBs;
    }

    public void setModelBs(List<ModelB> modelBs) {
        this.modelBs = modelBs;
    }

    public List<ModelC> getModelCs() {
        return modelCs;
    }

    public void setModelCs(List<ModelC> modelCs) {
        this.modelCs = modelCs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flagA == null) ? 0 : flagA.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modelBs == null) ? 0 : modelBs.hashCode());
        result = prime * result + ((modelCs == null) ? 0 : modelCs.hashCode());
        result = prime * result + ((textA == null) ? 0 : textA.hashCode());
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
        ModelA other = (ModelA) obj;
        if (flagA == null) {
            if (other.flagA != null)
                return false;
        } else if (!flagA.equals(other.flagA))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (modelBs == null) {
            if (other.modelBs != null)
                return false;
        } else if (!modelBs.equals(other.modelBs))
            return false;
        if (modelCs == null) {
            if (other.modelCs != null)
                return false;
        } else if (!modelCs.equals(other.modelCs))
            return false;
        if (textA == null) {
            if (other.textA != null)
                return false;
        } else if (!textA.equals(other.textA))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ModelA [id=" + id + ", flagA=" + flagA + ", textA=" + textA + ", modelBs=" + modelBs + ", modelCs="
                + modelCs + "]";
    }

}
