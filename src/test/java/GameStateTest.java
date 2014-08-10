import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;
import static org.bantumi.Turn.*;

import java.util.List;

import org.junit.Test;

import org.bantumi.GameState;
import org.bantumi.Move;
import org.bantumi.Score;

public class GameStateTest {

    @Test
    public void game_state_is_repeatable() {
        List<Integer> standardBeanSetup = asList(2, 2, 2, 2, 2, 2);

        assertThat(new GameState(standardBeanSetup, standardBeanSetup, PLAYER_A, new Score(0, 0)))
                .isEqualTo(new GameState(standardBeanSetup, standardBeanSetup, PLAYER_A, new Score(0, 0)));
    }

    @Test
    public void performs_basic_move() {
        List<Integer> standardBeanSetup = asList(2, 2, 2, 2, 2, 2);
        List<Integer> resultingBeanSetup = asList(2, 0, 3, 3, 2, 2);

        GameState gameState = new GameState(standardBeanSetup, standardBeanSetup, PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(1));

        assertThat(newGameState).isEqualTo(new GameState(resultingBeanSetup, standardBeanSetup, PLAYER_B, new Score(0, 0)));
    }

    @Test
    public void performs_basic_move_as_player_b() {
        List<Integer> standardBeanSetup = asList(2, 2, 2, 2, 2, 2);
        GameState gameState = new GameState(standardBeanSetup, standardBeanSetup, PLAYER_B, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(1));

        assertThat(newGameState).isEqualTo(new GameState(standardBeanSetup, asList(2, 0, 3, 3, 2, 2), PLAYER_A, new Score(0, 0)));
    }

    @Test
    public void performs_move_giving_some_bean_to_opponent() {
        GameState gameState = new GameState(asList(2, 2, 2, 2, 5, 2), asList(2, 2, 2, 2, 2, 2), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(4));

        assertThat(newGameState).isEqualTo(new GameState(asList(2, 2, 2, 2, 0, 3), asList(3, 3, 3, 2, 2, 2), PLAYER_B, new Score(1, 0)));
    }

    @Test
    public void performs_move_giving_some_bean_to_opponent_as_player_b() {
        GameState gameState = new GameState(asList(2, 2, 2, 2, 2, 2), asList(2, 2, 2, 2, 5, 2), PLAYER_B, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(4));

        assertThat(newGameState).isEqualTo(new GameState(asList(3, 3, 3, 2, 2, 2), asList(2, 2, 2, 2, 0, 3), PLAYER_A, new Score(0, 1)));
    }

    @Test
    public void move_from_bucket_with_enough_beans_to_make_a_full_round_and_ends_in_my_small_bucket() {
        GameState gameState = new GameState(asList(14, 2, 2, 2, 2, 2), asList(2, 2, 2, 2, 2, 2), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(0));

        assertThat(newGameState).isEqualTo(new GameState(asList(1, 4, 3, 3, 3, 3), asList(3, 3, 3, 3, 3, 3), PLAYER_B, new Score(1, 0)));
    }

    @Test
    public void move_from_bucket_with_enough_beans_to_make_a_full_round() {
        GameState gameState = new GameState(asList(20, 2, 2, 2, 2, 2), asList(2, 2, 2, 2, 2, 2), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(0));

        assertThat(newGameState).isEqualTo(new GameState(asList(1, 4, 4, 4, 4, 4), asList(4, 3, 3, 3, 3, 3), PLAYER_B, new Score(2, 0)));
    }

    @Test
    public void when_finishing_in_empty_bucket_it_goes_to_big_pot() {
        GameState gameState = new GameState(asList(3, 2, 2, 0, 2, 2), asList(1, 0, 0, 0, 0, 0), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(1));

        assertThat(newGameState).isEqualTo(new GameState(asList(3, 0, 3, 0, 2, 2), asList(1, 0, 0, 0, 0, 0), PLAYER_B, new Score(1, 0)));
    }

    @Test
    public void when_finishing_in_opponent_empty_bucket_it_is_nothing() {
        GameState gameState = new GameState(asList(2, 2, 2, 2, 3, 2), asList(0, 0, 0, 0, 0, 0), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(4));

        assertThat(newGameState).isEqualTo(new GameState(asList(2, 2, 2, 2, 0, 3), asList(1, 0, 0, 0, 0, 0), PLAYER_B, new Score(1, 0)));

    }

    @Test
    public void steals() {
        GameState gameState = new GameState(asList(2, 2, 2, 0, 2, 2), asList(2, 2, 2, 2, 2, 2), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(1));

        assertThat(newGameState).isEqualTo(new GameState(asList(2, 0, 3, 0, 2, 2), asList(2, 2, 0, 2, 2, 2), PLAYER_B, new Score(3, 0)));
    }

    @Test
    public void cannot_steal_from_oneself() {
        GameState gameState = new GameState(asList(2, 2, 2, 2, 2, 3), asList(2, 0, 2, 2, 2, 2), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(5));

        assertThat(newGameState).isEqualTo(new GameState(asList(2, 2, 2, 2, 2, 0), asList(3, 1, 2, 2, 2, 2), PLAYER_B, new Score(1, 0)));
    }

    @Test
    public void making_full_round_means_finishing_in_empty_bucket_and_stealing() {
        GameState gameState = new GameState(asList(13, 2, 2, 2, 2, 2), asList(1, 2, 3, 4, 5, 6), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(0));

        assertThat(newGameState).isEqualTo(new GameState(asList(0, 3, 3, 3, 3, 3), asList(2, 3, 4, 5, 6, 0), PLAYER_B, new Score(9, 0)));
    }

    @Test
    public void finishing_in_the_big_pot_gives_extra_turn() {
        GameState gameState = new GameState(asList(2, 2, 2, 2, 2, 2), asList(1, 0, 0, 0, 0, 0), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(4));

        assertThat(newGameState).isEqualTo(new GameState(asList(2, 2, 2, 2, 0, 3), asList(1, 0, 0, 0, 0, 0), PLAYER_A, new Score(1, 0)));

    }

    @Test
    public void recognizes_game_end() {
        GameState gameState = new GameState(asList(0, 0, 0, 0, 0, 0), asList(0, 0, 0, 0, 0, 1), PLAYER_B, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(5));

        assertThat(newGameState).isEqualTo(new GameState(asList(0, 0, 0, 0, 0, 0), asList(0, 0, 0, 0, 0, 0), GAME_END, new Score(0, 1)));

    }

    @Test
    public void adds_all_remaining_beans_to_score_after_game_end() {
        GameState gameState = new GameState(asList(1, 1, 0, 1, 1, 1), asList(0, 0, 0, 3, 0, 0), PLAYER_A, new Score(0, 0));

        GameState newGameState = gameState.performMove(new Move(1));

        assertThat(newGameState).isEqualTo(new GameState(asList(0, 0, 0, 0, 0, 0), asList(0, 0, 0, 0, 0, 0), GAME_END, new Score(8, 0)));

    }

}
