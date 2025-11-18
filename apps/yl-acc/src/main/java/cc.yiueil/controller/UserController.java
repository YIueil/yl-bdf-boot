package cc.yiueil.controller;

import cc.yiueil.general.RestUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * UserController 用户控制器
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/8/1 22:56
 */
@Slf4j
@RestController
@RequestMapping(value = RestUrl.BASE_PATH + "/user")
public class UserController implements LoggedController {

    @Autowired
    DataSource dataSource;

    @GetMapping(value="test3")
    public String test3(HttpServletRequest request){
        log.debug(dataSource.toString());
        return dataSource.toString();
    }

}
