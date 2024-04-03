package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class NotionName extends PragmaKind {

    public NotionName(Element element) {
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
    public Representation texRepr(Integer representationCase) {
        return new Representation("\\phantom{}\\newline{\\color{blue}" + getElement().attributeValue(ESXAttributeName.INSCRIPTION) + "}\\phantom{}\\newline");
    }
}
