package by.isida.service;

import by.isida.model.Link;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LinkService implements Serializable {
    @Serial
    private static final long serialVersionUID = 14965485415455485L;
    private String linkHref;
    private List<Link> links = new ArrayList<>();

    public List<Link> analyze() {
        if (links.size() > 0) {
            links.clear();
        }
        Document document = new Document("");
        try {
            document = Jsoup.connect(linkHref).get();
        } catch (IOException e) {
            Logger logger = LogManager.getLogger(LinkService.class);
            logger.error("Can not connect to given url");
        }
        Elements elements = document.getElementsByTag("a");
        for (Element linkElement : elements) {
            String linkHref = linkElement.attr("href");
            String linkName = linkElement.text();
            if (isValidLink(linkHref, linkName)) {
                links.add(new Link(linkName, linkHref));
            }
        }
        return links;
    }

    public void clear() {
        links.clear();
    }

    public void update(String newHref) {
        linkHref = newHref;
    }

    private boolean isValidLink(String linkHref, String linkName) {
        return linkHref.startsWith("http") && !linkName.equals("");
    }
}
