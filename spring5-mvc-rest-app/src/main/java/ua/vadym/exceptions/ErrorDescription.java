package ua.vadym.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"errorKey", "description"})
public interface ErrorDescription {

    @JsonProperty
    String errorKey();

    @JsonProperty
    String description();

    Integer statusCode();
}
