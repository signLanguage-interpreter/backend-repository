package signLanguage.web.domain.common;

public enum Position {
    MANAGER("과장"),
    STAFF("사원"),
    SEINOR_STAFF("주임"),
    ASSISTANT_MANAGER("대리"),
    PRESIDENT("회장"),
    VICE_PRESIDENT("부회장"),
    DEPUTY_GENERAL_MANAGER("차장"),
    DIRECTOR("이사");

    private String value;

    Position(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
