package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class PrivatePredicateDefinition extends Item {

    private Variable variable;
    private TypeList typeList;
    private Formula formula;

    public PrivatePredicateDefinition(Element element) {
        super(element);
        variable = new Variable(element.element(ESXElementName.VARIABLE));
        typeList = new TypeList(element.element(ESXElementName.TYPE_LIST));
        formula = Formula.buildFormula(element.elements().get(2));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        variable.run();
        typeList.run();
        formula.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation("Let " + variable.texRepr(representationCase).repr
                + " over " + typeList.texRepr(representationCase)
                + " be the statement " + formula.texRepr(representationCase) + ". ");
    }
}
