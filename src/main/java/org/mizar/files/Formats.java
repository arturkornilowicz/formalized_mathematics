package org.mizar.files;

import java.io.*;
import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.dom4j.io.*;

@Getter

public class Formats extends LinkedList<Format> {

    private String fileName;

    public Formats(String fileName) {
        this.fileName = fileName;
        loadFormats();
    }

    private void loadFormats() {
        try {
            System.out.println("Loading formats from " + fileName);
            SAXReader saxBuilder = new SAXReader();
            Document document = saxBuilder.read(new File(fileName));
            for (Element element: document.getRootElement().elements("Format")) {
                this.add(new Format(element));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public Format format(String nr) {
        for (Format format: this) {
            if (format.getElement().attributeValue("nr").equals(nr)) {
                return format;
            }
        }
        return null;
    }
}
