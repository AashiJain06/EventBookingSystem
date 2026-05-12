package in.aj.main.dto;


import lombok.Data;

@Data
public class AuthUserResponse {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;
}