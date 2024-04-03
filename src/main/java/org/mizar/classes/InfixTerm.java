package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class InfixTerm extends Term {

    private Arguments arguments;

    public InfixTerm(Element element) {
        super(element);
        arguments = new Arguments(element.element(ESXElementName.ARGUMENTS));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        arguments.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public String kind() {
        return "O";
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return Translation.texReprInfix(this,this.arguments);
    }
}
