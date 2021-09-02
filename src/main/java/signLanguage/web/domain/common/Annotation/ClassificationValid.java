package signLanguage.web.domain.common.Annotation;

import javax.validation.Payload;

public @interface ClassificationValid {
    String message() default "값이 유효하지 않아요. 거부할게요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends java.lang.Enum<?>> enumClass();
    boolean ignoreCase() default false;
}
