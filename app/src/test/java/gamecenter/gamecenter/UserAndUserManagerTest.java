package gamecenter.gamecenter;

import org.junit.Test;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.*;
import static org.junit.Assert.*;

public class UserAndUserManagerTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private EnterActivity enterActivity = new EnterActivity();
    private User testUser;
    private UserManager userManager;

    @Before
    public void setUpUser(){
        testUser = new User("A", "123");
        userManager = new UserManager(enterActivity.getBaseContext());
        userManager.signUpUser("A","123");
    }

    @Test
    public void testGetUserName() {
        assertEquals("A", testUser.getUserName());
    }

    @Test
    public void testGetUserPassword() {
        assertEquals("123", testUser.getUserPassword());
    }

    @Test
    public void TestGet_USER_SAVED_GAME_FILE() {
        assertEquals("Asliding.ser", testUser.get_USER_SAVED_GAME_FILE("sliding_file"));
        assertEquals("Asnake.ser", testUser.get_USER_SAVED_GAME_FILE("snake_file"));
    }

    @Test
    public void testSetUserPassword() {
        testUser.setUserPassword("321");
        assertEquals("321", testUser.getUserPassword());
    }

    @Test
    public void testGetCurrentGameManager() {
        assertNull(testUser.getCurrentGameManager("currentSliding"));
        assertNull(testUser.getCurrentGameManager("currentSnake"));
    }

    @Test
    public void testSetHasSavedGame() {
        assertFalse(testUser.getHasSavedGame("hasSavedSliding"));
        assertFalse(testUser.getHasSavedGame("hasSavedSnake"));
        testUser.setHasSavedGame("hasSavedSliding", true);
        testUser.setHasSavedGame("hasSavedSnake", true);
        assertTrue(testUser.getHasSavedGame("hasSavedSliding"));
        assertTrue(testUser.getHasSavedGame("hasSavedSnake"));
    }

    @Test
    public void TestGetHasSavedGame() {
        testUser.setHasSavedGame("hasSavedSliding", true);
        testUser.setHasSavedGame("hasSavedSnake", true);
        assertTrue(testUser.getHasSavedGame("hasSavedSliding"));
        assertTrue(testUser.getHasSavedGame("hasSavedSnake"));
    }

    @Test
    public void testToString() {
        assertEquals("User: A     Password: 123", testUser.toString());
    }

    @Test
    public void testEquals() {
        User newUser = new User("A", "123");
        assertEquals(newUser, testUser);
    }



    //===== tests for UserManager ======

    @Test
    public void testSignUpUser() {
        assertEquals(1, userManager.signUpUser("B", "123"));
        assertEquals(0, userManager.signUpUser("B","123"));
    }

    @Test
    public void testSignUpUserInvalidTyping(){
        assertEquals(2, userManager.signUpUser("acsbjvbjvdksnvmsnvm,nbfjkvhbdfkjbv kjdnd d", "dkvmkjbjvvkjdbsd vkjhbejkqhvbkjasv ,s dj jka vba kv sjds"));
    }

    @Test
    public void getExistUserTest() {
        assertEquals("A", userManager.getUser("A").getUserName());
    }

    @Test
    public void getNonUserTest() {
        assertNull(userManager.getUser("C"));}

    @Test
    public void testUserLogInTest() {
        assertEquals(1, userManager.userLogIn("A", "123"));
        assertEquals(0, userManager.userLogIn("B", "123"));
    }

    @Test
    public void setCurrentUserTest() {
        userManager.signUpUser("C", "123");
        userManager.setCurrentUser(userManager.getUser("C"));
        assertEquals("C", userManager.getCurrentUser().getUserName());
    }



}