package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserResponse {

    private String name;
    private String job;
    private Integer id;
    private String createdAt;
}
