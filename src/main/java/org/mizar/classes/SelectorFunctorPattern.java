package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class SelectorFunctorPattern extends Pattern {

    private Loci loci;

    private Representation selectorRepr;

    public SelectorFunctorPattern(Element element) {
        super(element);
        loci = new Loci(element.element(ESXElementName.LOCI));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        loci.run();
    }

    @Override
    public void postProcess() {
        selectorRepr = Translation.texRepr(this,loci,false);
        super.postProcess();
    }

    @Override
    public String kind() { return "U"; }
}
