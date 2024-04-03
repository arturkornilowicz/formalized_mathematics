package org.mizar.files;

import org.jbibtex.*;
import org.mizar.application.FormalizedMathematics;
import java.util.*;

public class Sections extends LinkedList<String> {

    public Sections() {
        int nr = 0;
        Value value = FormalizedMathematics.bibTeXEntry.getField(new Key("SECTION" + (++nr)));
        while (value != null) {
            add(value.toUserString());
            value = FormalizedMathematics.bibTeXEntry.getField(new Key("SECTION" + (++nr)));
        }
        System.out.println((nr-1) + " titles of sections loaded.");
    }
}
