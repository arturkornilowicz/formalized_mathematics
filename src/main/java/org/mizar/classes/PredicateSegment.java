package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class PredicateSegment extends SchematicVariableSegment {

    private Variables variables;
    private TypeList typeList;

    public PredicateSegment(Element element) {
        super(element);
        variables = new Variables(element.element(ESXElementName.VARIABLES));
        typeList = new TypeList(element.element(ESXElementName.TYPE_LIST));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        variables.run();
        typeList.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    private String arity() {
        switch (typeList.getTypeList().size()) {
            case 0:
                return "a~nullary";
            case 1:
                return "a~unary";
            case 2:
                return "a~binary";
            case 3:
                return "a~ternary";
            case 4:
                return "a~quaternary";
            case 5:
                return "a~" + LaTeX.unfinished(getClass(),"arity missing");
            case 6:
                return "a~" + LaTeX.unfinished(getClass(),"arity missing");
            case 7:
                return "a~" + LaTeX.unfinished(getClass(),"arity missing");
            case 8:
                return "a~" + LaTeX.unfinished(getClass(),"arity missing");
            case 9:
                return "a~" + LaTeX.unfinished(getClass(),"arity missing");
            default:
                return "";
        }
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        //TODO plural
        return new Representation(arity() + " predicate " + LaTeX.texReprMathCal(variables.getVariables()));
    }
}
