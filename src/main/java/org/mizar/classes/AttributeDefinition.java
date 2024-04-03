package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class AttributeDefinition extends Item {

    private Redefine redefine;
    private AttributePattern attributePattern;
    private Definiens definiens;

    private Translation translation;

    public AttributeDefinition(Element element) {
        super(element);
        redefine = new Redefine(element.element(ESXElementName.REDEFINE));
        attributePattern = new AttributePattern(element.element(ESXElementName.ATTRIBUTE_PATTERN));
        if (element.element(ESXElementName.DEFINIENS) != null) {
            definiens = Definiens.buildDefiniens(element.element(ESXElementName.DEFINIENS));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
        translation = attributePattern.translation();
    }

    @Override
    public void process() {
        redefine.run();
        attributePattern.run();
        if (definiens != null) {
            definiens.run();
        }
    }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = "";
        result += Texts.S1 + attributePattern.getLocus().texRepr(representationCase) + LaTeX.has_sat(translation) + attributePattern.texRepr(representationCase);
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
