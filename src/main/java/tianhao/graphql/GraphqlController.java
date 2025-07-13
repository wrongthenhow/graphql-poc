package tianhao.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import tianhao.shared.UserService;
import tianhao.graphql.model.User;

@Controller
public class GraphqlController {

    private final UserService userService;

    public GraphqlController(UserService userService) {
        this.userService = userService;
    }

    @SchemaMapping(typeName = "Query", field = "user")
    public User getUser(@Argument Long id) {
        return userService.findById(id);
    }

    @SchemaMapping(typeName = "User", field = "username")
    public String getUsername(User user) {
        return user.username();
    }

    @SchemaMapping(typeName = "User", field = "email")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEmail(User user) {
        return user.email();
    }
}
