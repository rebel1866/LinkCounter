package by.isida.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
/* Объект данного класса содержит ссылку, найденную на ресурсе, указанном пользователем. Эта ссылка, в числе прочих, будет
отображена в таблице найденных ссылок */
public class Link {
    private String name;
    private String href;
}
