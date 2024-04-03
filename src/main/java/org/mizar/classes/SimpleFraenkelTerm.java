package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class SimpleFraenkelTerm extends Term {

    private VariableSegments variableSegments;
    private Term term;

    public SimpleFraenkelTerm(Element element) {
        super(element);
        variableSegments = new VariableSegments(element.element(ESXElementName.VARIABLE_SEGMENTS));
        term = Term.buildTerm(element.elements().get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        variableSegments.run();
        term.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        //TODO be -> is in where;  where -> ranges
        String result = LaTeX.ensureMath("\\{");
        result += term.texRepr(representationCase);
        if (variableSegments.getSegments().size() > 0) {
            result += LaTeX.text(Texts.WHERE + variableSegments.texRepr(representationCase));
        }
        result += LaTeX.ensureMath("\\}");
        return new Representation(result + "\n" + LaTeX.unfinished(getClass()));
    }
}
