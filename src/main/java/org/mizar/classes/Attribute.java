package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class Attribute extends XMLElement {

    private Arguments arguments;

    private String preposition;

    private Translation translation;
    private String adjectiveKind;

    public Attribute(Element element) {
        super(element);
        arguments = new Arguments(element.element(ESXElementName.ARGUMENTS));
    }

    @Override
    public void preProcess() {
        super.preProcess();
        if (!getElement().attributeValue(ESXAttributeName.SPELLING).equals("strict")) {
            translation = translation();
            this.adjectiveKind = translation.getElement().attributeValue(TranslationNames.HEADER).substring(0, 1);
        } else {
            this.adjectiveKind = AdjectiveKind.A;
        }
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
        return "V";
    }

    public boolean negated() {
        return getElement().attribute(ESXAttributeName.NONOCC )!= null;
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = "";
        if (getElement().attributeValue(ESXAttributeName.SPELLING).equals("strict")) {
            if (getElement().attributeValue(ESXAttributeName.NONOCC) != null) {
                result += Texts.NON;
            }
            //TODO space before strict
            return new Representation(result + " " + Representation.attrStrict.repr);
        } else {
            String translation = Translation.texRepr(this, this.arguments).repr;
            if (getElement().attribute(ESXAttributeName.NONOCC) == null) {
                preposition = LaTeX.preposition(translation);
            } else {
                preposition = "a";
            }
            //TODO negation
//            if (getElement().attributeValue(ESXAttributeName.NONOCC) != null) {
//                result += Texts.NON;
//            }
            result += translation;
            return new Representation(result);
        }
    }
}
