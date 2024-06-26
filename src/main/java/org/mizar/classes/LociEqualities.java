package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class LociEqualities extends XMLElement {

    private List<LociEquality> lociEqualities = new LinkedList<>();

    public LociEqualities(Element element) {
        super(element);
        for (Element element1: element.elements(ESXElementName.LOCI_EQUALITY)) {
            lociEqualities.add(new LociEquality(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (LociEquality lociEquality: lociEqualities) {
            lociEquality.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.texReprTextAnd(lociEqualities,representationCase));
    }
}
