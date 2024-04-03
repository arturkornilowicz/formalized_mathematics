package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class SimpleEquationalDefiniens extends Definiens {

    private Label label;
    private Term term;

    public SimpleEquationalDefiniens(Element element) {
        super(element);
        label = new Label(element.element(ESXElementName.LABEL));
        term = Term.buildTerm(element.elements().get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        label.run();
        term.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return term.texRepr(representationCase);
    }

    @Override
    public void texDefiniens() {
        LaTeX.defDescriptionItem();
        LaTeX.addText(term.texRepr(RepresentationCase.GENERAL).repr);
        LaTeX.addText(".");
    }

    @Override
    public String texDefiniensString() {
        String result = "";
        result += LaTeX.defDescriptionItemString();
        result += term.texRepr(RepresentationCase.GENERAL);
        result += ".";
        return result;
    }
}
