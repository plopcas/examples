package com.example.examplespringforms.model;

import java.util.UUID;

public class ModelD {

    public UUID id;

    public String textD;

    public ModelD() {
    }

    public ModelD(UUID id, String textD) {
        this.id = id;
        this.textD = textD;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTextD() {
        return textD;
    }

    public void setTextD(String textD) {
        this.textD = textD;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((textD == null) ? 0 : textD.hashCode());
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
        ModelD other = (ModelD) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (textD == null) {
            if (other.textD != null)
                return false;
        } else if (!textD.equals(other.textD))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ModelD [id=" + id + ", textD=" + textD + "]";
    }

   
}
