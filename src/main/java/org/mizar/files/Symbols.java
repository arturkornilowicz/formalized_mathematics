package org.mizar.files;

import java.io.*;
import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.dom4j.io.*;

@Getter

public class Symbols extends LinkedList<Symbol> {

    private String fileName;

    public Symbols(String fileName) {
        this.fileName = fileName;
        loadSymbols();
    }

    private void loadSymbols() {
        try {
            System.out.println("Loading symbols from " + fileName);
            SAXReader saxBuilder = new SAXReader();
            Document document = saxBuilder.read(new File(fileName));
            for (Element element: document.getRootElement().elements("Symbol")) {
                this.add(new Symbol(element));
            }
            for (Symbol symbol: extraSymbols()) {
                this.add(symbol);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private Element element(String kind, String nr, String name, String absolutenr) {
        return DocumentHelper.createElement("Symbol")
                .addAttribute("kind",kind)
                .addAttribute("nr",nr)
                .addAttribute("name",name)
                .addAttribute("absolutenr",absolutenr);
    }

    private List<Symbol> extraSymbols() {
        List<Symbol> result = new LinkedList<>();
        //TODO check
        result.add(new Symbol(element("R","1","=","HIDDEN:1")));
        result.add(new Symbol(element("M","1","set","HIDDEN:1")));
        result.add(new Symbol(element("K","1","{","HIDDEN:1")));
        result.add(new Symbol(element("L","1","}","HIDDEN:1")));
        result.add(new Symbol(element("K","2","[","HIDDEN:2")));
        result.add(new Symbol(element("L","2","]","HIDDEN:2")));
        result.add(new Symbol(element("V","1","strict","HIDDEN:1")));
        return result;
    }
}
