package by.isida.controller;

import by.isida.model.Link;
import by.isida.controller.exception.LinkControllerException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class LinkController implements Serializable {
    @Serial
    private static final long serialVersionUID = 14965485415455485L;
    private String linkHref;
    private List<Link> links = new ArrayList<>();

    public void analyze() throws LinkControllerException {
        if (links.size() > 0) {
            links.clear();
        }
        normalizeLink();
        Document document;
        try {
            document = Jsoup.connect(linkHref).get();
        } catch (IOException | IllegalArgumentException e) {
            Logger logger = LogManager.getLogger(LinkController.class);
            String errorMessage = String.format("Невозможно подключиться по указанному URL-адресу: %s", linkHref);
            logger.error(errorMessage);
            throw new LinkControllerException(errorMessage);
        }
        fillLinkList(document, links);
    }

    public void clear() {
        links.clear();
    }

    public void updateHref(String newHref) {
        linkHref = newHref;
    }

    private void fillLinkList(Document document, List<Link> links) {
        Elements elements = document.getElementsByTag("a");
        for (Element linkElement : elements) {
            String linkHref = linkElement.attr("href");
            String linkName = linkElement.text();
            if (isValidLink(linkHref, linkName)) {
                links.add(new Link(linkName, linkHref));
            }
        }
    }

    private void normalizeLink() {
        if (!linkHref.startsWith("http")) {
            linkHref = "http://" + linkHref;
        }
    }

    private boolean isValidLink(String linkHref, String linkName) {
        return linkHref.startsWith("http") && !linkName.equals("");
    }
}
