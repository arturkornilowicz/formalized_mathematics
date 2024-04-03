package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class ExistentialRegistration extends Cluster {

    private AdjectiveCluster adjectiveCluster;
    private Type type;

    public ExistentialRegistration(Element element) {
        super(element);
        adjectiveCluster = new AdjectiveCluster(element.element(ESXElementName.ADJECTIVE_CLUSTER));
        type = Type.buildType(element.elements().get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        adjectiveCluster.run();
        type.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    private String verb() {
        return "";
//        return switch (adjectiveCluster.getAdjectiveKind()) {
//            case AdjectiveKind.HAS -> " has ";
//            case AdjectiveKind.SAT -> " satisfies ";
//            default -> " is ";
//        };
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        //TODO plural
        String adjRepr = adjectiveCluster.texRepr(representationCase).repr;
        return switch (representationCase) {
            case RepresentationCase.MERGED_CLUSTERS ->
                    new Representation(Texts.S5 + type.texRepr(representationCase) + " which " + verb() + adjRepr);
            default ->
                    new Representation(Texts.T2 + Texts.S5 + type.texRepr(representationCase) + " which " + verb() + adjRepr + ".");
        };
    }
}
