package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class ConjunctiveFormula extends BinaryFormula {

    public ConjunctiveFormula(Element element) {
        super(element, Connectives.CONJUNCTION);
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() { super.process(); }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(getFormula1().texRepr(representationCase) + " and " + getFormula2().texRepr(representationCase));
    }
}
