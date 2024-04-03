package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class PartialEquationalDefiniens extends PartialDefiniens {

    private Term term;
    private Formula guard;

    public PartialEquationalDefiniens(Element element) {
        super(element);
        term = Term.buildTerm(element.elements().get(0));
        guard = Formula.buildFormula(element.elements().get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        term.run();
        guard.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(term.texRepr(representationCase) + ", " + Texts.IF + guard.texRepr(representationCase));
    }
}
