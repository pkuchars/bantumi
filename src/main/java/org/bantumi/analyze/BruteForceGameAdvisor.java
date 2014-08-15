package org.bantumi.analyze;

import java.util.Comparator;

import org.bantumi.GameState;
import org.bantumi.Move;

public class BruteForceGameAdvisor implements GameAdvisor {

    private final int analysisDepth;

    public BruteForceGameAdvisor(int analysisDepth) {
        this.analysisDepth = analysisDepth;
    }

    @Override
    public Move bestMoveIn(GameState state){
        GameStateAnalyzer analyzer = new GameStateAnalyzer();
        return state.possibleMoves().stream()
                .map(move -> new MoveOutcome(move,
                        analyzer.playerAValueOf(state.performMove(move), analysisDepth)))
                .max(MoveOutcome.comparator()).get().move;

    }

    private static class MoveOutcome {
        private final Move move;

        private final GameStateValue gameStateValue;

        public MoveOutcome(Move move, GameStateValue gameStateValue) {
            this.move = move;
            this.gameStateValue = gameStateValue;
        }

        public static Comparator<MoveOutcome> comparator(){
            return new Comparator<MoveOutcome>() {
                @Override
                public int compare(MoveOutcome o1, MoveOutcome o2) {
                    return GameStateValue.getGameStateValueComparator().compare(o1.gameStateValue,o2.gameStateValue);
                }
            };
        }
    }
}
