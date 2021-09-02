package signLanguage.web.domain.common;

public enum Classification {
    Medical("의료"),
    Law("법률"),
    Life("생활"),
    Education("교육");

    private String value;

    Classification(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
