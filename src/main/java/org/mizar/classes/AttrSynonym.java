package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class AttrSynonym extends Item {

    private AttributePattern attributePattern;
    private PatternShapedExpression patternShapedExpression;

    public AttrSynonym(Element element) {
        super(element);
        attributePattern = new AttributePattern(element.element(ESXElementName.ATTRIBUTE_PATTERN));
        patternShapedExpression = new PatternShapedExpression(element.element(ESXElementName.PATTERN_SHAPED_EXPRESSION));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        attributePattern.run();
        patternShapedExpression.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String subject = LaTeX.varRepr(LaTeX.spelling(attributePattern.getLocus()));
        return new Representation(Texts.T5
                + subject
                + Texts.IS
                + attributePattern.texRepr(representationCase)
                + Texts.T6s
                + subject
                + Texts.IS
                + patternShapedExpression.texRepr(representationCase) + ".");
    }
}
