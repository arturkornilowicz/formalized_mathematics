package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class ComplexDefiniens extends Definiens {

    private Label label;
    private PartialDefiniensList partialDefiniensList;
    private Otherwise otherwise;

    public ComplexDefiniens(Element element) {
        super(element);
        label = new Label(element.element(ESXElementName.LABEL));
        partialDefiniensList = new PartialDefiniensList(element.element(ESXElementName.PARTIAL_DEFINIENS_LIST));
        otherwise = Otherwise.buildOtherwise(element.element(ESXElementName.OTHERWISE));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        label.run();
        partialDefiniensList.run();
        otherwise.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.unfinished(getClass()));
    }

    @Override
    public void texDefiniens() {
        LaTeX.defDescriptionItem();
        LaTeX.unfinished(getClass(),"texDefiniens");
        LaTeX.addText(".");
    }

    @Override
    public String texDefiniensString() {
        String result = "";
        result += LaTeX.defDescriptionItemString();
        result += "\n\\begin{itemize}";
        result += partialDefiniensList.texRepr(RepresentationCase.GENERAL);
        result += otherwise.texRepr(RepresentationCase.GENERAL);
        result += "\n\\end{itemize}";
        result += LaTeX.unfinished(getClass(),"check ; and dots");
        result += ".";
        return result;
    }
}
