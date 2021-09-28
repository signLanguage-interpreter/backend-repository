package signLanguage.web.domain.dto.component;

import lombok.Getter;

@Getter
public class ManagerMainAllList<T,E,L> {
    private T paging;
    private E list;
    private L manager;

    public ManagerMainAllList(T paging, E list, L manager) {
        this.paging = paging;
        this.list = list;
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "ManagerMainAllList{" +
                "paging=" + paging +
                ", list=" + list +
                ", manager=" + manager +
                '}';
    }
}
