package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class Arguments extends XMLElement {

    private List<Term> arguments = new LinkedList<>();

    public Arguments(Element element) {
        super(element);
        for (Element element1: element.elements()) {
            arguments.add(Term.buildTerm(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (Term term: arguments) {
            term.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        boolean oneArg = arguments.size() == 1;
        return switch (representationCase) {
            case RepresentationCase.ARGS_IN_PRIVATE -> new Representation(LaTeX.texReprText(arguments));
            default -> new Representation(arguments.size() != 0 ?
                    (oneArg ? "" : LaTeX.ensureMath(Texts.LB1)) + LaTeX.texReprText(arguments) + (oneArg ? "" : LaTeX.ensureMath(Texts.RB1)) : "");
        };
    }
}
