package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long userId;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatar;
    private String accountType;
}
