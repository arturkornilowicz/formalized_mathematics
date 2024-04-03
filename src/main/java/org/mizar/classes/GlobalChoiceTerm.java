package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class GlobalChoiceTerm extends Term {

    private Type type;

    public GlobalChoiceTerm(Element element) {
        super(element);
        type = Type.buildType(element.elements().get(0));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        type.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(" the " + type.texRepr(representationCase));
    }
}
