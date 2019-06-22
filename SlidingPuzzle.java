package Pack;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

/**
 * Gra "Przestawne puzzle"
 * 
 * @author https://github.com/dylionen
 *
 */
public class SlidingPuzzle extends GraphStateImpl {
	// Ruchy w grze
	public final int MOVE_UP = 0;
	public final int MOVE_DOWN = 1;
	public final int MOVE_LEFT = 2;
	public final int MOVE_RIGHT = 3;

	// plansza kwadratowa o polu N*N
	public static int N = 3;

	public byte[][] board;
	private SlidingPuzzle prev = null;
	private byte inheritedDirection;

	public SlidingPuzzle() {
		board = new byte[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = (byte) ((byte) (i * N) + j);
			}
		}
	}

	public SlidingPuzzle(SlidingPuzzle sp, byte direction) {
		prev = sp;
		inheritedDirection = direction;
		board = new byte[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = sp.board[i][j];
			}
		}
	}

	/*
	 * Metoda do wyświetlenia drogi wygranej
	 */

	public void showMeTheWayToWin() {

		if (prev != null) {
			prev.showMeTheWayToWin();
		}
		System.out.println(getDirectionChar(inheritedDirection) + Arrays.deepToString(this.board));
	}

	public byte[][] getBoard() {
		return board;
	}

	public byte getInheritedDirection() {
		return inheritedDirection;
	}

	public void setBoard(byte[][] board) {
		this.board = board;
	}

	/**
	 * 
	 * @param kierunek 0-góra,1-gół,2-lewo,3-prawo
	 * @return zwraca true jeżeli można przesunąć klocek w miejsce 0
	 */
	public boolean canMove(int kierunek) {
		switch (kierunek) {
		case MOVE_UP:
			return canMoveUp();
		case MOVE_DOWN:
			return canMoveDown();
		case MOVE_LEFT:
			return canMoveLeft();
		case MOVE_RIGHT:
			return canMoveRight();
		}
		return false;
	}

	public char getDirectionChar(int side) {
		char[] signs = new char[] { '\u2191', '\u2193', '\u2190', '\u2192' };
		if ((side >= 0) && (side < 4)) {
			return signs[side];
		} else
			return '\u2218';

	}

	public boolean canMoveUp() {
		for (int i = 0; i < N; i++)
			if (board[N - 1][i] == 0)
				return false;

		return true;
	}

	public boolean canMoveDown() {
		for (int i = 0; i < N; i++)
			if (board[0][i] == 0)
				return false;

		return true;
	}

	public boolean canMoveLeft() {
		for (int i = 0; i < N; i++)
			if (board[i][N - 1] == 0)
				return false;

		return true;
	}

	public boolean canMoveRight() {
		for (int i = 0; i < N; i++)
			if (board[i][0] == 0)
				return false;

		return true;
	}

	public void moveUp() {
		if (canMoveUp()) {
			boolean isMoved = false;
			for (int i = 0; i < N; i++) {
				if (isMoved) {
					break;
				}
				for (int j = 0; j < N - 1; j++) {
					if (board[i][j] == 0) {
						board[i][j] = board[i + 1][j];
						board[i + 1][j] = 0;
						isMoved = true;
					}

				}
			}
		}

	}

	public void moveDown() {
		if (canMoveDown()) {
			boolean isMoved = false;
			for (int i = 1; i < N; i++) {
				if (isMoved) {
					break;
				}
				for (int j = 0; j < N; j++) {
					if (board[i][j] == 0) {
						board[i][j] = board[i - 1][j];
						board[i - 1][j] = 0;
						isMoved = true;
					}

				}
			}
		}

	}

	public void moveLeft() {
		if (canMoveLeft()) {
			boolean isMoved = false;
			for (int j = 0; j < N; j++) {
				if (isMoved) {
					break;
				}
				for (int i = 0; i < N - 1; i++) {
					if (board[i][j] == 0) {
						board[i][j] = board[i][j + 1];
						board[i][j + 1] = 0;
						isMoved = true;
					}

				}
			}
		}

	}

	public void moveRight() {
		if (canMoveRight()) {
			boolean isMoved = false;
			for (int j = 1; j < N; j++) {
				if (isMoved) {
					break;
				}
				for (int i = 0; i < N; i++) {
					if (board[i][j] == 0) {
						board[i][j] = board[i][j - 1];
						board[i][j - 1] = 0;
						isMoved = true;
					}

				}
			}
		}
	}

	public void move(int kierunek) {
		switch (kierunek) {
		case MOVE_UP: {
			moveUp();
			break;
		}

		case MOVE_DOWN: {
			moveDown();
			break;
		}
		case MOVE_LEFT: {
			moveLeft();
			break;
		}
		case MOVE_RIGHT: {
			moveRight();
			break;
		}
		}

	}

	public void mixBoard(int ilosc) {
		Random rand = new Random(2);
		byte draw;
		while (ilosc != 0) {
			draw = (byte) rand.nextInt(4);
			if (canMove(draw)) {
				move(draw);
				ilosc--;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder txt = new StringBuilder();
		txt.append("\u250F\u2501\u2501\u2501\u2533\u2501\u2501\u2501\u2533\u2501\u2501\u2501\u2513\n");
		for (int i = 0; i < N; i++) {
			txt.append("\u2503");
			for (int j = 0; j < N; j++) {
				txt.append(" " + board[i][j] + " \u2503");
			}
			txt.append('\n');
			if (i != N - 1) {
				txt.append("\u2523\u2501\u2501\u2501\u254B\u2501\u2501\u2501\u254B\u2501\u2501\u2501\u252B\n");
			}
		}
		txt.append("\u2517\u2501\u2501\u2501\u253B\u2501\u2501\u2501\u253B\u2501\u2501\u2501\u251B\n");
		return txt.toString();
	}

	@Override
	public List<GraphState> generateChildren() {
		List<GraphState> children = new ArrayList<>();

		if (getMisplacedTiles() == 0) {
			return children;
		}

		for (byte i = 0; i < 4; i++) {

			if (this.canMove(i)) {
				byte[][] temp = new byte[3][3];
				temp = board.clone();
				SlidingPuzzle child = new SlidingPuzzle(this, i);
				child.move(i);
				children.add(child);
			}
		}

		return children;

	}

	@Override
	public int hashCode() {
		byte[] puzzle1d = new byte[N * N];
		int k = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				puzzle1d[k++] = board[i][j];
			}
		}
		return Arrays.hashCode(puzzle1d);
	}

	@Override
	public boolean isSolution() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] != (byte) ((byte) (i * N) + j)) {
					return false;
				}
			}
		}
		return true;
	}

	public int getMisplacedTiles() {
		int incorrect = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] != (byte) ((byte) (i * N) + j)) {
					incorrect++;
				}
			}
		}
		return incorrect;
	}

	public int getManhattan() {
		short licznik = 0;
		byte tmp;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tmp = board[i][j];
				licznik += Math.abs((i - (tmp / N)) + (j - (tmp % N)));
				/*
				 * //tester System.out.println("Liczba: " + tmp + "Pozycje: i: " + i + " j: " +
				 * j ); System.out.println("Liczba: " + tmp + "Pozycje oryginalne: x" + (tmp /
				 * N) + " j: " + (tmp % N) ); System.out.println("Wynikiem jest: " + Math.abs((i
				 * - (tmp / N)) + (j - (tmp % N)))); System.out.println();
				 */
			}
		}

		return licznik;
	}

}
