package org.bantumi.analyze;

import java.util.Collection;
import java.util.List;

import org.bantumi.GameState;
import org.bantumi.Move;

public class RightHandSideChoiceAdvisor implements GameAdvisor{
    @Override
    public Move bestMoveIn(GameState state) {
        List<Move> moves = (List<Move>)state.possibleMoves();
        return moves.get(moves.size() - 1);
    }
}
