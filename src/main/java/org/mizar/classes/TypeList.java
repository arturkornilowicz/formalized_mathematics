package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class TypeList extends XMLElement {

    private List<Type> typeList = new LinkedList<>();

    public TypeList(Element element) {
        super(element);
        for (Element element1: element.elements()) {
            typeList.add(Type.buildType(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (Type type: typeList) {
            type.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.texReprText(typeList));
    }

}
