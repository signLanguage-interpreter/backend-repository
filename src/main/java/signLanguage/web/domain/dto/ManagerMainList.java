package signLanguage.web.domain.dto;

import lombok.Data;

@Data
public class ManagerMainList<T,K> {
    private T paging;
    private K list;

    public ManagerMainList(T data1, K data2) {
        paging = data1;
        list = data2;
    }
}
