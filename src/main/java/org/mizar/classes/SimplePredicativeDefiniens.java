package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class SimplePredicativeDefiniens extends Definiens {

    private Label label;
    private Formula formula;

    public SimplePredicativeDefiniens(Element element) {
        super(element);
        label = new Label(element.element(ESXElementName.LABEL));
        formula = Formula.buildFormula(element.elements().get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        label.run();
        formula.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return formula.texRepr(representationCase);
    }

    @Override
    public void texDefiniens() {
        LaTeX.defDescriptionItem();
        LaTeX.addText(formula.texRepr(RepresentationCase.GENERAL).repr);
        LaTeX.addText(".");
    }

    @Override
    public String texDefiniensString() {
        String result = "";
        result += LaTeX.defDescriptionItemString();
        result += formula.texRepr(RepresentationCase.GENERAL);
        result += ".";
        return result;
    }
}
