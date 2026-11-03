package es.ulpgc.chess.domain.model;

import java.util.Objects;

public record Move(Square from, Square to) {

    public Move {
        Objects.requireNonNull(from, "from cannot be null");
        Objects.requireNonNull(to, "to cannot be null");
        if (from.equals(to)) {
            throw new IllegalArgumentException("from and to cannot be the same");
        }
    }
}
