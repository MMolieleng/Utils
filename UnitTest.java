// Example class to deserialize
public class UserDto {
    private Long id;
    private String name;
    private String email;
    
    // Getters, setters, constructors
}

// In your test class
@Test
public void testSomething() {
    // Load a single object
    UserDto userMock = JsonTestUtils.loadObject("mocks/user.json", UserDto.class);
    
    // Load a list of objects
    List<UserDto> usersList = JsonTestUtils.loadList("mocks/users-list.json", UserDto.class);
    
    // Use the mocks in your test
    someService.processUser(userMock);
    // ...assertions
}
