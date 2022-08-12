package by.isida.controller;

import by.isida.model.InputLink;
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
import javax.faces.bean.ManagedProperty;
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

    @ManagedProperty(value = "#{inputLink}")
    private InputLink inputLink;
    private List<Link> links = new ArrayList<>();
    private static final String URL_START_SYMBOLS = "http";

    /* Метод выполняет анализ указанного пользователем ресурса - производится поиск ссылок. Найденные ссылки помещаются
    в ArrayList links. В случае, когда соединение с ресурсом невозможно, выбрасывается исключение, информация о котором
    впоследствии будет отображена на странице */
    public void analyze() throws LinkControllerException {
        if (links.size() > 0) {
            links.clear();
        }
        normalizeLink();
        Document document;
        String linkHref = inputLink.getInputURL();
        try {
            document = Jsoup.connect(linkHref).get();
        } catch (IOException | IllegalArgumentException e) {
            Logger logger = LogManager.getLogger(LinkController.class);
            String errorMessage = String.format("Невозможно подключиться к указанному URL-адресу: %s", linkHref);
            logger.error(errorMessage);
            throw new LinkControllerException(errorMessage);
        }
        fillLinkList(document, links);
    }

    // Метод выполнятся при нажатии кнопки Очистить
    public void clearLinks() {
        links.clear();
    }

    /* Вспомогательный метод. Извлекает ссылки, создает объекты Link, заполняет ими ArrayList links. Вызывается внутри
    метода analyze() */
    private void fillLinkList(Document document, List<Link> links) {
        Elements elements = document.getElementsByTag("a");
        for (Element linkElement : elements) {
            String linkHref = linkElement.attr("href");
            String linkName = linkElement.text();
            if (isValidLink(linkHref, linkName)) {
                Link link = new Link(linkName, linkHref);
                links.add(link);
            }
        }
    }

    /* Приводит ссылку к установленному образцу. Если в начале ссылки отсуствует указание протокола передачи данных
       (http:// или https://) - в таком случае метод присоединяет соответсвующее имя протокола */
    private void normalizeLink() {
        String linkHref = inputLink.getInputURL();
        if (!linkHref.startsWith(URL_START_SYMBOLS)) {
            inputLink.setInputURL("http://" + linkHref);
        }
    }

    // Проверяет валидность ссылки - ссылка не должна быть относительной, а ее имя не должно быть пустым.
    private boolean isValidLink(String linkHref, String linkName) {
        return linkHref.startsWith(URL_START_SYMBOLS) && !linkName.equals("");
    }
}
