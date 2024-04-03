package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class NegatedFormula extends Formula {

    private Formula formula;

    public NegatedFormula(Element element) {
        super(element);
        formula = Formula.buildFormula(element.elements().get(0));
    }

    @Override
    public void preProcess() { super.preProcess(); }

    @Override
    public void process() {
        formula.run();
    }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(" not " + formula.texRepr(representationCase));
    }
}
