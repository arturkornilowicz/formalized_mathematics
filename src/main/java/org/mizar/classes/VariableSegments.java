package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class VariableSegments extends XMLElement {

    private List<QualifiedSegment> segments = new LinkedList<>();

    private Integer varNbrVS = 0;

    public VariableSegments(Element element) {
        super(element);
        for (Element element1: element.elements()) {
            segments.add(QualifiedSegment.buildQualifiedSegment(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (QualifiedSegment segment: segments) {
            segment.run();
        }
    }

    @Override
    public void postProcess() {
        for (QualifiedSegment qualifiedSegment: segments) {
            varNbrVS += qualifiedSegment.getVarNbrSgm();
        }
        super.postProcess();
    }

    boolean plural() {
        return varNbrVS > 1;
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.texReprTextAnd(segments,representationCase));
    }
}
