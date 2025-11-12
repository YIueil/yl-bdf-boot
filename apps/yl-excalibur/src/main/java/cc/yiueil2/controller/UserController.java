package cc.yiueil2.controller;

import cc.yiueil.controller.BaseController;
import cc.yiueil2.dto.UserDTO;
import cc.yiueil2.dto.UserRegDTO;
import cc.yiueil2.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "APP-用户")
@RestController
@RequestMapping(value = "/user")
public class UserController implements BaseController {
    @Autowired
    UserService userService;

    @PostMapping
    public String createUser(@RequestBody UserRegDTO userRegDTO) {
        UserDTO userDTO = userService.createUser(userRegDTO);
        return success(userDTO);
    }

    @GetMapping("/{guid}")
    public String getUserByGuid(@PathVariable String guid) {
        UserDTO userDTO = userService.getUserByGuid(guid);
        return success(userDTO);
    }

    @PutMapping("/{guid}")
    public String updateUser(@PathVariable String guid, @RequestBody UserDTO userDTO) {
        userService.updateUser(guid, userDTO);
        return success();
    }

    @DeleteMapping("/{guid}")
    public String deleteUser(@PathVariable String guid) {
        userService.deleteUser(guid);
        return success();
    }

    @PostMapping("/{guid}/checkin")
    public String checkInUser(@PathVariable String guid) {
        userService.incrSignDays(guid);
        return success();
    }
}
