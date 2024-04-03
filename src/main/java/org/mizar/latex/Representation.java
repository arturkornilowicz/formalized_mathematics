package org.mizar.latex;

import java.util.*;

public class Representation {

    public String repr;
    public Translation translation;
    public boolean plural;
    public List<Representation> args = new LinkedList<>();

    public boolean openTerm;

    Representation() {
    }

    public Representation(String repr, Translation translation, boolean plural) {
        this.repr = repr;
        this.translation = translation;
        this.plural = plural;
        this.openTerm = isOpenTerm();
    }

    public Representation(String repr, Translation translation) {
        this(repr,translation,false);
    }

    public Representation(String repr) {
        this(repr,null);
    }

    @Override
    public String toString() {
        return repr;
    }

    private boolean isOpenTerm() {
        if (translation != null && translation.getKind().equals("O")) {
            return translation.getElement().attributeValue(TranslationNames.HEADER).substring(1, 2).equals("o");
        }
        return false;
    }

    public final static Representation attrStrict = new Representation("strict");

    // ( a + b ) * ( c + d ) = a * c + a * d + b * c + b * d
    public void test() {
        Representation a = new Representation("a");
        Representation b = new Representation("b");
        Representation c = new Representation("c");
        Representation d = new Representation("d");

        Representation a_p_b = new Representation("a+b");
        a_p_b.args.add(a);
        a_p_b.args.add(b);

        Representation c_p_d = new Representation("c+d");
        c_p_d.args.add(c);
        c_p_d.args.add(d);

        Representation L = new Representation("a+b*c+d");

        Representation a_r_c = new Representation("a*c");
        Representation a_r_d = new Representation("a*d");
        Representation b_r_c = new Representation("b*c");
        Representation b_r_d = new Representation("b*d");
    }

    public static void main(String[] args) {
        Representation app = new Representation();
        app.test();
    }
}
