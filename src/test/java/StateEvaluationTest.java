import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;
import static org.bantumi.Turn.*;
import static org.bantumi.analyze.GameStateValue.*;

import org.bantumi.GameState;
import org.bantumi.Move;
import org.bantumi.Score;
import org.bantumi.analyze.BruteForceGameAdvisor;
import org.bantumi.analyze.GameStateAnalyzer;
import org.bantumi.analyze.GameStateValue;
import org.junit.Test;

public class StateEvaluationTest {

    private GameStateAnalyzer analyzer = new GameStateAnalyzer();

    @Test
    public void simple_score_evaluation_for_zero_depth_analysis() {
        GameState gameState = new GameState(asList(1, 2, 3), asList(4, 5, 6), PLAYER_B, new Score(15, 44));

        assertThat(analyzer.playerAValueOf(gameState, 0)).isEqualTo(new GameStateValue(15 - 44));
    }

    @Test
    public void value_is_best_of_player_a_moves() {
        GameState gameState = new GameState(asList(1, 2), asList(4, 5), PLAYER_A, new Score(0, 0));

        assertThat(analyzer.playerAValueOf(gameState, 1)).isEqualTo(new GameStateValue(1));
    }

    @Test
    public void value_is_best_of_player_a_moves_case_2() {
        GameState gameState = new GameState(asList(1, 0, 1), asList(5, 4, 3), PLAYER_A, new Score(0, 0));

        assertThat(analyzer.playerAValueOf(gameState, 1)).isEqualTo(new GameStateValue(5));
    }

    @Test
    public void value_is_most_unfavorable_player_b_move() {
        GameState gameState = new GameState(asList(5, 4, 3, 2, 1), asList(1, 0, 1, 0, 1), PLAYER_B, new Score(0, 0));

        assertThat(analyzer.playerAValueOf(gameState, 1)).isEqualTo(new GameStateValue(-5));
    }

    @Test
    public void recognize_game_won() {
        GameState gameState = new GameState(asList(0, 0), asList(0, 0), GAME_END, new Score(88, 87));

        assertThat(analyzer.playerAValueOf(gameState, 15)).isEqualTo(gameWon(new Score(88,87)));
    }

    @Test
    public void zero_depth_analysis_should_recognize_game_won() {
        GameState gameState = new GameState(asList(0, 0), asList(0, 0), GAME_END, new Score(88, 87));

        assertThat(analyzer.playerAValueOf(gameState, 0)).isEqualTo(gameWon(new Score(88,87)));
    }

    @Test
    public void recognize_game_lost() {
        GameState gameState = new GameState(asList(0, 0), asList(0, 0), GAME_END, new Score(87, 88));

        assertThat(analyzer.playerAValueOf(gameState, 15)).isEqualTo(gameLost(new Score(87, 88)));
    }

    @Test
    public void game_won_is_greater_than_any_other_value() {
        GameState gameState = new GameState(asList(1, 1, 1, 0, 1), asList(0, 5, 0, 0, 0), PLAYER_A, new Score(100, 0));

        assertThat(analyzer.playerAValueOf(gameState, 1)).isEqualTo(gameWon(new Score(109, 0)));
    }

}
