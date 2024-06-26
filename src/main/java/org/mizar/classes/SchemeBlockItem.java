package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class SchemeBlockItem extends Item {

    private Block block;

    public SchemeBlockItem(Element element) {
        super(element);
        block = Block.buildBlock(element.element(ESXElementName.BLOCK));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        block.run();
    }

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(block.getLatexOutput());
    }
}
