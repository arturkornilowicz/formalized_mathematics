package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class ConditionalFormula extends BinaryFormula {

    public ConditionalFormula(Element element) {
        super(element, Connectives.IMPLICATION);
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() { super.process(); }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(" if " + getFormula1().texRepr(representationCase) + ", then " + getFormula2().texRepr(representationCase));
    }
}
