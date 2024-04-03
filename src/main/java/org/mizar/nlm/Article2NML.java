package org.mizar.nlm;

import org.dom4j.*;
import org.dom4j.io.*;
public class Article2NML {

    Document document = DocumentHelper.createDocument();
    String doi;

    Element abstract_ = DocumentHelper.createElement(NLMElements.ABSTRACT);
    Element addr_line = DocumentHelper.createElement(NLMElements.ADDR_LINE);
    Element aff = DocumentHelper.createElement(NLMElements.AFF);
    Element article = DocumentHelper.createElement(NLMElements.ARTICLE);
    Element article_categories = DocumentHelper.createElement(NLMElements.ARTICLE_CATEGORIES);
    Element article_id = DocumentHelper.createElement(NLMElements.ARTICLE_ID);
    Element article_meta = DocumentHelper.createElement(NLMElements.ARTICLE_META);
    Element article_title = DocumentHelper.createElement(NLMElements.ARTICLE_TITLE);
    Element back = DocumentHelper.createElement(NLMElements.BACK);
    Element bold = DocumentHelper.createElement(NLMElements.BOLD);
    Element city = DocumentHelper.createElement(NLMElements.CITY);
    Element contrib = DocumentHelper.createElement(NLMElements.CONTRIB);
    Element contrib_group = DocumentHelper.createElement(NLMElements.CONTRIB_GROUP);
    Element copyright_holder = DocumentHelper.createElement(NLMElements.COPYRIGHT_HOLDER);
    Element copyright_statement = DocumentHelper.createElement(NLMElements.COPYRIGHT_STATEMENT);
    Element copyright_year = DocumentHelper.createElement(NLMElements.COPYRIGHT_YEAR);
    Element country = DocumentHelper.createElement(NLMElements.COUNTRY);
    Element counts = DocumentHelper.createElement(NLMElements.COUNTS);
    Element date = DocumentHelper.createElement(NLMElements.DATE);
    Element day = DocumentHelper.createElement(NLMElements.DAY);
    Element fpage = DocumentHelper.createElement(NLMElements.FPAGE);
    Element front = DocumentHelper.createElement(NLMElements.FRONT);
    Element given_names = DocumentHelper.createElement(NLMElements.GIVEN_NAMES);
    Element history = DocumentHelper.createElement(NLMElements.HISTORY);
    Element institution = DocumentHelper.createElement(NLMElements.INSTITUTION);
    Element issn = DocumentHelper.createElement(NLMElements.ISSN);
    Element issue = DocumentHelper.createElement(NLMElements.ISSUE);
    Element italic = DocumentHelper.createElement(NLMElements.ITALIC);
    Element journal_id = DocumentHelper.createElement(NLMElements.JOURNAL_ID);
    Element journal_meta = DocumentHelper.createElement(NLMElements.JOURNAL_META);
    Element journal_title = DocumentHelper.createElement(NLMElements.JOURNAL_TITLE);
    Element journal_title_group = DocumentHelper.createElement(NLMElements.JOURNAL_TITLE_GROUP);
    Element kwd = DocumentHelper.createElement(NLMElements.KWD);
    Element kwd_group = DocumentHelper.createElement(NLMElements.KWD_GROUP);
    Element label = DocumentHelper.createElement(NLMElements.LABEL);
    Element license = DocumentHelper.createElement(NLMElements.LICENSE);
    Element license_p = DocumentHelper.createElement(NLMElements.LICENSE_P);
    Element lpage = DocumentHelper.createElement(NLMElements.LPAGE);
    Element mixed_citation = DocumentHelper.createElement(NLMElements.MIXED_CITATION);
    Element month = DocumentHelper.createElement(NLMElements.MONTH);
    Element name = DocumentHelper.createElement(NLMElements.NAME);
    Element p = DocumentHelper.createElement(NLMElements.P);
    Element page_count = DocumentHelper.createElement(NLMElements.PAGE_COUNT);
    Element permissions = DocumentHelper.createElement(NLMElements.PERMISSIONS);
    Element pub_date = DocumentHelper.createElement(NLMElements.PUB_DATE);
    Element publisher = DocumentHelper.createElement(NLMElements.PUBLISHER);
    Element publisher_name = DocumentHelper.createElement(NLMElements.PUBLISHER_NAME);
    Element ref = DocumentHelper.createElement(NLMElements.REF);
    Element ref_list = DocumentHelper.createElement(NLMElements.REF_LIST);
    Element related_article = DocumentHelper.createElement(NLMElements.RELATED_ARTICLE);
    Element sc = DocumentHelper.createElement(NLMElements.SC);
    Element sup = DocumentHelper.createElement(NLMElements.SUP);
    Element surname = DocumentHelper.createElement(NLMElements.SURNAME);
    Element title = DocumentHelper.createElement(NLMElements.TITLE);
    Element title_group = DocumentHelper.createElement(NLMElements.TITLE_GROUP);
    Element volume = DocumentHelper.createElement(NLMElements.VOLUME);
    Element xref = DocumentHelper.createElement(NLMElements.XREF);
    Element year = DocumentHelper.createElement(NLMElements.YEAR);

    private void createJournalMeta() {
        journal_meta.addElement(NLMElements.JOURNAL_ID)
                .addAttribute(NLMAttributes.JOURNAL_ID_TYPE,"publisher-id")
                .addText("forma");
        journal_meta.addElement(NLMElements.JOURNAL_ID)
                .addAttribute(NLMAttributes.JOURNAL_ID_TYPE,"journal-code")
                .addText("forma");
        journal_meta.addElement(NLMElements.JOURNAL_ID)
                .addAttribute(NLMAttributes.JOURNAL_ID_TYPE,"doi-code")
                .addText("forma");

        journal_title_group.add(journal_title);
        journal_title.addText("Formalized Mathematics");
        journal_meta.add(journal_title_group);

        issn.addAttribute(NLMAttributes.PUB_TYPE,"epub")
                .addText("1898-9934");
        journal_meta.add(issn);

        publisher_name.addText("Sciendo");
        publisher.add(publisher_name);
        journal_meta.add(publisher);
    }

    private void createContrib() {
        contrib.addAttribute(NLMAttributes.CONTRIB_TYPE,"author");
        Element name = DocumentHelper.createElement(NLMElements.NAME);
        name.addElement(NLMElements.SURNAME);
        name.addElement(NLMElements.GIVEN_NAMES);
        contrib.add(name);
        contrib.addElement(NLMElements.XREF)
                .addAttribute(NLMAttributes.REF_TYPE,"aff")
                .addAttribute(NLMAttributes.RID,"j_" + doi + "_aff_001")
                .addText("1");
    }

    private void createArticleMeta() {
        article_meta.addElement(NLMElements.ARTICLE_ID)
                .addAttribute(NLMAttributes.PUB_ID_TYPE,"publisher-id")
                .addText(doi);
        article_meta.addElement(NLMElements.ARTICLE_ID)
                .addAttribute(NLMAttributes.PUB_ID_TYPE,"doi")
                .addText("10.2478/" + doi);

        article_meta.add(article_categories);

        article_meta.addElement(NLMElements.TITLE_GROUP)
                .addElement(NLMElements.ARTICLE_TITLE)
                .addText("TITLE"); // TODO get from files

        createContrib();
        article_meta.add(contrib);

        //pub-date
        article_meta.add(pub_date);

        volume.addText("31");
        article_meta.add(volume);

        issue.addText("1");
        article_meta.add(issue);

        fpage.addText("67");
        article_meta.add(fpage);

        lpage.addText("73");
        article_meta.add(lpage);

        article_meta.add(history);

        article_meta.add(permissions);

        article_meta.add(related_article);

        article_meta.add(abstract_);

        // kwg-groups

        page_count.addAttribute(NLMAttributes.COUNT,"7");
        counts.add(page_count);
        article_meta.add(counts);

    }

    private void createFront() {
        article.addAttribute(NLMAttributes.ARTICLE_TYPE,"research-article")
                .addAttribute(NLMAttributes.DTD_VERSION,"1.1")
                .addAttribute(NLMAttributes.XML_LANG,"en")
                .addAttribute(NLMAttributes.XMLNS_XLINK,"http://www.w3.org/1999/xlink");

        createJournalMeta();
        front.add(journal_meta);

        createArticleMeta();
        front.add(article_meta);
    }

    private void createBack() {
        back.add(ref_list);
    }

    private void createNLM() {
        System.out.println("Generating NLM for " + doi + " ...");

        document.add(article);

        createFront();
        article.add(front);

        createBack();
        article.add(back);
    }

    private void printNLM() throws Exception {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(System.out, format);
        writer.write(document);
        writer.close();
    }

    public static void main(String[] args) throws Exception {

        Article2NML app = new Article2NML();
        app.doi = args[0];
        app.createNLM();
        app.printNLM();
    }
}
