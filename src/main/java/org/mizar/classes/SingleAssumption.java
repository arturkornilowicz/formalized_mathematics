package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class SingleAssumption extends Assumption {

    private Proposition proposition;

    public SingleAssumption(Element element) {
        super(element);
        proposition = new Proposition(element.element(ESXElementName.PROPOSITION));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        proposition.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(Texts.S8 + proposition.texRepr(representationCase) + ".");
    }
}
