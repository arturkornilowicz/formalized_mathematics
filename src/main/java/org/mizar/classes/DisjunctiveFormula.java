package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class DisjunctiveFormula extends BinaryFormula {

    public DisjunctiveFormula(Element element) {
        super(element, Connectives.DISJUNCTION);
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
        return new Representation(getFormula1().texRepr(representationCase) + " or " + getFormula2().texRepr(representationCase));
    }
}
