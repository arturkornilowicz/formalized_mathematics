package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class PredicateDefinition extends Item {

    private Redefine redefine;
    private PredicatePattern predicatePattern;
    private Definiens definiens;

    public PredicateDefinition(Element element) {
        super(element);
        redefine = new Redefine(element.element(ESXElementName.REDEFINE));
        predicatePattern = new PredicatePattern(element.element(ESXElementName.PREDICATE_PATTERN));
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
        predicatePattern.run();
        if (definiens != null) {
            definiens.run();
        }
    }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = Texts.S1 + predicatePattern.texRepr(RepresentationCase.GENERAL);
        if (definiens != null) {
            result += Texts.Tiff;
            List<Definiens> definienses = new LinkedList<>();
            definienses.add(getDefiniens());
            result += LaTeX.texDefiniensesString(definienses);
        }
        if (redefine.getElement().attributeValue(ESXAttributeName.OCCURS).equals("true")) {
            result += LaTeX.unfinished(this.getClass(),"REDEFINITION");
        }
        return new Representation(result);
    }
}
