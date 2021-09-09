package signLanguage.web.domain.common.basicDataDto;

import lombok.Data;

@Data
public class TwoData<T,K>{
    private T user;
    private K regi_list;

    public TwoData(T data1, K data2) {
        user = data1;
        regi_list = data2;
    }
}
