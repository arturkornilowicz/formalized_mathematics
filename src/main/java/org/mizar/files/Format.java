package org.mizar.files;

import lombok.*;
import org.dom4j.*;
import org.mizar.application.TranslationApplication;

@AllArgsConstructor
@Getter
@ToString

public class Format {

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
        return this.element.attributeValue("kind").equals(that.getElement().attributeValue("kind"))
                && this.element.attributeValue("nr").equals(that.getElement().attributeValue("nr"));
    }

    public Symbol symbol() {
        // WARNING conflict L and G
        String kind = this.element.attributeValue("kind");
        if (kind.equals("J") || kind.equals("L")) {
            kind = "G";
        }
        for (Symbol symbol : TranslationApplication.symbols) {
            if (kind.equals(symbol.getElement().attributeValue("kind"))
                    && this.element.attributeValue("symbolnr").equals(symbol.getElement().attributeValue("nr"))
            ) {
                return symbol;
            }
        }
        return null;
    }

    public Symbol rightSymbol() {
        String kind = this.element.attributeValue("kind");
        if (kind.equals("K")) {
            kind = "L";
        }
        for (Symbol symbol : TranslationApplication.symbols) {
            if (kind.equals(symbol.getElement().attributeValue("kind"))
                    && this.element.attributeValue("rightsymbolnr").equals(symbol.getElement().attributeValue("nr"))
            ) {
                return symbol;
            }
        }
        return null;
    }

    public Integer argsNbr() {
        return Integer.parseInt(element.attributeValue("argnr"));
    }

    public Integer leftArgsNbr() {
        if (element.attributeValue("leftargnr") != null) {
            return Integer.parseInt(element.attributeValue("leftargnr"));
        } else {
            return 0;
        }
    }

    public Integer rightArgsNbr() {
        return argsNbr() - leftArgsNbr();
    }
}
