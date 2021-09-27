package signLanguage.web.domain.dto.component;

public class ManagerMainAllList<T,E,L> {
    private T page;
    private E order;
    private L manager;

    public ManagerMainAllList(T page, E order, L manager) {
        this.page = page;
        this.order = order;
        this.manager = manager;
    }
}
