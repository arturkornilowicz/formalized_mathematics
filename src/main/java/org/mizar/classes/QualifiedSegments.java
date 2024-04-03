package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class QualifiedSegments extends XMLElement {

    private List<QualifiedSegment> qualifiedSegments = new LinkedList<>();

    private Integer varNbrQS = 0;

    public QualifiedSegments(Element element) {
        super(element);
        for (Element element1: element.elements()) {
            qualifiedSegments.add(QualifiedSegment.buildQualifiedSegment(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (QualifiedSegment qualifiedSegment: qualifiedSegments) {
            qualifiedSegment.run();
        }
    }

    @Override
    public void postProcess() {
        for (QualifiedSegment qualifiedSegment: qualifiedSegments) {
            varNbrQS += qualifiedSegment.getVarNbrSgm();
        }
        super.postProcess();
    }

    boolean plural() {
        return varNbrQS > 1;
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.texReprTextAnd(qualifiedSegments,representationCase));
    }
}
