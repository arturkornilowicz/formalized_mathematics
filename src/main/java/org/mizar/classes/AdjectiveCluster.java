package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class AdjectiveCluster extends XMLElement {

    private List<Attribute> attributes = new LinkedList<>();

    private String adjectiveKind;

    public AdjectiveCluster(Element element) {
        super(element);
        for (Element element1: element.elements(ESXElementName.ATTRIBUTE)) {
            attributes.add(new Attribute(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (Attribute attribute: attributes) {
            attribute.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    public String addAdjectives(Collection<? extends XMLElement> col, Integer representationCase) {
        String string = "";
        Iterator<? extends XMLElement> it = col.iterator();
        while (it.hasNext()) {
            string += it.next().texRepr(representationCase).repr;
            if (it.hasNext()) {
                string += " ";
            }
        }
        return string;
    }

    @Override
    public Representation texRepr(Integer representationCase) {

        AdjectiveClusterRepresentation acr = new AdjectiveClusterRepresentation();

        String string = "";

        for (Attribute attribute : attributes) {
            //TODO add other cases
            if (attribute.negated()) {
                switch (attribute.getAdjectiveKind()) {
                    case AdjectiveKind.HAS:
                        acr.get(AdjectiveKind.HAS + AdjectiveClusterRepresentation.neg).getAttributes().add(attribute);
                        break;
                    case AdjectiveKind.SAT:
                        acr.get(AdjectiveKind.SAT + AdjectiveClusterRepresentation.neg).getAttributes().add(attribute);
                        break;
                    default:
                        acr.get(AdjectiveClusterRepresentation.regular + AdjectiveClusterRepresentation.neg).getAttributes().add(attribute);
                }
            } else {
                switch (attribute.getAdjectiveKind()) {
                    case AdjectiveKind.HAS:
                        acr.get(AdjectiveKind.HAS + AdjectiveClusterRepresentation.pos).getAttributes().add(attribute);
                        break;
                    case AdjectiveKind.SAT:
                        acr.get(AdjectiveKind.SAT + AdjectiveClusterRepresentation.pos).getAttributes().add(attribute);
                        break;
                    default:
                        acr.get(AdjectiveClusterRepresentation.regular + AdjectiveClusterRepresentation.pos).getAttributes().add(attribute);
                }
            }
        }

        for (String s: acr.keySet()) {
            if (!acr.get(s).getAttributes().isEmpty()) {
                adjectiveKind = s.substring(0,1);
                break;
            }
        }

        boolean addAndAlso = false;

        for (String s: acr.keySet()) {
            if (!acr.get(s).getAttributes().isEmpty()) {
                if (addAndAlso) {
                    string += " and also ".toUpperCase();
                }
                if (representationCase == RepresentationCase.ADJECTIVES_WITH_TYPE) {
                    string += acr.get(s).getTranslationING().toUpperCase() + " ";
                } else {
                    string += acr.get(s).getTranslation().toUpperCase() + " ";
                }
                string += LaTeX.texReprTextAnd(acr.get(s).getAttributes(),representationCase);
                addAndAlso = true;
            }
        }

        return new Representation(string);
    }

    @Deprecated
    public Representation texReprDepr(Integer representationCase) {

        String string = "";
        List<Attribute> has = new ArrayList<>();
        List<Attribute> sat = new ArrayList<>();
        List<Attribute> regular = new ArrayList<>();

        for (Attribute attribute: attributes) {
            switch (attribute.getAdjectiveKind()) {
                case AdjectiveKind.HAS: has.add(attribute);
                    break;
                case AdjectiveKind.SAT: sat.add(attribute);
                    break;
                default: regular.add(attribute);
            }
        }

        if (!regular.isEmpty()) {
            adjectiveKind = AdjectiveKind.EMPTY;
            string += addAdjectives(regular, representationCase);
            if (!has.isEmpty()) {
                string += " and has ";
                string += addAdjectives(has, representationCase);
                if (!sat.isEmpty()) {
                    string += " and satisfies ";
                    string += addAdjectives(sat, representationCase);
                }
            } else {
                if (!sat.isEmpty()) {
                    string += " and satisfies ";
                    string += addAdjectives(sat, representationCase);
                }
            }
        } else {
            if (!has.isEmpty()) {
                adjectiveKind = AdjectiveKind.HAS;
                string += addAdjectives(has, representationCase);
                if (!sat.isEmpty()) {
                    string += " and satisfies ";
                    string += addAdjectives(sat, representationCase);
                }
            } else {
                adjectiveKind = AdjectiveKind.SAT;
                string += addAdjectives(sat, representationCase);
            }
        }

        return new Representation(string);

        // TODO OLD
//        return new Representation(LaTeX.texReprTextAnd(attributes));
    }
}
