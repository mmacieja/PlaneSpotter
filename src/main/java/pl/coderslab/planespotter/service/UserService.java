package pl.coderslab.planespotter.service;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.planespotter.dto.request.UserRequest;
import pl.coderslab.planespotter.dto.response.UserResponse;
import pl.coderslab.planespotter.entity.User;
import pl.coderslab.planespotter.exception.DuplicateResourceException;
import pl.coderslab.planespotter.exception.ResourceNotFoundException;
import pl.coderslab.planespotter.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse toResponse(User user){

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setId(user.getId());

        return userResponse;
    }

    public UserResponse create(UserRequest userRequest){

        Map<String, List<String>> errors = new HashMap<>();

        if(userRepository.existsByEmail(userRequest.getEmail())){
            errors.put("email", List.of("A user with this email already exists"));

        }
        if(userRepository.existsByUsername(userRequest.getUsername())){
            errors.put("username", List.of("A user with this username already exists"));
        }

        if(!errors.isEmpty()){
            throw new DuplicateResourceException(errors);
        }

        User user = new User();

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return toResponse(userRepository.save(user));
    }

    public List<UserResponse> findAll() {

        return userRepository.findAll()
                .stream()
                .map( plane -> toResponse(plane))
                .toList();

    }

    public UserResponse findById(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return toResponse(user);
    }

    public void delete(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);

    }

    public UserResponse update(Long id, UserRequest userRequest){

        Map<String, List<String>> errors = new HashMap<>();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        if(userRepository.existsByEmailAndIdNot(userRequest.getEmail(), id)){
            errors.put("email", List.of("A user with this email already exists"));

        }
        if(userRepository.existsByUsernameAndIdNot(userRequest.getUsername(), id)){
            errors.put("username", List.of("A user with this username already exists"));
        }

        if(!errors.isEmpty()){
            throw new DuplicateResourceException(errors);
        }

        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return toResponse(userRepository.save(user));

    }

    public UserResponse getByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return toResponse(user);

    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
