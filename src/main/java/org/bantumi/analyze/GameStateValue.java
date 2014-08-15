package org.bantumi.analyze;

import java.util.Comparator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bantumi.Score;

 class GameStateValue implements Comparable<GameStateValue> {
    private final Integer value;

    private final EvaluationResult evaluationResult;

    public GameStateValue(Integer value) {
        this.value = value;
        this.evaluationResult = EvaluationResult.GAME_IN_PROGRESS;
    }

    private GameStateValue(Score score, EvaluationResult evaluationResult) {
        this.evaluationResult = evaluationResult;
        this.value = (score.playerAPoints - score.playerBPoints);
    }

    public static GameStateValue gameWon(Score score) {
        return new GameStateValue(score,EvaluationResult.GAME_WON);
    }

    public static GameStateValue gameLost(Score score) {
        return new GameStateValue(score,EvaluationResult.GAME_LOST);
    }

    public static Comparator<GameStateValue> getGameStateValueComparator() {
        return (o1, o2) -> {
            if (o1.evaluationResult.equals(o2.evaluationResult)) {
                return Integer.compare(o1.value, o2.value);

            } else if (EvaluationResult.GAME_WON.equals(o1.evaluationResult)) {
                return 1;
            } else if (EvaluationResult.GAME_WON.equals(o2.evaluationResult)) {
                return -1;
            } else if (EvaluationResult.GAME_LOST.equals(o1.evaluationResult)) {
                return -1;
            } else if (EvaluationResult.GAME_LOST.equals(o2.evaluationResult)) {
                return 1;
            } else {
                return Integer.compare(o1.value, o2.value);
            }
        };
    }

    @Override
    public int compareTo(GameStateValue o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "GameStateValue{" +
                "value=" + value +
                ", evaluationResult=" + evaluationResult +
                '}';
    }

    private enum EvaluationResult {
        GAME_LOST, GAME_IN_PROGRESS, GAME_WON
    }
}
