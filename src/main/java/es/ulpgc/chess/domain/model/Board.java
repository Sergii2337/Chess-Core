package es.ulpgc.chess.domain.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Board(Map<Square, Piece> pieces) {

    public Board {
        pieces = Map.copyOf(pieces);
    }

    public Piece pieceAt(Square square) {
        return pieces.get(square);
    }

    public boolean isOccupied(Square square) {
        return pieces.containsKey(square);
    }

    public static Board initial() {
        PieceType[] backRank = {
                PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
                PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
        };

        Map<Square, Piece> pieces = Arrays.stream(File.values())
                .flatMap(file -> {

                    PieceType type = backRank[file.ordinal()];

                    return Stream.of(
                            Map.entry(new Square(file, Rank.R1), new Piece(type, Color.WHITE)),
                            Map.entry(new Square(file, Rank.R2), new Piece(PieceType.PAWN, Color.WHITE)),
                            Map.entry(new Square(file, Rank.R7), new Piece(PieceType.PAWN, Color.BLACK)),
                            Map.entry(new Square(file, Rank.R8), new Piece(type, Color.BLACK))
                    );
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new Board(pieces);
    }
}