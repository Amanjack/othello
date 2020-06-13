package models;

import java.util.ArrayList;

public class Reversi {

	// ゲーム進行用フラグ
	private boolean game;
	private boolean fin;
	private boolean ok_reverse;

	// 8×8の盤面のデータを格納するインスタンス
	private String[][] board;

	// 何手目かカウントする変数
	private int cnt_turn;
	private ArrayList<String[][]> record;

	// 配列に格納するオセロのデータ
	static final String EMPTY = "　";
	static final String BLACK = "●";
	static final String WHITE = "○";

	// 置く石と反転される石
	private String stone;
	private String rev_stone;

	// ゲーム進行用メッセージ
	private String msg;

	// 石の駒数のカウント
	private int cnt_black;
	private int cnt_white;

	public Reversi() {
	}
	public Reversi(boolean game, boolean fin, boolean ok_reverse, String[][] board, int cnt_turn,
			ArrayList<String[][]> record, String stone, String rev_stone, String msg, int cnt_black, int cnt_white) {
		super();
		this.game = game;
		this.fin = fin;
		this.ok_reverse = ok_reverse;
		this.board = board;
		this.cnt_turn = cnt_turn;
		this.record = record;
		this.stone = stone;
		this.rev_stone = rev_stone;
		this.msg = msg;
		this.cnt_black = cnt_black;
		this.cnt_white = cnt_white;
	}
	public boolean isGame() {
		return game;
	}
	public void setGame(boolean game) {
		this.game = game;
	}
	public boolean isFin() {
		return fin;
	}
	public void setFin(boolean fin) {
		this.fin = fin;
	}
	public boolean isOk_reverse() {
		return ok_reverse;
	}
	public void setOk_reverse(boolean ok_reverse) {
		this.ok_reverse = ok_reverse;
	}
	public String[][] getBoard() {
		return board;
	}
	public String[] getBoard(int i) {
		return board[i];
	}
	public String getBoard(int i, int j) {
		return board[i][j];
	}
	public void setBoard(String[][] board) {
		this.board = board;
	}
	public void setBoard(int i, int j, String value) {
		this.board[i][j] = value;
	}
	public int getCnt_turn() {
		return cnt_turn;
	}
	public void setCnt_turn(int cnt_turn) {
		this.cnt_turn = cnt_turn;
	}
	public ArrayList<String[][]> getRecord() {
		return record;
	}
	public String[][] getRecord(int i) {
		return record.get(i);
	}
	public void setRecord(ArrayList<String[][]> record) {
		this.record = record;
	}
	public void addRecord(String[][] new_board) {
		this.record.add(new_board);
	}
	public void removeRecord(int i) {
		this.record.remove(i);
	}
	public void clearRecord() {
		this.record.clear();
	}
	public String getStone() {
		return stone;
	}
	public void setStone(String stone) {
		this.stone = stone;
	}
	public String getRev_stone() {
		return rev_stone;
	}
	public void setRev_stone(String rev_stone) {
		this.rev_stone = rev_stone;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCnt_black() {
		return cnt_black;
	}
	public void setCnt_black(int cnt_black) {
		this.cnt_black = cnt_black;
	}
	public int getCnt_white() {
		return cnt_white;
	}
	public void setCnt_white(int cnt_white) {
		this.cnt_white = cnt_white;
	}
}
