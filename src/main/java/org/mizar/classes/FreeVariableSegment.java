package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class FreeVariableSegment extends QualifiedSegment {

    private Variable variable;
    private ReservedDscrType reservedDscrType;

    public FreeVariableSegment(Element element) {
        super(element);
        variable = new Variable(element.element(ESXElementName.VARIABLE));
        reservedDscrType = new ReservedDscrType((element.element(ESXElementName.RESERVEDDSCR_TYPE)));
    }

    @Override
    public void preProcess() {
        super.preProcess();
        setVarNbrSgm(0);
    }

    @Override
    public void process() {
        variable.run();
        reservedDscrType.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
//        return variable.texRepr() + " be " + reservedDscrType.texRepr();
        return new Representation("");
    }
}
