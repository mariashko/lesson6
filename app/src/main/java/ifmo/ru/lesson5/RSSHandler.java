package ifmo.ru.lesson5;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RSSHandler extends DefaultHandler {

    private List<RSSItem> rssItems;
    private RSSItem currentItem;
    private StringBuilder content;
    private boolean inItem = false;

    public RSSHandler() {
        rssItems = new ArrayList();
    }

    public List<RSSItem> getItems() {
        return rssItems;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        content = new StringBuilder();
        if (localName.equalsIgnoreCase("item")) {
            inItem = true;
            currentItem = new RSSItem();
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equalsIgnoreCase("title")) {
            if(inItem) {
                currentItem.setTitle(content.toString());
            }
        } else if(localName.equalsIgnoreCase("link")) {
            if(inItem) {
                currentItem.setUrl(content.toString());
            }
        } else if(localName.equalsIgnoreCase("description")) {
            if(inItem) {
                currentItem.setText(content.toString());
            }
        } else if(localName.equalsIgnoreCase("item")) {
            inItem = false;
            rssItems.add(currentItem);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        content.append(ch, start, length);
    }
}
