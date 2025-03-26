package cc.yiueil.controller;

import cc.yiueil.dto.UserDTO;
import cc.yiueil.dto.UserRegDTO;
import cc.yiueil.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "APP-用户")
@RestController
public class UserController implements BaseController {
    @Autowired
    UserService userService;

    @PostMapping("user")
    public String createUser(@RequestBody UserRegDTO userRegDTO) {
        UserDTO userDTO = userService.createUser(userRegDTO);
        return success(userDTO);
    }

    @GetMapping("user/{guid}")
    public String getUserByGuid(@PathVariable String guid) {
        UserDTO userDTO = userService.getUserByGuid(guid);
        return success(userDTO);
    }

    @PutMapping("user/{guid}")
    public String updateUser(@PathVariable String guid, @RequestBody UserDTO userDTO) {
        userService.updateUser(guid, userDTO);
        return success();
    }

    @DeleteMapping("user/{guid}")
    public String deleteUser(@PathVariable String guid) {
        return success();
    }
}
