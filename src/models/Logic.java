package models;

import java.util.ArrayList;

public class Logic {

	// 初期設定するメソッド
	static public void initialize(Reversi reversi) {

		// インスタンスの生成
		String[][] board = new String[8][8];
		ArrayList<String[][]> record = new ArrayList<String[][]>();

		// 初期化
		reversi.setGame(false);
		reversi.setFin(false);
		reversi.setOk_reverse(false);
		reversi.setBoard(board);
		reversi.setRecord(record);
		reversi.setCnt_turn(0);
		reversi.setCnt_black(0);
		reversi.setCnt_white(0);

		//オセロ版の要素を全てクリアする
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = Reversi.EMPTY;
			}
		}

		//初期状態の配置
		board[3][3] = Reversi.BLACK;
		board[3][4] = Reversi.WHITE;
		board[4][3] = Reversi.WHITE;
		board[4][4] = Reversi.BLACK;

		// 0手目の盤面をコピーする
		add_record(reversi);

		reversi.setCnt_black(2);
		reversi.setCnt_white(2);

		//次うつ駒の色を指定
		reversi.setStone(Reversi.BLACK);
		reversi.setRev_stone(Reversi.WHITE);

		reversi.setMsg(reversi.getStone() +"のターンです");

		//ゲーム実行中フラグ
		reversi.setGame(true);

	}

	static public void add_record(Reversi reversi) {

		String[][] new_board = new String[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				new_board[i][j] = reversi.getBoard(i,j);
			}
		}
		reversi.addRecord(new_board);
	}

	static public void setStone(Reversi reversi, int x, int y) {


		// 位置名称の変数のセット
		int a = x+1;
		int b = y+1;
		String position = a+" - "+b;

		// 駒を配置できる場合
		if ((reversi.getBoard(y,x)).equals(Reversi.EMPTY)) {

			// 駒をセット
			reversi.setBoard(y, x, reversi.getStone());

			// ひっくり返す処理
			turnStone(x, y, reversi);

			// ひっくり返す場所があった場合と
			// なかった場合の処理
			if(reversi.isOk_reverse()) {

				// 駒の集計
				cnt(reversi);

				// 次うつ駒の設定
				String next_rev_storn = reversi.getStone();
				reversi.setStone(reversi.getRev_stone());
				reversi.setRev_stone(next_rev_storn);

				// 現在の盤面を手数ごとに格納
				reversi.setCnt_turn(reversi.getCnt_turn()+1);
				add_record(reversi);

			} else {
				reversi.setMsg(position+"に駒は置けません");
				reversi.setBoard(y, x , Reversi.EMPTY);
			}

		} else {
			// 既に駒がおいてある位置を指定した場合
			reversi.setMsg(position+"にはすでに駒が置いてあります");
		}
	}

	static public void cnt(Reversi reversi) {

		//駒の集計をする処理
		boolean existempty = false;
		reversi.setCnt_black(0);
		reversi.setCnt_white(0);

		for (String[] sarr : reversi.getBoard()) {
			for (String s : sarr) {

				//空いている座標があるか、各駒数の集計
				if (s.equals(Reversi.EMPTY)) {
					existempty = true;
				} else if (s.equals(Reversi.BLACK)) {
					reversi.setCnt_black(reversi.getCnt_black()+1);
				} else if (s.equals(Reversi.WHITE)) {
					reversi.setCnt_white(reversi.getCnt_white()+1);
				}
			}
		}

		if (existempty) {
			reversi.setMsg(reversi.getRev_stone() + "のターンです");
		} else if(reversi.getCnt_black() > reversi.getCnt_white()) {
			reversi.setMsg(Reversi.BLACK + "の勝ちです");
			reversi.setFin(true);
		} else if(reversi.getCnt_white() > reversi.getCnt_black()) {
			reversi.setMsg(Reversi.WHITE + "の勝ちです");
			reversi.setFin(true);
		} else if(reversi.getCnt_white() == reversi.getCnt_black()) {
			reversi.setMsg("引き分けです");
			reversi.setFin(true);
		}
	}

	static public void stop(Reversi reversi) {

		if(reversi.getCnt_turn() > 0) {

			// 前回の盤面に戻す処理
			reversi.setCnt_turn(reversi.getCnt_turn()-1);
			reversi.setBoard(reversi.getRecord(reversi.getCnt_turn()));

			// 不要な盤面の削除
			reversi.removeRecord(reversi.getCnt_turn()+1);

			// 次うつ駒の設定
			String next_rev_storn = reversi.getStone();
			reversi.setStone(reversi.getRev_stone());
			reversi.setRev_stone(next_rev_storn);

			reversi.setMsg(reversi.getStone()+"のターンです");

		} else if(reversi.getCnt_turn() == 0) {
			reversi.setMsg("一手目です");
		}
	}

	static public void pass(Reversi reversi) {

		// 次うつ駒の設定
		String next_rev_storn = reversi.getStone();
		reversi.setStone(reversi.getRev_stone());
		reversi.setRev_stone(next_rev_storn);

		// 現在の盤面を手数ごとに格納
		reversi.setCnt_turn(reversi.getCnt_turn()+1);
		add_record(reversi);

		reversi.setMsg(reversi.getStone()+"のターンです");

	}

	static public void turnStone(int x, int y, Reversi reversi) {

		// フラグの初期化
		reversi.setOk_reverse(false);

		// 8方向の駒の配置を確認し、ひっくり返す
		turnLeftUp(x, y, reversi);
		turnUp(x, y, reversi);
		turnRightUp(x, y, reversi);
		turnLeft(x, y, reversi);
		turnRight(x, y, reversi);
		turnLeftDown(x, y, reversi);
		turnDown(x, y, reversi);
		turnRightDown(x, y, reversi);

	}

	static public void turnLeftUp(int x, int y, Reversi reversi) {
		if (y > 1 && x > 1) {

			// となりの駒
			String next = reversi.getBoard(y-1,x-1);

			// となりの駒が裏駒の場合
			if (next.equals(reversi.getRev_stone())) {

				// さらにその一つとなりから順に確認
				for (int i = 2; true; i++) {

					if (x - i < 0 || y - i < 0 || reversi.getBoard(y-i,x-i).equals(Reversi.EMPTY)) {
						// 駒がない場合終了
						break;
					} else if (reversi.getBoard(y-i,x-i).equals(reversi.getStone())) {
						// 自駒の場合

						// あいだの駒をすべて自駒にひっくりかえす
						for (int t = 1; t < i; t++) {
							// 配列の要素を上書き
							reversi.setBoard(y-t, x-t, reversi.getStone());
						}
						reversi.setOk_reverse(true);
						break;
					}
				}
			}
		}
	}

	static public void turnUp(int x, int y, Reversi reversi) {
		if (y > 1) {

			// となりの駒
			String next = reversi.getBoard(y - 1,x);

			// となりの駒が裏駒の場合
			if (next.equals(reversi.getRev_stone())) {

				// さらにその一つとなりから順に確認
				for (int i = 2; true; i++) {

					if (y - i < 0 || reversi.getBoard(y - i,x).equals(Reversi.EMPTY)) {
						// 駒がない場合終了
						break;
					} else if (reversi.getBoard(y - i,x).equals(reversi.getStone())) {
						// 自駒の場合

						// あいだの駒をすべて自駒にひっくりかえす
						for (int t = 1; t < i; t++) {
							// 配列の要素を上書き
							reversi.setBoard(y - t,x,reversi.getStone());
						}
						reversi.setOk_reverse(true);
						break;
					}
				}
			}
		}
	}

	static public void turnRightUp(int x, int y, Reversi reversi) {
		if (y > 1 && x < 6) {

			// となりの駒
			String next = reversi.getBoard(y - 1,x + 1);

			// となりの駒が裏駒の場合
			if (next.equals(reversi.getRev_stone())) {

				// さらにその一つとなりから順に確認
				for (int i = 2; true; i++) {

					if (x + i > 7 || y - i < 0 || reversi.getBoard(y - i,x + i).equals(Reversi.EMPTY)) {
						// 駒がない場合終了
						break;
					} else if (reversi.getBoard(y - i,x + i).equals(reversi.getStone())) {
						// 自駒の場合

						// あいだの駒をすべて自駒にひっくりかえす
						for (int t = 1; t < i; t++) {
							// 配列の要素を上書き
							reversi.setBoard(y - t,x + t,reversi.getStone());
						}
						reversi.setOk_reverse(true);
						break;
					}
				}
			}
		}
	}

	static public void turnDown(int x, int y, Reversi reversi) {
		if (y < 6) {

			// となりの駒
			String next = reversi.getBoard(y + 1,x);

			// となりの駒が裏駒の場合
			if (next.equals(reversi.getRev_stone())) {

				// さらにその一つとなりから順に確認
				for (int i = 2; true; i++) {

					if (y + i > 7 || reversi.getBoard(y + i,x).equals(Reversi.EMPTY)) {
						// 駒がない場合終了
						break;
					} else if (reversi.getBoard(y + i,x).equals(reversi.getStone())) {
						// 自駒の場合

						// あいだの駒をすべて自駒にひっくりかえす
						for (int t = 1; t < i; t++) {
							// 配列の要素を上書き
							reversi.setBoard(y + t,x,reversi.getStone());
						}
						reversi.setOk_reverse(true);
						break;
					}
				}
			}
		}
	}

	static public void turnRight(int x, int y, Reversi reversi) {
		if (x < 6) {

			// となりの駒
			String next = reversi.getBoard(y,x + 1);

			// となりの駒が裏駒の場合
			if (next.equals(reversi.getRev_stone())) {

				// さらにその一つとなりから順に確認
				for (int i = 2; true; i++) {

					if (x + i > 7 || reversi.getBoard(y,x + i).equals(Reversi.EMPTY)) {
						// 駒がない場合終了
						break;
					} else if (reversi.getBoard(y,x + i).equals(reversi.getStone())) {
						// 自駒の場合

						// あいだの駒をすべて自駒にひっくりかえす
						for (int t = 1; t < i; t++) {
							// 配列の要素を上書き
							reversi.setBoard(y,x + t,reversi.getStone());
						}
						reversi.setOk_reverse(true);
						break;
					}
				}
			}

		}
	}

	static public void turnLeftDown(int x, int y, Reversi reversi) {
		if (y < 6 && x > 1) {

			// となりの駒
			String next = reversi.getBoard(y + 1,x - 1);

			// となりの駒が裏駒の場合
			if (next.equals(reversi.getRev_stone())) {

				// さらにその一つとなりから順に確認
				for (int i = 2; true; i++) {

					if (x - i < 0 || y + i > 7 || reversi.getBoard(y + i,x - i).equals(Reversi.EMPTY)) {
						// 駒がない場合終了
						break;
					} else if (reversi.getBoard(y + i,x - i).equals(reversi.getStone())) {
						// 自駒の場合

						// あいだの駒をすべて自駒にひっくりかえす
						for (int t = 1; t < i; t++) {
							// 配列の要素を上書き
							reversi.setBoard(y + t,x - t,reversi.getStone());
						}
						reversi.setOk_reverse(true);
						break;
					}
				}
			}
		}
	}

	static public void turnLeft(int x, int y, Reversi reversi) {
		if (x > 1) {

			// となりの駒
			String next = reversi.getBoard(y,x - 1);

			// となりの駒が裏駒の場合
			if (next.equals(reversi.getRev_stone())) {

				// さらにその一つとなりから順に確認
				for (int i = 2; true; i++) {

					if (x - i < 0 || reversi.getBoard(y,x - i).equals(Reversi.EMPTY)) {
						// 駒がない場合終了
						break;
					} else if (reversi.getBoard(y,x - i).equals(reversi.getStone())) {
						// 自駒の場合

						// あいだの駒をすべて自駒にひっくりかえす
						for (int t = 1; t < i; t++) {
							// 配列の要素を上書き
							reversi.setBoard(y,x - t,reversi.getStone());
						}
						reversi.setOk_reverse(true);
						break;
					}
				}
			}
		}
	}

	static public void turnRightDown(int x, int y, Reversi reversi) {
		if (y < 6 && x < 6) {

			// となりの駒
			String next = reversi.getBoard(y + 1,x + 1);

			// となりの駒が裏駒の場合
			if (next.equals(reversi.getRev_stone())) {

				// さらにその一つとなりから順に確認
				for (int i = 2; true; i++) {

					if (x + i > 7 || y + i > 7 || reversi.getBoard(y + i,x + i).equals(Reversi.EMPTY)) {
						// 駒がない場合終了
						break;
					} else if (reversi.getBoard(y + i,x + i).equals(reversi.getStone())) {
						// 自駒の場合

						// あいだの駒をすべて自駒にひっくりかえす
						for (int t = 1; t < i; t++) {
							// 配列の要素を上書き
							reversi.setBoard(y + t,x + t,reversi.getStone());
						}
						reversi.setOk_reverse(true);
						break;
					}
				}
			}
		}
	}

}
