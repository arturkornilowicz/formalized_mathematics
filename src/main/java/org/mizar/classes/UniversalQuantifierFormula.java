package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class UniversalQuantifierFormula extends Formula {

    private VariableSegments variableSegments;
    private Restriction restriction;
    private Scope scope;

    public UniversalQuantifierFormula(Element element) {
        super(element);
        variableSegments = new VariableSegments(element.element(ESXElementName.VARIABLE_SEGMENTS));
        if (element.element(ESXElementName.RESTRICTION) != null) {
            restriction = new Restriction(element.element(ESXElementName.RESTRICTION));
        }
        scope = new Scope(element.element(ESXElementName.SCOPE));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        variableSegments.run();
        if (restriction != null) {
            restriction.run();
        }
        scope.run();
    }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        //TODO remove unnecessary holds
        String result = "";
        boolean forRequired = variableSegments.getVarNbrVS() > 0;
        result += forRequired ? Texts.FOR : "";
        result += variableSegments.texRepr(RepresentationCase.ADJECTIVES_WITH_TYPE);
        if (restriction != null) {
            result += Texts.SUCH_THAT + restriction.texRepr(representationCase);
        }
        result += forRequired ? Texts.HOLDS : "";
        result += scope.texRepr(representationCase);
        return new Representation(result);
    }
}
