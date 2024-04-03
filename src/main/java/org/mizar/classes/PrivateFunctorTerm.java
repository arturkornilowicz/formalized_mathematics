package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class PrivateFunctorTerm extends Term {

    private Arguments arguments;

    public PrivateFunctorTerm(Element element) {
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
        boolean bracketed = arguments.getArguments().size() > 0;
        return new Representation(LaTeX.ensureMath(LaTeX.mathcal(LaTeX.spelling(this))) + (bracketed ? Texts.LB4 : "") + arguments.texRepr(RepresentationCase.ARGS_IN_PRIVATE) + (bracketed ? Texts.RB4 : ""));
    }
}
