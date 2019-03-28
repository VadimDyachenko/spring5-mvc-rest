package ua.vadym.spring5mvcrest.exceptions;

public class ApplicationException extends RuntimeException {

    private final ErrorDescription description;

    public ApplicationException(ErrorDescription description, Throwable e) {
        super(e);
        this.description = description;
    }

    public ApplicationException(ErrorDescription description) {
        super();
        this.description = description;
    }

    public ErrorDescription description(){
        return this.description;
    }
}
