
package gamecenter.gamecenter;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import android.content.Context;

import gamecenter.gamecenter.MoleGame.MoleManager;
import gamecenter.gamecenter.SlidingGame.BoardManager;
import gamecenter.gamecenter.SnakeGame.SnakeManager;


public class UserManager implements Serializable {

    private final String USER_MANAGER_FILE = "userManager.ser";

    private HashMap<String, User> users;

    private User currentUser;

    private Context context;

    /**
     * Create a new UserManger
     *
     * @param context an activity (EnterActivity/ SignUpActivity) context
     */
    public UserManager(Context context) {
        users = new HashMap<>();
        this.context = context;
//        loadUsersOfUserManagerFromFile();
    }

    /**
     * Add User to UserManager once a user signed up.
     *
     * @param userName String representing the user name
     * @param password the user password
     * @return return 1 if user was added into users, 0 if user already exists
     */
    int signUpUser(String userName, String password) {
        //if the user did not exist before, and its username length and password length <= 30
        if (!users.containsKey(userName) && userName.length() <= 30 && password.length() <= 30) {
            User newUser = new User(userName, password);
            //create a new user, add it to the users(the hash map), and save users
            //to "userManager.ser"
            users.put(userName, newUser);
            return 1;
            // typing is not valid
        } else if (!users.containsKey(userName)) {
            return 2;
        }
        //user already exists
        return 0;
    }

    /**
     * Gets User.
     *
     * @param userName the User's name
     * @return the user
     */
    public User getUser(String userName) {
        if (users.keySet().contains(userName)) {
            return users.get(userName);
        }
        return null;
    }

    /**
     * Check whether a username has already existed
     *
     * @param name     a username
     * @param password a user password
     * @return 1 if a username exists and logged in, 0 otherwise
     */
    int userLogIn(String name, String password) {
        for (String userName : users.keySet()) {
            if (users.get(userName).getUserName().equals(name) && users.get(userName).getUserPassword().equals(password)) {
                return 1;
            }
        }
        return 0;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Get the user who are currently logging in
     *
     * @return the user who are currently logging in
     */
    public User getCurrentUser() {
        return this.currentUser;
    }


//===============================Save, Read, toString==========================================


    /**
     * Load the board manager from fileName.
     */
    public void loadUsersOfUserManagerFromFile() {

        try {
            InputStream inputStream = this.context.openFileInput(USER_MANAGER_FILE);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.users = (HashMap<String, User>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to the "".ser" fileName.
     */
    public void saveUsersOfUserManagerToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.context.openFileOutput(USER_MANAGER_FILE, Context.MODE_PRIVATE));
            outputStream.writeObject(users);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the saved board manager from fileName.
     */
    public void loadGameOfUserFromFile(User user, String gameName,  String gameManagerName) {
        try {
            InputStream inputStream = this.context.openFileInput(gameManagerName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                if (gameName.equals("sliding_file")) {
                    user.setCurrentGameManager("currentSliding", (BoardManager) input.readObject());
                } else if (gameName.equals("snake_file")) {
                    user.setCurrentGameManager("currentSnake", (SnakeManager) input.readObject());
                } else if (gameName.equals("mole_file")) {
                    user.setCurrentGameManager("currentMole", (MoleManager) input.readObject());
                }
                user.get_user_saved_games(gameName).remove(gameManagerName);
                saveUsersOfUserManagerToFile();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName. Return 1 if save success, otherwise return 0.
     */
    void saveGameOfUserToFile(User user, String gameName, String gameManagerName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.context.openFileOutput(gameManagerName, Context.MODE_PRIVATE));
            if (gameName.equals("sliding_file")) {
                if(!user.get_user_saved_games(gameName).contains(gameManagerName) && user.get_user_saved_games(gameName).size()<=5) {
                    user.get_user_saved_games(gameName).add(gameManagerName);
                    outputStream.writeObject(user.getCurrentGameManager("currentSliding"));
                }
            } else if (gameName.equals("snake_file")) {
                if(!user.get_user_saved_games(gameName).contains(gameManagerName) && user.get_user_saved_games(gameName).size()<=5) {
                    user.get_user_saved_games(gameName).add(gameManagerName);
                    outputStream.writeObject(user.getCurrentGameManager("currentSnake"));
                }
            } else if (gameName.equals("mole_file")) {
                if(!user.get_user_saved_games(gameName).contains(gameManagerName) && user.get_user_saved_games(gameName).size()<=5) {
                    user.get_user_saved_games(gameName).add(gameManagerName);
                    outputStream.writeObject(user.getCurrentGameManager("currentMole"));
                }
            }
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
