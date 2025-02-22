package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.ESXElementName;

import java.util.ArrayList;
import java.util.List;

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
        if (formula instanceof BinaryFormula) {
            return new Representation(" not (" + formula.texRepr(representationCase) + ")");
        } else {
            return new Representation(" not " + formula.texRepr(representationCase));
        }
    }
}
