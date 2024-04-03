package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class PrivatePredicateFormula extends Formula {

    private Arguments arguments;

    public PrivatePredicateFormula(Element element) {
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
    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.ensureMath(LaTeX.mathcal(LaTeX.spelling(this)) + "[" + arguments.texRepr(RepresentationCase.ARGS_IN_PRIVATE) + "]"));
    }
}
