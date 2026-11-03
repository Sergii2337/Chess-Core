package es.ulpgc.chess.domain.model;

import java.util.Objects;

public record Square(File file, Rank rank) {

    public Square {
        Objects.requireNonNull(file, "file cannot be null");
        Objects.requireNonNull(rank, "rank cannot be null");
    }
}
