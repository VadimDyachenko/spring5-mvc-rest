package ua.vadym.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {
    private String firstname;
    private String lastname;
    @JsonProperty("customer_url")
    private String url;
}
