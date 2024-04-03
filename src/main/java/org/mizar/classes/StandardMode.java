package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class StandardMode extends ModePatternKind {

    private TypeSpecification typeSpecification;
    private Definiens definiens;

    public StandardMode(Element element) {
        super(element);
        if (element.element(ESXElementName.TYPE_SPECIFICATION) != null) {
            typeSpecification = new TypeSpecification(element.element(ESXElementName.TYPE_SPECIFICATION));
        }
        if (element.element(ESXElementName.DEFINIENS) != null) {
            definiens = Definiens.buildDefiniens(element.element(ESXElementName.DEFINIENS));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        if (typeSpecification != null) {
            typeSpecification.run();
        }
        if (definiens != null) {
            definiens.run();
        }
    }

    @Override
    public void postProcess() { super.postProcess(); }

    private String texReprDefinition() {
        String result = "";
        if (typeSpecification != null) {
            result += Texts.IS + typeSpecification.texRepr(RepresentationCase.GENERAL);
        }
        if (definiens != null) {
            List<Definiens> definienses = new LinkedList<>();
            result += typeSpecification != null ? Texts.S4a : Texts.S4;
            definienses.add(getDefiniens());
            result += LaTeX.texDefiniensesString(definienses);
        }
        return result;
    }

    private String texReprRedefinition() {
        //TODO just copied
        if (typeSpecification != null && definiens == null) {
            return Texts.IS + typeSpecification.texRepr(RepresentationCase.GENERAL) + ".";
        }
        if (typeSpecification == null && definiens != null) {
            return LaTeX.unfinished(getClass(),"Not recognized redefinition of TYPE.");
        }
        if (typeSpecification != null && definiens != null) {
            return LaTeX.unfinished(getClass(),"Not recognized redefinition of TYPE and DEFINIENS.");
        }
        return LaTeX.unfinished(getClass(),"Not recognized redefinition.");
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        if (getElement().getParent().element(ESXElementName.REDEFINE).attributeValue(ESXAttributeName.OCCURS).equals("true")) {
            return new Representation(texReprRedefinition());
        } else {
            return new Representation(texReprDefinition());
        }
    }
}
