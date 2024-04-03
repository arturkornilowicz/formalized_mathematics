package org.mizar.latex;

import lombok.*;
import org.mizar.classes.*;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter

public class AdjectiveDescr {

    private String translation;
    private String translationING;

    private List<Attribute> attributes;

//    @Override
//    public String toString() {
//        return attributes.isEmpty() ? "" : LaTeX.texReprTextAnd(attributes) + "zzz";
//    }
}
