package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class BinaryFormula extends Formula {

    private Formula formula1;
    private Formula formula2;

    public BinaryFormula(Element element, Connective connective) {
        super(element,connective);
        formula1 = Formula.buildFormula(element.elements().get(0));
        formula2 = Formula.buildFormula(element.elements().get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        formula1.run();
        formula2.run();
//        String repr1 = formula1.texRepr();
//        String repr2 = formula2.texRepr();
//        if (this.getConnective().getPriority() > getFormula1().getConnective().getPriority()) {
//            LaTeX.addLine(LaTeX.ensureMath(Texts.LB4));
//            formula1.run();
//            LaTeX.addLine(LaTeX.ensureMath(Texts.RB4));
//        } else {
//            formula1.run();
//        }
//
//        LaTeX.addLine(this.getConnective().getRepr());
//
//        if (this.getConnective().getPriority() > getFormula2().getConnective().getPriority()) {
//            LaTeX.addLine(LaTeX.ensureMath(Texts.LB5));
//            formula2.run();
//            LaTeX.addLine(LaTeX.ensureMath(Texts.RB5));
//        } else {
//            formula2.run();
//        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }
}
