package main.com.igmv.eightpuzzle;

import java.util.ArrayList;
import java.util.List;

import main.com.igmv.search.Successor;
import main.com.igmv.search.SuccessorFunction;


public class EightPuzzleSuccessorFunction implements SuccessorFunction {

	public List getSuccessors(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		List<Successor> successors = new ArrayList<Successor>();
		if (board.canMoveGap(EightPuzzleBoard.UP)) {
			EightPuzzleBoard newBoard = copyOf(board);
			newBoard.moveGapUp();
			successors.add(new Successor(EightPuzzleBoard.UP, newBoard));
		}
		if (board.canMoveGap(EightPuzzleBoard.DOWN)) {
			EightPuzzleBoard newBoard = copyOf(board);
			newBoard.moveGapDown();
			successors.add(new Successor(EightPuzzleBoard.DOWN, newBoard));
		}
		if (board.canMoveGap(EightPuzzleBoard.LEFT)) {
			EightPuzzleBoard newBoard = copyOf(board);
			newBoard.moveGapLeft();
			successors.add(new Successor(EightPuzzleBoard.LEFT, newBoard));
		}
		if (board.canMoveGap(EightPuzzleBoard.RIGHT)) {
			EightPuzzleBoard newBoard = copyOf(board);
			newBoard.moveGapRight();
			successors.add(new Successor(EightPuzzleBoard.RIGHT, newBoard));
		}
		return successors;
	}

	private EightPuzzleBoard copyOf(EightPuzzleBoard board) {
		EightPuzzleBoard newBoard = new EightPuzzleBoard();
		newBoard.setBoard(board.getPositions());
		return newBoard;
	}

}