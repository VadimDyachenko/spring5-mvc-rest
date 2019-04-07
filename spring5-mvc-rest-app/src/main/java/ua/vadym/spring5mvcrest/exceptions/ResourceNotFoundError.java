package ua.vadym.spring5mvcrest.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundError implements ErrorDescription {
    private final String description;

    public ResourceNotFoundError(String description) {
        this.description = description;
    }

    @Override
    public String errorKey() {
        return "resource_not_found_error";
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public Integer statusCode() {
        return NOT_FOUND.value();
    }
}
