package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class Identify extends Item {

    private PatternShapedExpression leftPattern;
    private PatternShapedExpression rightPattern;
    private LociEqualities lociEqualities;

    public Identify(Element element) {
        super(element);
        leftPattern = new PatternShapedExpression(element.elements(ESXElementName.PATTERN_SHAPED_EXPRESSION).get(0));
        rightPattern = new PatternShapedExpression(element.elements(ESXElementName.PATTERN_SHAPED_EXPRESSION).get(1));
        lociEqualities = new LociEqualities(element.element(ESXElementName.LOCI_EQUALITIES));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        leftPattern.run();
        rightPattern.run();
        lociEqualities.run();
    }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = leftPattern.texRepr(representationCase) + Texts.T4 + rightPattern.texRepr(representationCase);
        if (lociEqualities.getLociEqualities().size() > 0) {
            result += " provided " + lociEqualities.texRepr(representationCase);
        }
        return new Representation(result);
    }
}
