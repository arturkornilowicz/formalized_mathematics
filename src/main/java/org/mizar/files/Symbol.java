package org.mizar.files;

import lombok.*;
import org.dom4j.*;
import org.mizar.xml_names.ESXAttributeName;

@AllArgsConstructor
@Getter
@ToString

public class Symbol {

    private Element element;

    @Override
    public int hashCode() {
        return element.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Symbol)) {
            return false;
        }
        Symbol that = (Symbol) obj;
        return this.element.attributeValue("kind").equals(that.element.attributeValue("kind"))
                && this.element.attributeValue("nr").equals(that.element.attributeValue("nr"));
    }

    public String vocabularyName() {
        String[] tab = element.attributeValue(ESXAttributeName.ABSOLUTENR).split(":");
        return tab[0];
    }
}
