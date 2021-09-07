package signLanguage.web.domain.common.basicDataDto;

import lombok.Data;

@Data
public class TwoData<T,K>{
    private T Data1;
    private K Data2;

    public TwoData(T data1, K data2) {
        Data1 = data1;
        Data2 = data2;
    }
}
