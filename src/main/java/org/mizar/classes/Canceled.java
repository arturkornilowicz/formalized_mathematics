package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class Canceled extends PragmaKind {

    public Canceled(Element element) {
        super(element);
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {}

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    //TODO maybe required
    public Representation texRepr(Integer representationCase) { return new Representation(""); }
}
