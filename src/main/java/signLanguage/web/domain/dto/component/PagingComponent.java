package signLanguage.web.domain.dto.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PagingComponent {

//    @Value("${want.page}")
    private final static int paging = 20;
    private final static int pagingAll = 10;

    public Long getStart(Long curPage){
        return (paging*(curPage-1))+1;
    }

    public Long getEnd(Long curPage){
        return curPage * paging;
    }

    public int getEndPage(Long curPage){
        return (int) Math.ceil(curPage/pagingAll)*pagingAll;
    }

    public int getStartPage(Long curPage) {
        return getEndPage(curPage)-(pagingAll-1);
    }

    public int getRealEnd(Long allAmount) {
        return (int) Math.ceil(allAmount/paging);
    }

    public boolean getStartPageExist(Long curPage){
        return getStartPage(curPage)>1;
    }
    public boolean getEndPageExist(Long curPage, Long allAmount){
        return getEndPage(curPage)<getRealEnd(allAmount);
    }
}
