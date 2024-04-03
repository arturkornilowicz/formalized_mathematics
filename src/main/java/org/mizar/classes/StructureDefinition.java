package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class StructureDefinition extends Item {

    private Ancestors ancestors;
    private StructurePattern structurePattern;
    private FieldSegments fieldSegments;
    private StructurePatternsRendering structurePatternsRendering;

    private List<String> internalSelectorTerms = new LinkedList<>();

    public StructureDefinition(Element element) {
        super(element);
        ancestors = new Ancestors(element.element(ESXElementName.ANCESTORS));
        structurePattern = new StructurePattern(element.element(ESXElementName.STRUCTURE_PATTERN));
        fieldSegments = new FieldSegments(element.element(ESXElementName.FIELD_SEGMENTS));
        structurePatternsRendering = new StructurePatternsRendering(element.element(ESXElementName.STRUCTURE_PATTERNS_RENDERING));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        ancestors.run();
        structurePattern.run();
        fieldSegments.run();
        structurePatternsRendering.run();
    }

    @Override
    public void postProcess() { super.postProcess(); }

    /* Finds Pattern for Internal-Selector-Term */
    private SelectorFunctorPattern selectorFunctorPattern(InternalSelectorTerm internalSelectorTerm) {
        for (SelectorFunctorPattern structurePatternsRendering: structurePatternsRendering.getSelectorsList().getSelectorFunctorPatterns()) {
            if (structurePatternsRendering.getElement().attributeValue(ESXAttributeName.FORMATNR)
                    .equals(internalSelectorTerm.getElement().attributeValue(ESXAttributeName.ORIGINNR))) {
                return structurePatternsRendering;
            }
        }
        return null;
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = "";
        result += Texts.T5;
        result += structurePattern.texRepr(representationCase);
        if (ancestors.getTypeList().size() > 0) {
            result += Texts.S9;
            result += ancestors.texRepr(representationCase);
        }
        result += Texts.S10;
        result += LaTeX.ensureMath("\\langle");
        result += structurePatternsRendering.getSelectorsList().texRepr(representationCase);
        result += LaTeX.ensureMath("\\rangle");
        result += Texts.WHERE;
        int selectorNr = 0;
        int i;
        for (i = 0; i < fieldSegments.getSegments().size() - 1; i++) {
            for (int s = 0; s < fieldSegments.getSegments().get(i).getSelectors().getSelectors().size() - 1; s++) {
                result += "the " + structurePatternsRendering.getSelectorsList().getSelectorFunctorPatterns().get(selectorNr++).getSelectorRepr() + ", ";
            }
            result += "the " + structurePatternsRendering.getSelectorsList().getSelectorFunctorPatterns().get(selectorNr++).getSelectorRepr();
            result += fieldSegments.getSegments().get(i).getSelectors().getSelectors().size() > 1 ? Texts.ARE : Texts.IS;
            result += fieldSegments.getSegments().get(i).getTypeRepr() + "; ";
        }
        for (int s = 0; s < fieldSegments.getSegments().get(i).getSelectors().getSelectors().size() - 1; s++) {
            result += "the " + structurePatternsRendering.getSelectorsList().getSelectorFunctorPatterns().get(selectorNr++).getSelectorRepr() + ", ";
        }
        result += "the " + structurePatternsRendering.getSelectorsList().getSelectorFunctorPatterns().get(selectorNr++).getSelectorRepr();
        result += fieldSegments.getSegments().get(i).getSelectors().getSelectors().size() > 1 ? Texts.ARE : Texts.IS;
        result += fieldSegments.getSegments().get(i).getTypeRepr() + ".";

        result += LaTeX.unfinished(getClass(), "a an, the, plural in intro");
        return new Representation(result);
    }
}
