package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class CollectiveAssumption extends Assumption {

    private Conditions conditions;

    public CollectiveAssumption(Element element) {
        super(element);
        conditions = new Conditions(element.element(ESXElementName.CONDITIONS));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        conditions.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        //TODO better translation with conditions
        return new Representation(Texts.S8 + conditions.texRepr(representationCase) + ".");
    }
}
