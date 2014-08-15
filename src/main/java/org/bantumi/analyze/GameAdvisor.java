package org.bantumi.analyze;

import org.bantumi.GameState;
import org.bantumi.Move;

public interface GameAdvisor {
    Move bestMoveIn(GameState state);
}
