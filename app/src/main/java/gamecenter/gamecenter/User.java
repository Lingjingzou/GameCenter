//
//package gamecenter.gamecenter;
//
//import java.io.Serializable;
//import java.util.HashMap;
//
///**
// * The User class
// */
//public class User implements Serializable {
//
//    private String userName;
//    private String userPassword;
//    private HashMap<String, GameManager> userCurrentGameManagers = new HashMap<>();
//    private HashMap<String, Boolean> hasSavedGames = new HashMap<>();
//    private ScoreBoardManager scoreBoardManager;
//    private HashMap<String, String> gameSavedFiles = new HashMap<>();
//
//    /**
//     * Constructor for User
//     *
//     * @param name     the user name of the user
//     * @param password the password for the user
//     */
//    public User(String name, String password){
//        this.userName = name;
//        this.userPassword = password;
//       // this.scoreBoardManager= new ScoreBoardManager();
//
//        userCurrentGameManagers.put("currentSliding", null);
//        userCurrentGameManagers.put("currentSnake", null);
//        userCurrentGameManagers.put("currentMole", null);
//
//        hasSavedGames.put("hasSavedSliding", false);
//        hasSavedGames.put("hasSavedSnake", false);
//        hasSavedGames.put("hasSavedMole", false);
//
//        gameSavedFiles.put("sliding_file",name + "sliding.ser");
//        gameSavedFiles.put("snake_file", name + "snake.ser");
//        gameSavedFiles.put("mole_file", name + "mole.ser");
//    }
//
//    /**
//     * Return a user's username
//     *
//     * @return username
//     */
//    public String getUserName(){return this.userName;}
//
//    /**
//     * Return a user's password
//     *
//     * @return password of its associated username
//     */
//    String getUserPassword(){return this.userPassword;}
//
//    String get_USER_SAVED_GAME_FILE(String gameName){
//        return gameSavedFiles.get(gameName);
//    }
//
//    void setUserPassword(String newPassword){this.userPassword = newPassword;}
//
//    /**
//     * Get the GameManger object the user select
//     *
//     * @return the GameManger object which a user wants to use
//     */
//    public GameManager getCurrentGameManager(String gameManagerName){
//        return userCurrentGameManagers.get(gameManagerName);
//    }
//
//    public void setHasSavedGame(String gameName, boolean hasSavedGame) {
//        hasSavedGames.put(gameName, hasSavedGame);
//    }
//
//    public boolean getHasSavedGame(String gameName){
//        return hasSavedGames.get(gameName);
//    }
//
//    public ScoreBoardManager getScoreBoardManager(){return scoreBoardManager;}
//
//    /**
//     * Set a user's relating BoardManger object to the one they want to use
//     *
//     * @param currentGameManager the BoardManger object a user choose to get game data from
//     */
//    public void setCurrentGameManager(String gameName, GameManager currentGameManager){
//        userCurrentGameManagers.put(gameName, currentGameManager);
//    }
//
//    @Override
//    public String toString(){return "User: " + this.userName + "     Password: " + this.userPassword;}
//
//    @Override
//    public boolean equals(Object other){return other instanceof User
//            && (this.userName.equals(((User) other).getUserName()))
//            && (this.userPassword.equals(((User) other).getUserPassword()));
//    }
//
//}
//=======
package gamecenter.gamecenter;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * The User class
 */
public class User implements Serializable {

    private String userName;
    private String userPassword;

    // "currentSliding": BoardManager,  "currentSnake": SnakeManager,  "currentMole":  MoleManager
    private HashMap<String, GameManager> userCurrentGameManagers = new HashMap<>();

    // keys are "hasSavedSliding", "hasSavedSnake", "hasSavedMole", with boolean values
    private HashMap<String, Boolean> hasSavedGames = new HashMap<>();

    //keys are "SlidingScoreManager", "SnakeScoreManager", "MoleScoreManager"
    private HashMap<String, ScoreBoardManager> scoreBoardManagers = new HashMap<>();

    //keys are "sliding_file", "snake_file", "mole_file" with corresponding .ser file to save and load game (not auto save)
    private HashMap<String, ArrayList<String>> gameSavedFiles = new HashMap<>();

    /**
     * Constructor for User
     *
     * @param name     the user name of the user
     * @param password the password for the user
     */
    public User(String name, String password){
        this.userName = name;
        this.userPassword = password;

        //init current game managers map
        userCurrentGameManagers.put("currentSliding", null);
        userCurrentGameManagers.put("currentSnake", null);
        userCurrentGameManagers.put("currentMole", null);

        //init hasSavedGames map
        hasSavedGames.put("hasSavedSliding", false);
        hasSavedGames.put("hasSavedSnake", false);
        hasSavedGames.put("hasSavedMole", false);

        //init saved game files map
        gameSavedFiles.put("sliding_file",new ArrayList<String>());
        gameSavedFiles.put("snake_file", new ArrayList<String>());
        gameSavedFiles.put("mole_file", new ArrayList<String>());


        //init scoreboards managers map
        scoreBoardManagers.put("SlidingScoreManager", new ScoreBoardManager());
        scoreBoardManagers.put("SnakeScoreManager", new ScoreBoardManager());
        scoreBoardManagers.put("MoleScoreManager", new ScoreBoardManager());
    }

    /**
     * Return a user's username
     *
     * @return username
     */
    public String getUserName(){return this.userName;}

    /**
     * Return a user's password
     *
     * @return password of its associated username
     */
    String getUserPassword(){return this.userPassword;}

    public ArrayList<String> get_user_saved_games(String gameName){
        return gameSavedFiles.get(gameName);
    }

    void setUserPassword(String newPassword){this.userPassword = newPassword;}

    /**
     * Get the GameManger object the user select
     *
     * @return the GameManger object which a user wants to use
     */
    public GameManager getCurrentGameManager(String gameManagerName){
        return userCurrentGameManagers.get(gameManagerName);
    }

//    public boolean getHasSavedGame(String gameName){
//        return hasSavedGames.get(gameName);
//    }

    public ScoreBoardManager getScoreBoardManager(String scoreBoardType){
        return scoreBoardManagers.get(scoreBoardType);
    }


    /**
     * Set a user's relating BoardManger object to the one they want to use
     *
     * @param currentGameManager the BoardManger object a user choose to get game data from
     */
    public void setCurrentGameManager(String gameName, GameManager currentGameManager){
        userCurrentGameManagers.put(gameName, currentGameManager);
    }

    @NonNull
    @Override
    public String toString(){return "User: " + this.userName + "     Password: " + this.userPassword;}

    @Override
    public boolean equals(Object other){return other instanceof User
            && (this.userName.equals(((User) other).getUserName()))
            && (this.userPassword.equals(((User) other).getUserPassword()));
    }

}
