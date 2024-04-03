package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class FlexaryConjunctiveFormula extends BinaryFormula {

    public FlexaryConjunctiveFormula(Element element) {
        super(element, Connectives.FLEX_CONJUNCTION);
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
        //TODO spaces
        return new Representation(getFormula1().texRepr(representationCase)
                + LaTeX.bf(Texts.AND)
                + LaTeX.bf(LaTeX.ensureMath("\\dots"))
                + LaTeX.bf(Texts.AND)
                + getFormula2().texRepr(representationCase));
    }
}
