package org.bantumi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Score {
    public final int playerAPoints;
    public final int playerBPoints;

    public Score(int playerAPoints, int playerBPoints) {
        this.playerAPoints = playerAPoints;
        this.playerBPoints = playerBPoints;
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
        return "Score{" +
                "A=" + playerAPoints +
                ", B=" + playerBPoints +
                '}';
    }

    public Score afterPlayerScoring(Turn turn, int pointsScored) {
        if(Turn.PLAYER_A.equals(turn)) {
            return new Score(playerAPoints + pointsScored, playerBPoints);
        } else {
            return new Score(playerAPoints, playerBPoints + pointsScored);
        }
    }
}
