package org.bantumi.analyze;

import static org.bantumi.Turn.*;
import static org.bantumi.analyze.GameStateValue.*;

import org.bantumi.GameState;
import org.bantumi.Score;

class GameStateAnalyzer {
    public GameStateValue playerAValueOf(GameState gameState, Integer analysisDepth) {

        if (GAME_END.equals(gameState.getTurn())) {
            return ultimateResult(gameState.getScore());
        } else if (analysisDepth == 0) {
            return stateValue(gameState);
        } else if (PLAYER_A.equals(gameState.getTurn())) {
            return gameState.possibleMoves().stream().parallel()
                    .map(move -> playerAValueOf(gameState.performMove(move), analysisDepth - 1))
                    .max(getGameStateValueComparator()).get();
        } else {
            return gameState.possibleMoves().stream().parallel()
                    .map(move -> playerAValueOf(gameState.performMove(move), analysisDepth - 1))
                    .min(getGameStateValueComparator()).get();
        }
    }

    private GameStateValue ultimateResult(Score score) {
        return score.playerAPoints > score.playerBPoints ? gameWon(score) : gameLost(score);
    }

    private GameStateValue stateValue(GameState gameState) {
        Score score = gameState.getScore();
        return new GameStateValue(score.playerAPoints - score.playerBPoints);
    }
}
