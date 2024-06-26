package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class PartialDefiniensList extends XMLElement {

    private List<PartialDefiniens> partialDefiniensList = new LinkedList<>();

    public PartialDefiniensList(Element element) {
        super(element);
        for (Element element1: element.elements(ESXElementName.PARTIAL_DEFINIENS)) {
            partialDefiniensList.add(PartialDefiniens.buildPartialDefiniens(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (PartialDefiniens partialDefiniens: partialDefiniensList) {
            partialDefiniens.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = "";
        int i;
        for (i = 0; i < partialDefiniensList.size()-1; i++) {
            result += "\n\\item" + partialDefiniensList.get(i).texRepr(representationCase) + ",";
        }
        result += "\n\\item" + partialDefiniensList.get(i).texRepr(representationCase) + ";";
        return new Representation(result);
    }
}
