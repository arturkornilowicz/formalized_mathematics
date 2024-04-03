package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class StructType extends Type {

    private Arguments arguments;

    public StructType(Element element) {
        super(element);
        arguments = new Arguments(element.element(ESXElementName.ARGUMENTS));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        arguments.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public String kind() {
        return "L";
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return Translation.texReprType(this,this.arguments);
    }
}
