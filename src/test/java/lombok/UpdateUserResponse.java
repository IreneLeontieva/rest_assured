package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserResponse {

    private String name;
    private String job;
    private String createdAt;
}
