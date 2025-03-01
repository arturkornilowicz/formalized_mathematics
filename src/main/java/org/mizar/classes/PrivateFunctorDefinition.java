package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

@_Nonpublicable
public class PrivateFunctorDefinition extends Item {

    private Variable variable;
    private TypeList typeList;
    private Term term;

    public PrivateFunctorDefinition(Element element) {
        super(element);
        variable = new Variable(element.element(ESXElementName.VARIABLE));
        typeList = new TypeList(element.element(ESXElementName.TYPE_LIST));
        term = Term.buildTerm(element.elements().get(2));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        variable.run();
        typeList.run();
        term.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }
}
