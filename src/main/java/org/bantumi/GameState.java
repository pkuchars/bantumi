package org.bantumi;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.*;
import static org.bantumi.Buckets.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GameState {

    private final Buckets playerHavingTurnBuckets;

    private final Buckets otherPlayerBuckets;

    private final int playersSmallBucketsCount;

    private final Turn turn;

    private final Score score;

    public GameState(List<Integer> playerABeansSetup, List<Integer> playerBBeansSetup, Turn turn, Score score) {
        this.score = score;
        playersSmallBucketsCount = playerABeansSetup.size();
        Buckets playerABuckets = bucketsFromSetup(playerABeansSetup);
        Buckets playerBBuckets = bucketsFromSetup(playerBBeansSetup);
        playerHavingTurnBuckets = Turn.PLAYER_A.equals(turn) ? playerABuckets : playerBBuckets;
        otherPlayerBuckets = Turn.PLAYER_A.equals(turn) ? playerBBuckets : playerABuckets;
        this.turn = turn;

    }

    private Buckets bucketsFromSetup(List<Integer> beansSetup) {
        return range(0, beansSetup.size()).boxed()
                .map((i) -> new Bucket(beansSetup.get(i))).collect(bucketCollector);
    }

    public GameState(Buckets playerHavingTurnBuckets, Buckets otherPlayerBuckets, Turn turn,
                     Score score) {
        this.playersSmallBucketsCount = playerHavingTurnBuckets.size();
        this.playerHavingTurnBuckets = playerHavingTurnBuckets;
        this.otherPlayerBuckets = otherPlayerBuckets;
        this.turn = turn;
        this.score = score;
    }

    public GameState performMove(Move fromBucket) {
        if(playerHavingTurnBuckets.get(fromBucket.getBucketIndex()).isEmpty()) {
            throw new RuntimeException("Z pustego kosza, osle?!");
        }

        MoveInfo move = new MoveInfo(fromBucket);

        Buckets newPlayerHavingTurnBuckets = newBuckets(index -> bucketOfPlayerHavingTurnAfterMove(move, index));
        Buckets newOtherPlayerBuckets = newBuckets(index -> bucketOfOtherPlayerAfterMove(move, index));

        Score newScore = newScore(move, score);

        if (isGameFinished(newOtherPlayerBuckets, newPlayerHavingTurnBuckets)) {
            return new GameState(zeros(playersSmallBucketsCount), zeros(playersSmallBucketsCount), Turn.GAME_END,
                    finalScore(newPlayerHavingTurnBuckets, newOtherPlayerBuckets, newScore));
        } else if (turn.equals(move.getNextTurn(turn))) {
            return new GameState(newPlayerHavingTurnBuckets, newOtherPlayerBuckets, turn, newScore);
        } else {
            return new GameState(newOtherPlayerBuckets, newPlayerHavingTurnBuckets, turn.getOtherPlayer(), newScore);

        }
    }

    public Collection<Move> possibleMoves() {
        return IntStream.range(0, playersSmallBucketsCount).boxed()
                .filter(i -> !playerHavingTurnBuckets.get(i).isEmpty())
                .map(Move::new)
                .collect(toList());
    }

    private Score finalScore(Buckets newPlayerHavingTurnBuckets, Buckets newOtherPlayerBuckets, Score newScore) {
        return newScore.afterPlayerScoring(turn, newPlayerHavingTurnBuckets.sumOfBeans())
                .afterPlayerScoring(turn.getOtherPlayer(), newOtherPlayerBuckets.sumOfBeans());
    }

    private boolean isGameFinished(Buckets newOtherPlayerBuckets, Buckets newPlayerHavingTurnBuckets) {
        return newOtherPlayerBuckets.areAllEmpty() || newPlayerHavingTurnBuckets.areAllEmpty();
    }

    private Score newScore(MoveInfo move, Score score) {
        int pointsScoredDirectly = beansLandingIn(move, playersSmallBucketsCount);
        int beansStolen = move.isEndingInPlayersEmptyBucket() ?
                1 + otherPlayerBuckets.get(oppositeIndexTo(move.lastBeanGoesInto)).beanCount : 0;
        int beansStolenByMakingOneFullRound = move.isExactOneFullRound() ? 1 : 0;
        return score.afterPlayerScoring(turn, pointsScoredDirectly + beansStolen + beansStolenByMakingOneFullRound);
    }

    private Buckets newBuckets(Function<Integer, Bucket> newBucket) {
        return range(0, playersSmallBucketsCount).boxed()
                .map(newBucket)
                .collect(bucketCollector);
    }

    private Bucket bucketOfPlayerHavingTurnAfterMove(MoveInfo move, Integer index) {
        if (move.isEndingInPlayersEmptyBucket(index)) {
            return new Bucket(0);
        }
        if (move.isFrom(index)) {
            return new Bucket(beansLandingIn(move, index));
        }

        return playerHavingTurnBuckets.get(index).withBeansAdded(beansLandingIn(move, index));
    }

    private Bucket bucketOfOtherPlayerAfterMove(MoveInfo move, Integer index) {
        int oppositeBucket = oppositeIndexTo(index);
        if (move.isEndingInPlayersEmptyBucket(oppositeBucket)) {
            return new Bucket(0);
        }
        return otherPlayerBuckets.get(index).withBeansAdded(beansLandingIn(move, opponentEquivalentIndexOf(index)));
    }

    private int opponentEquivalentIndexOf(Integer index) {
        return index + playersSmallBucketsCount + 1;
    }

    private int oppositeIndexTo(Integer index) {
        return playersSmallBucketsCount - 1 - index;
    }

    private int beansLandingIn(MoveInfo move, Integer index) {
        int b = move.beansToDistribute + 1 - distance(move.getBucketIndex(), index);

        return divideRoundUp(b, bucketCount());
    }

    private int bucketCount() {
        return 2 * playersSmallBucketsCount + 1;
    }

    private Integer distance(Integer from, Integer to) {
        if (to <= from) {
            return bucketCount() + to - from;
        } else {
            return to - from;
        }
    }

    private int divideRoundUp(int numerator, int divisor) {
        return (numerator + divisor - 1) / (divisor);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Score getScore() {
        return score;
    }

    public Turn getTurn() {
        return turn;
    }

    private class MoveInfo {

        private final Move move;

        private final Integer beansToDistribute;

        private final Integer lastBeanGoesInto;

        public MoveInfo(Move move) {
            this.move = move;
            beansToDistribute = playerHavingTurnBuckets.get(move.getBucketIndex()).beanCount;
            lastBeanGoesInto = (move.getBucketIndex() + beansToDistribute) % bucketCount();
        }

        public Integer getBucketIndex() {
            return move.getBucketIndex();
        }

        private Boolean isExactOneFullRoundFrom(Integer index) {
            return index.equals(move.getBucketIndex()) && isExactOneFullRound();
        }

        public boolean isEndingInPlayersEmptyBucket(Integer index) {
            return (lastBeanGoesInto.equals(index) && playerHavingTurnBuckets.get(index).isEmpty()) || isExactOneFullRoundFrom(index);
        }

        public boolean isEndingInPlayersEmptyBucket() {
            return lastBeanGoesInto < playersSmallBucketsCount &&
                    playerHavingTurnBuckets.get(lastBeanGoesInto).isEmpty() || isExactOneFullRound();
        }

        public boolean isFrom(Integer index) {
            return index.equals(getBucketIndex());
        }

        public Turn getNextTurn(Turn turn) {
            if (isEndingInPlayersBigBucket()) {
                return turn;
            } else {
                return turn.getOtherPlayer();
            }
        }

        private Boolean isEndingInPlayersBigBucket() {
            return lastBeanGoesInto.equals(playersSmallBucketsCount);
        }

        public boolean isExactOneFullRound() {
            return (move.getBucketIndex() + beansToDistribute) == (move.getBucketIndex() + bucketCount());
        }
    }

}
