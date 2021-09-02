package signLanguage.web.domain.common;

import lombok.Setter;

@lombok.Data
public class Data<T> {
    private T data;

    public Data(T t) {
        data = t;
    }
}
