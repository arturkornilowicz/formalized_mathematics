package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class LociDeclaration extends Item {

    private QualifiedSegments qualifiedSegments;
    private Conditions conditions;

    public LociDeclaration(Element element) {
        super(element);
        qualifiedSegments = new QualifiedSegments(element.element(ESXElementName.QUALIFIED_SEGMENTS));
        if (element.element(ESXElementName.CONDITIONS) != null) {
            conditions = new Conditions(element.element(ESXElementName.CONDITIONS));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        qualifiedSegments.run();
        if (conditions != null) {
            conditions.run();
        }
    }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation("\nLet " + qualifiedSegments.texRepr(representationCase) + ".");
    }
}
