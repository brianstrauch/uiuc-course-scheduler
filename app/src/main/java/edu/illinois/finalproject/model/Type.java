package edu.illinois.finalproject.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Type {
    @Attribute
    String code;

    public String getCode() {
        return code;
    }
}

