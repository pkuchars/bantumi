import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;
import static org.bantumi.Turn.*;

import org.junit.Test;

import org.bantumi.GameState;
import org.bantumi.Move;
import org.bantumi.Score;

public class FieldTest {

    @Test
    public void game1() {
        GameState game = new GameState(asList(3, 3, 3, 3, 3, 3), asList(3, 3, 3, 3, 3, 3), PLAYER_A, new Score(0, 0));

        game = game.performMove(new Move(3));
        assertThat(game).isEqualTo(new GameState(asList(3, 3, 3, 0, 4, 4), asList(3, 3, 3, 3, 3, 3), PLAYER_A, new Score(1, 0)));

        game = game.performMove(new Move(0));
        assertThat(game).isEqualTo(new GameState(asList(0, 4, 4, 0, 4, 4), asList(3, 3, 0, 3, 3, 3), PLAYER_B, new Score(5, 0)));

        game = game.performMove(new Move(3));
        assertThat(game).isEqualTo(new GameState(asList(0, 4, 4, 0, 4, 4), asList(3, 3, 0, 0, 4, 4), PLAYER_B, new Score(5, 1)));

        game = game.performMove(new Move(0));
        assertThat(game).isEqualTo(new GameState(asList(0, 4, 0, 0, 4, 4), asList(0, 4, 1, 0, 4, 4), PLAYER_A, new Score(5, 6)));

        game = game.performMove(new Move(4));
        assertThat(game).isEqualTo(new GameState(asList(0, 4, 0, 0, 0, 5), asList(1, 5, 1, 0, 4, 4), PLAYER_B, new Score(6, 6)));

        game = game.performMove(new Move(2));
        assertThat(game).isEqualTo(new GameState(asList(0, 4, 0, 0, 0, 5), asList(1, 5, 0, 0, 4, 4), PLAYER_A, new Score(6, 7)));

        game = game.performMove(new Move(1));
        assertThat(game).isEqualTo(new GameState(asList(0, 0, 1, 1, 1, 6), asList(1, 5, 0, 0, 4, 4), PLAYER_B, new Score(6, 7)));

        game = game.performMove(new Move(1));
        assertThat(game).isEqualTo(new GameState(asList(0, 0, 1, 1, 1, 6), asList(1, 0, 1, 1, 5, 5), PLAYER_B, new Score(6, 8)));

        game = game.performMove(new Move(0));
        assertThat(game).isEqualTo(new GameState(asList(0, 0, 1, 1, 0, 6), asList(0, 0, 1, 1, 5, 5), PLAYER_A, new Score(6, 10)));

        game = game.performMove(new Move(3));
        assertThat(game).isEqualTo(new GameState(asList(0, 0, 1, 0, 0, 6), asList(0, 0, 1, 1, 5, 5), PLAYER_B, new Score(7, 10)));

        game = game.performMove(new Move(5));
        assertThat(game).isEqualTo(new GameState(asList(1, 1, 2, 1, 0, 6), asList(0, 0, 1, 1, 5, 0), PLAYER_A, new Score(7, 11)));

        game = game.performMove(new Move(3));
        assertThat(game).isEqualTo(new GameState(asList(1, 1, 2, 0, 0, 6), asList(0, 0, 1, 1, 5, 0), PLAYER_B, new Score(8, 11)));

        game = game.performMove(new Move(4));
        assertThat(game).isEqualTo(new GameState(asList(2, 2, 3, 0, 0, 6), asList(0, 0, 1, 1, 0, 1), PLAYER_A, new Score(8, 12)));

        game = game.performMove(new Move(1));
        assertThat(game).isEqualTo(new GameState(asList(2, 0, 4, 0, 0, 6), asList(0, 0, 0, 1, 0, 1), PLAYER_B, new Score(10, 12)));

        game = game.performMove(new Move(3));
        assertThat(game).isEqualTo(new GameState(asList(2, 0, 4, 0, 0, 6), asList(0, 0, 0, 0, 0, 1), PLAYER_A, new Score(10, 13)));

        game = game.performMove(new Move(0));
        assertThat(game).isEqualTo(new GameState(asList(0, 1, 5, 0, 0, 6), asList(0, 0, 0, 0, 0, 1), PLAYER_B, new Score(10, 13)));

        game = game.performMove(new Move(5));
        assertThat(game).isEqualTo(new GameState(asList(0, 0, 0, 0, 0, 0), asList(0, 0, 0, 0, 0, 0), GAME_END, new Score(22, 14)));

    }
}
