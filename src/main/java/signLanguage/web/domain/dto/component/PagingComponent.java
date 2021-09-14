package signLanguage.web.domain.dto.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
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


    public int getEndPage(Long curPage, Long allAmount){
        if(getRealEnd(allAmount) < (int)Math.ceil((double)curPage / (double)paging) * paging){
            return (getRealEnd(allAmount));
        }
        return (int)Math.ceil((double)curPage / (double)paging) * paging;
    }

    public int getStartPage(Long curPage, Long allAmount) {
        if(getEndPage(curPage, allAmount) < 1){
            return 1;
        }
        return getEndPage(curPage, allAmount);
    }

    public int getRealEnd(Long allAmount) {
        return (int) Math.ceil((double)allAmount / (double)paging);
    }

    public boolean getStartPageExist(Long curPage,Long allAmount){
        return getStartPage(curPage,allAmount)>1;
    }
    public boolean getEndPageExist(Long curPage, Long allAmount){
        return getEndPage(curPage,allAmount)<getRealEnd(allAmount);
    }
}
