package signLanguage.web.domain.common.basicDataDto;

import lombok.Setter;

@lombok.Data
public class Data<T> {
    private T data;

    public Data(T t) {
        data = t;
    }
}
