### 1. Graphql Allows for field-level authorizations, with little boilerplate code/dtos

For a type:
```graphql
type User {
    id: ID!
    username: String!
    email: String!
}
```

if we want to restrict `email` field for only `ADMIN` roles:

### REST:
```java
@GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id, Authentication auth) {
        User user = userService.findById(id);

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return ok(new PrivateUserDTO(user.id(), user.username(), user.email()));
        } else {
            return ok(new PublicUserDTO(user.id(), user.username()));
        }
    }
```
- Two DTOs `PrivateUserDTO` and `PublicUserDTO` were created to differentiate one field
- Data is still transmitted despite having lack of roles
- Complexity when dealing with many fields for different roles

#### GraphQL:
```java
@SchemaMapping(typeName = "User", field = "email")
@PreAuthorize("hasRole('ADMIN')")
public String getEmail(User user) {
        return user.email();
}
```
- Built-in field-level authorizations
- Scalable with increasing fields/roles
- Error thrown when user is not permitted, ensuring data is only transmitted on a "need-to-know" basis


