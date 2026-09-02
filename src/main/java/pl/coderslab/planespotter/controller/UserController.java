package pl.coderslab.planespotter.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.planespotter.dto.request.UserRequest;
import pl.coderslab.planespotter.dto.response.UserResponse;
import pl.coderslab.planespotter.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userRequest));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllPlanes(){

        return ResponseEntity.ok(userService.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findPlane(@PathVariable Long id){

        return ResponseEntity.ok(userService.findById(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlane(@PathVariable Long id){

        userService.delete(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updatePlane(@PathVariable Long id,
                                                     @Valid @RequestBody UserRequest userRequest){

        return ResponseEntity.ok(userService.update(id, userRequest));

    }
}
