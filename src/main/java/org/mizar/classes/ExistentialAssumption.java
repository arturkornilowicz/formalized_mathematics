package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class ExistentialAssumption extends Item {

    private QualifiedSegments qualifiedSegments;
    private Conditions conditions;

    public ExistentialAssumption(Element element) {
        super(element);
        qualifiedSegments = new QualifiedSegments(element.element(ESXElementName.QUALIFIED_SEGMENTS));
        conditions = new Conditions(element.element(ESXElementName.CONDITIONS));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        qualifiedSegments.run();
        conditions.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = "";
        result += qualifiedSegments.plural() ? Texts.T8p : Texts.T8s;
        result += qualifiedSegments.texRepr(representationCase);
        result += Texts.SUCH_THAT;
        result += conditions.texRepr(representationCase);
        result += ". ";
        return new Representation(result);
    }
}
