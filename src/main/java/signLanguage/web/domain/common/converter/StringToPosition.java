//package signLanguage.web.domain.common.converter;
//
//import org.springframework.core.convert.converter.Converter;
//import signLanguage.web.domain.common.OrderStatus;
//import signLanguage.web.domain.common.Position;
//
//public class StringToPosition implements Converter<String, Position> {
//
//
//    @Override
//    public Position convert(String source) {
//        if(source.equals("과장")){
//            return Position.MANAGER;
//        }
//        if(source.equals("사원")){
//            return Position.STAFF;
//        }
//        if(source.equals("주임")){
//            return Position.SEINOR_STAFF;
//        }
//        if(source.equals("대리")){
//            return Position.ASSISTANT_MANAGER;
//        }
//        if(source.equals("회장")){
//            return Position.PRESIDENT;
//        }
//        if(source.equals("부회장")){
//            return Position.VICE_PRESIDENT;
//        }
//        if(source.equals("차장")){
//            return Position.DEPUTY_GENERAL_MANAGER;
//
//        }
//        if(source.equals("이사")){
//            return Position.DIRECTOR;
//
//        }
//
//        else{
//            throw new IllegalArgumentException();
//        }
//    }
//}
