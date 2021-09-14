package signLanguage.web.domain.dto;

import lombok.Data;

@Data
public class OrderManagerPagingDto {
    private Long start;
    private Long end;
    private int startPage;
    private int endPage;
    private int realEndPage;
    private Boolean startPageExist;
    private Boolean endPageExist;

    public OrderManagerPagingDto(Long start,
                                 Long end,
                                 int startPage,
                                 int endPage,
                                 int realEndPage,
                                 Boolean startPageExist,
                                 Boolean endPageExist) {
        this.start = start;
        this.end = end;
        this.startPage = startPage;
        this.endPage = endPage;
        this.realEndPage = realEndPage;
        this.startPageExist = startPageExist;
        this.endPageExist = endPageExist;
    }
}
