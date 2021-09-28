package signLanguage.web.domain.common.converter;

import org.springframework.core.convert.converter.Converter;
import signLanguage.web.domain.common.OrderStatus;

public class StringToStatus implements Converter<String, OrderStatus> {

    @Override
    public OrderStatus convert(String source) {
        if(source.equals("hold")){
            return OrderStatus.HOLD;
        }
        if(source.equals("ready")){
            return OrderStatus.READY;
        }
        if(source.equals("end")){
            return OrderStatus.END;
        }else{
            throw new IllegalArgumentException();
        }
    }
}
