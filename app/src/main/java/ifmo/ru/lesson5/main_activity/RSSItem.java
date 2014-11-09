package ifmo.ru.lesson5.main_activity;

/**
 * Created by mariashka on 10/19/14.
 */
public class RSSItem {
    private String title, text, url;

    RSSItem() {
        title = "";
        text = "";
        url = "";
    }

    RSSItem(String newTitle, String newText, String newURL) {
        title = newTitle;
        text = newText;
        url = newURL;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
    public void setTitle(String s) {
        title = s;
    }

    public void setText(String s) {
        text = s;
    }

    public void setUrl(String s) {
        url = s;
    }
}
