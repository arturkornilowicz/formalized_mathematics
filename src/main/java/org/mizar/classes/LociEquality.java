package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class LociEquality extends XMLElement {

    private Locus locus1;
    private Locus locus2;

    public LociEquality(Element element) {
        super(element);
        locus1 = new Locus(element.elements(ESXElementName.LOCUS).get(0));
        locus2 = new Locus(element.elements(ESXElementName.LOCUS).get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        locus1.run();
        locus2.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(locus1.texRepr(representationCase) + " = " + locus2.texRepr(representationCase));
    }
}
