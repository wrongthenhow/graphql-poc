package tianhao.shared;

import org.springframework.stereotype.Service;
import tianhao.graphql.model.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    public UserService() {
        users.put(1L, new User(1L, "alice", "alice@example.com"));
        users.put(2L, new User(2L, "bob", "bob@example.com"));
    }

    public User findById(Long id) {
        return users.get(id);
    }
}
