package org.bantumi;

public enum Turn {
    PLAYER_A {
        @Override
        public Turn getOtherPlayer() {
            return PLAYER_B;
        }
    }, PLAYER_B {
        @Override
        public Turn getOtherPlayer() {
            return PLAYER_A;
        }
    }, GAME_END {
        @Override
        public Turn getOtherPlayer() {
            return GAME_END;
        }
    };

    public abstract Turn getOtherPlayer();

}
