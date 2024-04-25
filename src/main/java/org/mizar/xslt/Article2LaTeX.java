package org.mizar.xslt;

import org.mizar.application.XMLApplication;

import java.util.*;
import org.dom4j.*;

public class Article2LaTeX extends XMLApplication {

    int counter;

    Element template(Element element) {
        Element result = DocumentHelper.createElement("xsl:template");

        for (Attribute attribute: element.attributes()) {
            result.addAttribute(attribute.getName(),attribute.getValue());
        }

        List<Element> elements = element.elements();
        String templateName;
        for (int i = 0; i < elements.size(); i++) {
            result.add(elements.get(i).createCopy());
            if (elements.get(i).getName().equals("param") &&
                    i+1 < elements.size() && !elements.get(i+1).getName().equals("param")) {
                templateName = element.attributeValue("name") == null ? "" : element.attributeValue("name");
                templateName = element.attributeValue("match") == null ? "" : element.attributeValue("match");
                result.addElement("text")
                        .addText("\n\n\\\\" + (templateName.equals("") ? "WZORZEC" + (++counter) : templateName) + "\n\n");
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Article2LaTeX app = new Article2LaTeX();
        app.init("/Users/artur/article2latex_ak.xsl");
        Element root = DocumentHelper.createElement("xsl:stylesheet")
                .addAttribute("version","1.0")
                .addAttribute("xmlns:xsl","http://www.w3.org/1999/XSL/Transform");
        Document newDocument = DocumentHelper.createDocument(root);
        List<Element> templates = Article2LaTeX.document.getRootElement().elements();
        for (Element element: templates) {
            if (element.getName().equals("template")) {
                root.add(app.template(element));
            } else {
                root.add(element.createCopy());
            }
        }
        System.out.println(templates.size());
        System.out.println(newDocument.asXML());
    }
}
