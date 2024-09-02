package server.shareholders_app_backend.model;

import java.util.List;

public class CustomErrorResponse {
    private List<String> errorMessages;

    public CustomErrorResponse(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
