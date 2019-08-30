package gamecenter.gamecenter;

public interface UndoableGameManager extends GameManager {

    void recordStep(Object o);

    Object getLastStep();
}
