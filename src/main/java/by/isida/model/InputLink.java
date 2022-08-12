package by.isida.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serial;
import java.io.Serializable;

@ManagedBean
@SessionScoped
@Getter
@Setter
@EqualsAndHashCode
@ToString
//Объект данного класса содержит url-адрес анализируемого ресурса (вводится пользователем)
public class InputLink implements Serializable {
    @Serial
    private static final long serialVersionUID = 2111485415455423L;
    private String inputURL;
}
