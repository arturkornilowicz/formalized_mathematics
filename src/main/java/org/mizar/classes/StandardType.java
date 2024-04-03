package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class StandardType extends Type {

    private Arguments arguments;

    public StandardType(Element element) {
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
        return "M";
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        Representation result = Translation.texReprType(this,this.arguments);
        String preposition = "";
        if (!getElement().getParent().getName().equals(ESXElementName.CLUSTERED_TYPE)) {
            if (!result.plural) {
                preposition = LaTeX.preposition(result.repr);
            }
        }
        return new Representation(preposition + result.repr, result.translation, result.plural);
    }
}
