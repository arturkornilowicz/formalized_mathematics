package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;

@Setter
@Getter
@ToString

public class TextProper extends XMLElement {

    private List<Item> items = new LinkedList<>();

    public TextProper(Element element) {
        super(element);
        for (Element element1: element.elements(ElementNames.ITEM)) {
            items.add(Item.buildItem(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (Item item: items) {
            item.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }
}
