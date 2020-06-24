package robtest.stateinterfw.openStack.cli.models;

import com.fasterxml.jackson.annotation.JsonGetter;

public class ExceptionResult {
    private int statusCode;
    private int subCode;
    private String message;
    private String action;

    @JsonGetter("status")
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @JsonGetter("sub_code")
    public int getSubCode() {
        return subCode;
    }

    public void setSubCode(int subCode) {
        this.subCode = subCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
