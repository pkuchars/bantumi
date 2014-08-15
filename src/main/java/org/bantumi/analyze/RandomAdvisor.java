package org.bantumi.analyze;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bantumi.GameState;
import org.bantumi.Move;

public class RandomAdvisor implements GameAdvisor {

    @Override
    public Move bestMoveIn(GameState state) {
        List<Move> moves = (List<Move>) state.possibleMoves();
        return moves.get(randIndex(moves.size()));
    }

    public static int randIndex(int max) {
        return new Random().nextInt(max);
    }
}
