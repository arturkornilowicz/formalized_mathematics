package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class FraenkelTerm extends Term {

    private VariableSegments variableSegments;
    private Term term;
    private Formula formula;

    public FraenkelTerm(Element element) {
        super(element);
        variableSegments = new VariableSegments(element.element(ESXElementName.VARIABLE_SEGMENTS));
        term = Term.buildTerm(element.elements().get(1));
        formula = Formula.buildFormula(element.elements().get(2));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        variableSegments.run();
        term.run();
        formula.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        //TODO be -> is in where
        String result = "\\{";
        result += term.texRepr(representationCase);
        if (variableSegments.getSegments().size() > 0) {
            result += Texts.WHERE + variableSegments.texRepr(representationCase);
        }
        result += ": " + formula.texRepr(representationCase);
        result += "\\}";
        return new Representation(result);
    }
}
