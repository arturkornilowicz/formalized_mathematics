package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class FunctorDefinition extends Item {

    private Redefine redefine;
    private FunctorPattern functorPattern;
    private TypeSpecification typeSpecification;
    private Definiens definiens;

    public FunctorDefinition(Element element) {
        super(element);
        redefine = new Redefine(element.element(ESXElementName.REDEFINE));
        functorPattern = FunctorPattern.buildFunctorPattern(element.elements().get(1));
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
        redefine.run();
        functorPattern.run();
        if (typeSpecification != null) {
            typeSpecification.run();
        }
        if (definiens != null) {
            definiens.run();
        }
    }

    @Override
    public void postProcess() { super.postProcess(); }

    private String defNonContradiction() {
        String result = "";
        if (definiens instanceof SimplePredicativeDefiniens) {
            SimplePredicativeDefiniens def = (SimplePredicativeDefiniens) definiens;
            if (def.getFormula() instanceof NegatedFormula) {
                NegatedFormula neg = (NegatedFormula) def.getFormula();
                if (neg.getFormula() instanceof Contradiction) {
                    result += functorPattern.texRepr(RepresentationCase.GENERAL) + " is the " + typeSpecification.texRepr(RepresentationCase.GENERAL);
                    return result;
                }
            }
        }
        return null;
    }

    private String texReprDefinition() {
        String result = Texts.S2 + functorPattern.texRepr(RepresentationCase.GENERAL);
        if (typeSpecification != null) {
            result += Texts.S3 + typeSpecification.texRepr(RepresentationCase.GENERAL);
        }
        //TODO means equals
        if (definiens != null) {
            result += Texts.S4;
            List<Definiens> definienses = new LinkedList<>();
            definienses.add(getDefiniens());
            result += LaTeX.texDefiniensesString(definienses);
        }
        return result;
    }

    private String texReprRedefinition() {
        if (typeSpecification != null && definiens == null) {
            return Texts.THE + functorPattern.texRepr(RepresentationCase.GENERAL) + Texts.IS + typeSpecification.texRepr(RepresentationCase.GENERAL) + ".";
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
        if (redefine.getElement().attributeValue(ESXAttributeName.OCCURS).equals("true")) {
            return new Representation(texReprRedefinition());
        } else {
            return new Representation(texReprDefinition());
        }
    }
}
