package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class FuncSynonym extends Item {

    private FunctorPattern functorPattern;
    private PatternShapedExpression patternShapedExpression;

    public FuncSynonym(Element element) {
        super(element);
        functorPattern = FunctorPattern.buildFunctorPattern(element.elements().get(0));
        patternShapedExpression = new PatternShapedExpression(element.element(ESXElementName.PATTERN_SHAPED_EXPRESSION));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        functorPattern.run();
        patternShapedExpression.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(Texts.T5 + functorPattern.texRepr(representationCase) + Texts.T6s + patternShapedExpression.texRepr(representationCase) + ".");
    }
}
