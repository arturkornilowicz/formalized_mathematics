package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class FlexaryDisjunctiveFormula extends BinaryFormula {

    public FlexaryDisjunctiveFormula(Element element) {
        super(element, Connectives.FLEX_DISJUNCTION);
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() { super.process(); }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(getFormula1().texRepr(representationCase)
                + LaTeX.bf(LaTeX.text(Texts.Tor))
                + LaTeX.bf(LaTeX.ensureMath("\\dots"))
                + LaTeX.bf(LaTeX.text(Texts.Tor))
                + getFormula2().texRepr(representationCase));
    }
}
