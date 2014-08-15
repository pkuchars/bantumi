package org.bantumi.league;

import static org.junit.Assert.*;

import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.bantumi.analyze.BruteForceGameAdvisor;
import org.bantumi.analyze.RandomAdvisor;
import org.junit.Test;

public class LeagueTest {

    @Test
    public void brute_vs_random() {
        League league = new League();

        league.playGame(new BruteForceGameAdvisor(5),new RandomAdvisor());
    }

    @Test
    public void league_test() {
        IntStream.range(0, 1).boxed().parallel().forEach(integer -> new League().playLeague());

    }
}
