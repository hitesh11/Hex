package hack.hex.framelay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HexframesActivity extends Activity {

	int[][] board = new int[7][7];
	int move = 0, undo = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ScrollView sv = new ScrollView(this);
		// LinearLayout l1 = new LinearLayout(this);

		setContentView(R.layout.main);
	}

	ImageView v;
	int finished = 0;

	public void imgclk(View vi) {
		int position, imd;
		position = vi.getId();
		imd = vi.getId();
		position = position - 2131034113;
		lastmove = position;
		int cell1 = 0;
		int cell2 = 0;
		cell1 = position / 7;
		cell2 = position % 7;
		v = (ImageView) findViewById(imd);
		TextView tv = (TextView) findViewById(R.id.tv1);
		if (finished == 0) {
			if (board[cell1][cell2] == 0) {
				if (move % 2 == 0) {
					v.setImageResource(R.drawable.hex1);
					board[cell1][cell2] = 1;
				} else {
					v.setImageResource(R.drawable.hex2);
					board[cell1][cell2] = 2;
				}
				int player = move % 2;
				checkboard(player, cell1, cell2);
				if (undo == 0) {
					undo++;
				}
				move = move + 1;
				int p2 = move;
				p2 = move % 2;
				p2 = p2 + 1;
				tv.setText("Player " + p2 + "'s move");
				// Toast.makeText( getApplicationContext(), "Box chosen is " +
				// String.valueOf(position) + " on move " +
				// String.valueOf(move),
				// Toast.LENGTH_SHORT).show();
			}
		}
		else
			Toast.makeText(getApplicationContext(), "Game over! Restart to play again", Toast.LENGTH_SHORT).show();

	}

	int done;
	int lastmove;
	View v2;

	public void undo(View v) {
		if(finished==0){
		if (undo == 1) {
			int c1 = 0, c2 = 0;
			c1 = lastmove / 7;
			c2 = lastmove % 7;
			board[c1][c2] = 0;
			int lid = 0;
			lid = lastmove + 2131034113;
			ImageView b = (ImageView) findViewById(lid);
			b.setImageResource(R.drawable.hexagon);
			move = move - 1;
			int p2;
			p2 = move % 2;
			p2 = p2 + 1;
			TextView tv = (TextView) findViewById(R.id.tv1);
			tv.setText("Player " + p2 + "'s move");
			undo--;
		} else if (move == 0) {
			Toast.makeText(getApplicationContext(), "Game yet to begin",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Last move undoed",
					Toast.LENGTH_SHORT).show();
		}
		}
		else
			Toast.makeText(getApplicationContext(), "Game over! Restart to play again", Toast.LENGTH_SHORT).show();
	}

	public void checkboard(int player, int x, int y) {
		player = player + 1;
		int i;
		done = 0;
		if (player == 1) {
			for (i = 0; i <= 6; i = i + 1) {
				if (board[i][0] == 1)
					recurse(1, i, 0);
			}
		}
		if (player == 2) {
			for (i = 0; i <= 6; i = i + 1) {
				if (board[0][i] == 2)
					recurse(2, 0, i);
			}
		}
		/*
		 * if (done == 1) { Toast.makeText(getApplicationContext(),
		 * "Game won by player  " + String.valueOf(player),
		 * Toast.LENGTH_SHORT).show(); finishboard(player); }
		 */
	}

	public void finishboard(int player) {
		int m1, m2, l;
		for (m1 = 0; m1 <= 6; m1 = m1 + 1) {
			for (m2 = 0; m2 <= 6; m2 = m2 + 1) {
				if (board[m1][m2] == 3) {
					l = m1 * 7 + m2;
					ImageView k = (ImageView) findViewById(l + 2131034113);
					if (player == 1) {
						k.setImageResource(R.drawable.hex1win);
					} else {
						k.setImageResource(R.drawable.hex2win);
					}
				}
			}
		}
		Toast.makeText(getApplicationContext(),
				"Game won by player  " + String.valueOf(player),
				Toast.LENGTH_SHORT).show();
		/*
		 * Handler handlerTimer = new Handler(); handlerTimer.postDelayed(new
		 * Runnable(){ public void run() { // do something }}, 20000);
		 */

		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 * builder.setMessage("Player " + player + "won the match!")
		 * .setCancelable(false) .setNeutralButton("Rematch!", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int id) { dialog.cancel(); } });
		 * 
		 * @SuppressWarnings("unused") AlertDialog alert = builder.create(); new
		 * AlertDialog.Builder(this) .setTitle("Victory!") .setMessage("Player "
		 * + player + " won the match!") .setNeutralButton("Rematch!", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dlg, int sumthin) { restartFirstActivity2();
		 * } }) .show();
		 */
		TextView result = (TextView) findViewById(R.id.tv1);
		result.setText("Player " + player + " won!");
		finished = 1;
	}

	private void restartFirstActivity2() {

		Intent i = getBaseContext().getPackageManager()
				.getLaunchIntentForPackage(getBaseContext().getPackageName());

		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	public void restartFirstActivity(View v) {

		Intent i = getBaseContext().getPackageManager()
				.getLaunchIntentForPackage(getBaseContext().getPackageName());

		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	public void recurse(int player, int x, int y) {
		if (player == 1 && y == 6) {

			board[x][y] = 3;
			if (done == 0)
				finishboard(player);
			board[x][y] = player;
			done = 1;
			return;
		}
		if (player == 2 && x == 6) {
			board[x][y] = 3;
			if (done == 0)
				finishboard(player);
			board[x][y] = 3;
			done = 1;
			return;
		}

		board[x][y] = 3;
		// if (x % 2 == 0) {
		if (x - 1 >= 0 && board[x - 1][y] == player)
			recurse(player, x - 1, y);
		if (x - 1 >= 0 && y - 1 >= 0 && board[x - 1][y - 1] == player)
			recurse(player, x - 1, y - 1);
		if (y - 1 >= 0 && board[x][y - 1] == player)
			recurse(player, x, y - 1);
		if (y + 1 <= 6 && board[x][y + 1] == player)
			recurse(player, x, y + 1);
		if (x + 1 <= 6 && board[x + 1][y] == player)
			recurse(player, x + 1, y);
		if (x + 1 <= 6 && y - 1 >= 0 && board[x + 1][y - 1] == player)
			recurse(player, x + 1, y - 1);
		// }
		/*
		 * if (x % 2 == 1) { if (x - 1 >= 0 && board[x - 1][y] == player)
		 * recurse(player, x - 1, y); if (x - 1 >= 0 && y + 1 <= 6 && board[x -
		 * 1][y + 1] == player) recurse(player, x - 1, y + 1); if (y - 1 >= 0 &&
		 * board[x][y - 1] == player) recurse(player, x, y - 1); if (y + 1 <= 6
		 * && board[x][y + 1] == player) recurse(player, x, y + 1); if (x + 1 <=
		 * 6 && board[x + 1][y] == player) recurse(player, x + 1, y); if (x + 1
		 * <= 6 && y + 1 <= 6 && board[x + 1][y + 1] == player) recurse(player,
		 * x + 1, y + 1); }
		 */
		board[x][y] = player;

	}
}
