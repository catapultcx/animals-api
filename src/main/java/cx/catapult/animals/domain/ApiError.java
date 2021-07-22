package cx.catapult.animals.domain;

public class ApiError {
    private String message;

    public ApiError() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
