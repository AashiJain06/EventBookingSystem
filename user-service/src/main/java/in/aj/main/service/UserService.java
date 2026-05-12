package in.aj.main.service;





import org.springframework.data.domain.Page;

import in.aj.main.dto.AuthUserResponse;
import in.aj.main.dto.CreateUserRequest;
import in.aj.main.dto.UpdateUserRequest;
import in.aj.main.dto.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    Page<UserResponse> getAllUsers(int page, int size);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
    
    AuthUserResponse getUserByEmail(String email);
}
