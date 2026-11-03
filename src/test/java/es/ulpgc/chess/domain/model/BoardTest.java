package es.ulpgc.chess.domain.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private static final List<PieceType> BACK_RANK = List.of(
            PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
            PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
    );

    private final Board board = Board.initial();

    @Test
    void initialBoardHas32Pieces() {
        assertEquals(32, board.pieces().size());
    }

    @Test
    void initialBoardPlacesWhiteBackRank() {
        assertBackRank(Rank.R1, Color.WHITE);
    }

    @Test
    void initialBoardPlacesBlackBackRank() {
        assertBackRank(Rank.R8, Color.BLACK);
    }

    @Test
    void initialBoardPlacesWhitePawnsOnRank2() {
        assertPawns(Rank.R2, Color.WHITE);
    }

    @Test
    void initialBoardPlacesBlackPawnsOnRank7() {
        assertPawns(Rank.R7, Color.BLACK);
    }

    @Test
    void initialBoardHasEmptyMiddleRanks() {
        for (Rank rank : List.of(Rank.R3, Rank.R4, Rank.R5, Rank.R6)) {
            for (File file : File.values()) {
                Square square = new Square(file, rank);

                assertFalse(board.isOccupied(square), square + " should be empty");
                assertNull(board.pieceAt(square), square + " should have no piece");
            }
        }
    }

    @Test
    void isOccupiedIsTrueWhenSquareHasPiece() {
        assertTrue(board.isOccupied(new Square(File.E, Rank.R1)));
    }

    @Test
    void piecesCannotBeModified() {
        Square square = new Square(File.E, Rank.R4);
        Piece piece = new Piece(PieceType.QUEEN, Color.WHITE);

        assertThrows(UnsupportedOperationException.class, () -> board.pieces().put(square, piece));
    }

    @Test
    void boardIsNotAffectedByChangesToOriginalMap() {
        Square square = new Square(File.E, Rank.R4);
        Piece piece = new Piece(PieceType.QUEEN, Color.WHITE);
        Map<Square, Piece> original = new HashMap<>();
        original.put(square, piece);

        Board copy = new Board(original);
        original.clear();

        assertEquals(piece, copy.pieceAt(square));
    }

    private void assertBackRank(Rank rank, Color color) {
        for (File file : File.values()) {
            Piece expected = new Piece(BACK_RANK.get(file.ordinal()), color);

            assertEquals(expected, board.pieceAt(new Square(file, rank)), file + " " + rank);
        }
    }

    private void assertPawns(Rank rank, Color color) {
        Piece expected = new Piece(PieceType.PAWN, color);

        for (File file : File.values()) {
            assertEquals(expected, board.pieceAt(new Square(file, rank)), file + " " + rank);
        }
    }
}
