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

### 2. Dynamic Query Cost Limits
```aiignore
tbd
```
- Idea is that Graphql can calculate amount of data extracted based on number of "leaf" nodes in response
- Limit rate based on this
- In comparison, for REST calls its difficult to narrow down how much to limit (many end points serving many data, vulnerable to scraping)

### 3. Auditable Access Patterns
```aiignore
tbd
```
- In REST its difficult to limit access to confidential data
- Fine-grained access to track "who saw what", without exposing the confidential data itself (through request schemas) 