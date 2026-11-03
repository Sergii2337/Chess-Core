package es.ulpgc.chess.domain.model;

import java.util.Objects;

public record Piece(PieceType type, Color color) {

    public Piece {
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(color, "color cannot be null");
    }
}
