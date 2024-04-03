package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class SchematicVariables extends XMLElement {

    private List<SchematicVariableSegment> segments = new LinkedList<>();

    public SchematicVariables(Element element) {
        super(element);
        for (Element element1: element.elements()) {
            segments.add(SchematicVariableSegment.buildSchematicVariableSegment(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (SchematicVariableSegment schematicVariableSegment: segments) {
            schematicVariableSegment.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.texReprTextAnd(segments,representationCase));
    }
}
