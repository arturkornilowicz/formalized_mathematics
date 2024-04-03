package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class ModeDefinition extends Item {

    private Redefine redefine;
    private ModePattern modePattern;
    private ModePatternKind modePatternKind;

    public ModeDefinition(Element element) {
        super(element);
        redefine = new Redefine(element.element(ESXElementName.REDEFINE));
        modePattern = new ModePattern(element.element(ESXElementName.MODE_PATTERN));
        modePatternKind = ModePatternKind.buildModePatternKind(element.elements().get(2));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        redefine.run();
        modePattern.run();
        modePatternKind.run();
    }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = "";
        Representation patternRepr = modePattern.texRepr(representationCase);
        //TODO WARNING with comment
        String word = patternRepr.repr.substring("\\colorbox{green}{".length());
        String preposition = LaTeX.preposition(word);
        preposition = (""+preposition.charAt(0)).toUpperCase() + preposition.substring(1);
        result += "\n" + preposition + modePattern.texRepr(representationCase) + modePatternKind.texRepr(representationCase);
        if (redefine.getElement().attributeValue(ESXAttributeName.OCCURS).equals("true")) {
            result += LaTeX.unfinished(this.getClass(),"REDEFINITION");
        }
        return new Representation(result);
    }
}
