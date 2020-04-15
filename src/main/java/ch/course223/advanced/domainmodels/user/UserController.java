package ch.course223.advanced.domainmodels.user;

import ch.course223.advanced.domainmodels.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_SEE')")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
    }

    @GetMapping({"", "/"})
    @PreAuthorize("hasAuthority('USER_SEE')")
    public @ResponseBody ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(userMapper.toDTOs(users), HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO user) {
        user = userMapper.toDTO(userService.save(userMapper.fromDTO(user)));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_MODIFY')")
    public ResponseEntity<UserDTO> updateById(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
        User user = userService.updateById(id, userMapper.fromDTO(userDTO));
        return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
