package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.misc.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class Property extends Item {

    private Properties properties;
    private Justification justification;

    public Property(Element element) {
        super(element);
        properties = new Properties(element.element(ESXElementName.PROPERTIES));
        justification = Justification.buildJustification(element.elements().get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        properties.run();
        justification.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String notion;
        String[] notions = new String[]{"functor","predicate","type"};
        switch (properties.getElement().attributeValue(ESXAttributeName.PROPERTY)) {
            case "asymmetry":
                notion = notions[1];
                break;
            case "commutativity":
                notion = notions[0];
                break;
            case "connectedness":
                notion = notions[1];
                break;
            case "idempotence":
                notion = notions[0];
                break;
            case "involutiveness":
                notion = notions[0];
                break;
            case "irreflexivity":
                notion = notions[1];
                break;
            case "projectivity":
                notion = notions[0];
                break;
            case "reflexivity":
                notion = notions[1];
                break;
            case "sethood":
                notion = notions[2];
                break;
            case "symmetry":
                notion = notions[1];
                break;
            default:
                Errors.error(properties.getElement(), "Missing Element in Property texRepr[" + properties.getElement().attributeValue(ESXAttributeName.PROPERTY) + "]");
                return null;
        }
        return new Representation(properties.texRepr(representationCase).repr);
    }
}
