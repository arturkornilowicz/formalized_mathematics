package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class ModePattern extends Pattern {

    private Loci loci;

    public ModePattern(Element element) {
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
        super.postProcess();
    }

    @Override
    public String kind() {
        return "M";
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return Translation.texReprMode(this,this.loci);
    }
}
