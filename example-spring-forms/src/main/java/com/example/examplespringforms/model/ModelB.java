package com.example.examplespringforms.model;

import java.util.UUID;

public class ModelB {

    public UUID id;

    public String textB;

    public ModelB() {
    }

    public ModelB(UUID id, String textB) {
        this.id = id;
        this.textB = textB;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTextB() {
        return textB;
    }

    public void setTextB(String textB) {
        this.textB = textB;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((textB == null) ? 0 : textB.hashCode());
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
        ModelB other = (ModelB) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (textB == null) {
            if (other.textB != null)
                return false;
        } else if (!textB.equals(other.textB))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ModelB [id=" + id + ", textB=" + textB + "]";
    }


}
