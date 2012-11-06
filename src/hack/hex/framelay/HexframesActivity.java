//currentid = 

package hack.hex.framelay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HexframesActivity extends Activity {

	int[][] board = new int[7][7];
	int move = 1, undo = 0;

	node state;
	
	int cur;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ScrollView sv = new ScrollView(this);
		// LinearLayout l1 = new LinearLayout(this);

		setContentView(R.layout.main);
		ImageView va;
		va = (ImageView) findViewById(2131034113+30);
		va.setImageResource(R.drawable.hex1);
		board[4][2] = 2;
		pm=30;
		move=1;
		cur=0;
		gp("--");
	}

	ImageView v;
	int finished = 0,xf,yf;
	
	@SuppressLint("NewApi")
	public boolean gp(String ip){
		
		int RED = 0;
		int BLUE = 1;
		int BLANK = 2;
		
		String[] parts = ip.split("-",-1);
		
		Log.d("df",": " + parts[RED]);
		Log.d("df",": " + parts[BLUE]);
		Log.d("df",": " + parts[BLANK]);
		
		String[] ptsr = parts[RED].split(",");
		if (! ptsr[0].isEmpty()){
		for (String spt : ptsr){
			int pt = Integer.valueOf(spt);
			if (board[pt/7][pt%7] != 2){
				return false;
			}
		}
		}
		
		String[] ptsb = parts[BLUE].split(",");
		if (! ptsb[0].isEmpty()){
		for (String spt : ptsb){
			int pt = Integer.valueOf(spt);
			if (board[pt/7][pt%7] != 1){
				return false;
			}
		}
		}
		
		String[] pts = parts[BLANK].split(",");
		if (pts[0].equalsIgnoreCase("*"))
		{
			
			int[] a = new int[49];
			for(int i=0;i<49;i++){
				a[i] = i;
			}
			for(String s : ptsr){
				a[Integer.valueOf(s)] = 0;
			}
			for(String s : ptsb){
				a[Integer.valueOf(s)] = 0;
			}
			for(int s : a){
				if (board[s/7][s%7] != 0){
					return false;
				}
			}
			return true;
		}
		
		if (! pts[0].isEmpty()){
		for (String spt : pts){
			int pt = Integer.valueOf(spt);
			if (board[pt/7][pt%7] != 0){
				return false;
			}
		}
		}
		return true;
		
	}
	
	public boolean twos(int i, int j){
	
		if(((j<3 && i+j<6)||(j>3 && i+j>6)) && !(i==3 && j==2)) return true;
		else 
			return false;
	
	}
	public boolean ones(int i, int j){
		
		if(i+j>=6 && i>=4 && j<=3 && !(i==4 && j==2))
			return true;
		else
		return false;
		
	}
	public boolean threes(int i, int j){
		
		if(i<=2 && j>=3 && i+j<=6 && !(i==2 && j==3))
			return true;
		return false;
		
	}
	
	int pm;
	
	public void makemove(int x){
		ImageView v;
		pm=x;
		v = (ImageView) findViewById(x+2131034113);
		v.setImageResource(R.drawable.hex1);
		board[x/7][x%7] = 2;
		
	}
	
	boolean upperrow=false, lowerrow=false;
	public int virtualfiller(){
		
		for(int i=0;i<7;i++){
			for(int j=0;j<7;j++){
				if(board[i][j]==2){
				if(i-2>=0 && j+1<7 && board[i-2][j+1]==2){
					if(board[i-1][j]==1 && board[i-1][j+1]==0){
						makemove(7*(i-1)+(j+1));
						return 1;
					}
					else if (board[i-1][j+1]==1 && board[i-1][j]==0 ){
						makemove(7*(i-1)+j);
						return 1;
					}
				}
				
				if(i-1>=0 && j-1>=0 && board[i-1][j-1]==2){
					if(board[i-1][j]==1 && board[i][j-1]==0){
						makemove(7*(i)+(j-1));
						return 1;
					}
					else if (board[i][j-1]==1 && board[i-1][j]==0 ){
						makemove(7*(i-1)+j);
						return 1;
					}
				}
				
				if(inbound(i-1,j+2) && board[i-1][j+2]==2 && !(i==4 && j==2)){
					if(board[i-1][j+1]==1 && board[i][j+1]==0){
						makemove(7*(i)+(j+1));
						return 1;
					}
					else if (board[i][j+1]==1 && board[i-1][j+1]==0 ){
						makemove(7*(i-1)+j+1);
						return 1;
					}
				}
				
				
				/*if(i+1<7 && j-2>=0 && board[i+1][j-2]==2){
					if(board[i][j-1]==1 && board[i+1][j-1]==0){
						makemove(7*(i+1)+(j-1));
						return 1;
					}
					else if (board[i+1][j-1]==1 && board[i][j-1]==0 ){
						makemove(7*(i)+j-1);
						return 1;
					}
				}*/
				
				if(i==1 && j<6){
					if(board[i-1][j]==1 && board[i-1][j+1]==0 && upperrow==false){
						makemove(7*(i-1)+(j+1));
						upperrow=true;
						return 1;
					}
					else if (board[i-1][j+1]==1 && board[i-1][j]==0 && upperrow==false){
						makemove(7*(i-1)+j);
						upperrow=true;
						return 1;
					}
				}
				
				if(i==5 && j>0){
					if(board[i+1][j]==1 && board[i+1][j-1]==0 && lowerrow==false){
						makemove(7*(i+1)+(j-1));
						lowerrow=true;
						return 1;
					}
					else if (board[i+1][j-1]==1 && board[i+1][j]==0 && lowerrow==false){
						makemove(7*(i+1)+j);
						lowerrow=true;
						return 1;
					}
				}
				}
				
				
				
			}
		}
		return 0;
	}

	boolean uc = false;
	boolean omg = false;
	int masterfunction(int id, int inp){
	
		
		if (id==0){
			
			
			if (twos(inp/7, inp%7) && isfree(17)){
				//output
				makemove(17);
				return 1;
			}
			else if(inp == 23 && isfree(24)){
				makemove(24);
				return 2;
			}
			else if(inp == 24 && isfree(23)){
				makemove(23);
				return 3;
			}
			else if (inp ==17 && isfree(18)){
				makemove(18);
				omg = true;
				return 1;
			}
			else if (inp !=31 && inp!=37 && inp!=36 && inp!=38 && inp/7>=4){
				makemove(38);
				return 1;
			}
			else if (inp!=36 && inp!=37 && inp/7>=4){
				makemove(43);
				return 1;
			}
			
			
		}
		
		if(id==2 || id==3){
			if(twos(inp/7, inp%7)  && isfree(17)){
				makemove(17);
				return 1;  			// 4 ki jagah 1
			}
			if(inp==17){
				omg=true;
			}
		}
		
		
		if(id==1){
			//hardcoded moves
			if (gp("18,25,23,30,43-16,17,24,38-31")){
				makemove(31);
				return 1;
			}
			//end
			if (inp==17 && isfree(18)){
				makemove(18);
				return 1;
			}
			/*if (board[2][3]==0 && isfree(10) && isfree(11) && inp!=36 && inp!=37){
				makemove(17);
				return 1;
			}*/
			if (gp("--17,10,11") && inp!=36 && inp!=37){
				makemove(17);
				return 1;
			}
			else if (board[3][2]==2 && board[3][3]==1 && board[2][2]==1 && board[2][3]==1 && board[2][4]==2 && isfree(25)){
				makemove(25);
				return 1;
			}
			else if (board[2][3]==1 && board[3][1]==1 && isfree(24)){
				makemove(24);
				return 1;
			}
			else if(!ones(inp/7,inp%7) && board[2][3]!=1){
				
				if(inp==4 || inp==10  && isfree(12)){
					
					makemove(12);
					uc=true;
					return 1;
				}
				else if(inp==11 && isfree(10)){
					makemove(10);
					return 1;
				}
				else if (  isfree(4) && isfree(10) && isfree(11)){
					makemove(4);
					uc=true;
					return 1;
				}
				//
				else if (inp !=31 && inp!=37 && inp!=36 && inp!=38 && inp/7>=6){
					makemove(38);
					return 1;
				}
				else if (inp!=36 && inp!=37 && inp/7>=6){
					makemove(43);
					return 1;
				}
				//
			}	
				else if (omg == false){
					
					if((inp==36 || inp==43)  && isfree(38)){
						makemove(38);
						return 1;
					}
					else if(inp==37  && isfree(36)){
						makemove(36);
						return 1;
					}
					else if (  isfree(43) && inp/7>=5 && (inp%7) <=3){
						makemove(43);
						return 1;
					}
					
				}
					
					
	}
		
		
		
		//diagonal move
		int a, b;
		a=inp/7;
		b=inp%7;
		for (int i=0;i<7;i++){
			for(int j=0;j<7;j++){
				if (board[i][j] == 2){
					if ((a == i+2 && b == j-1) || (i>=4 && j==4)){
						if (inbound(i+1, j-2) && isfree((i+1)*7+j-2) && isfree(i*7+j-1) && isfree((i+1)*7 + j-1) ){
							Log.d("moves", "diag move");
							makemove((i+1)*7+j-2);
							return 1;
						}
						if (inbound(i+1, j+1) && isfree((i+1)*7+j+1) && isfree(i*7+j+1) && isfree((i+1)*7 + j) ){
							Log.d("moves", "diag move");
							makemove((i+1)*7+j+1);
							return 1;
						}
					}
					
					/*if (i-2==0 && inbound(i-2,j+1) && isfree((i-2)*7 + (j+1)) && isfree((i-1)*7 + (j)) && isfree((i-1)*7 + (j+1)) )
					{
						Log.d("moves", "diag move");
						makemove((i-2)*7 + (j+1));
						return 1;
					}*/
				}
				
				
			}
		}
		
		//try to create lower virtual connection
		if (isfree(43) && board[5][1]==0 && board[5][2]==0 && uc && inp>35){
			makemove(43);
			Log.d("move", "create lower vc");
			return 1;
		} else if (isfree(38) && board[5][3]==1 && board[4][3]==0 && board[5][2]==0 && uc && inp>35){
			makemove(38);
			Log.d("move", "close lower vc");
			return 1;
		}
		
		
		////
		//try closing opponents virtual connections
				for(int i=0;i<7;i++){
					for(int j=0;j<7;j++){
						if (board[i][j]==1 && inbound(i-1,j-1) && board[i-1][j-1]==1 ){
							if (isfree((i-1)*7+j) && board[i][j-1]==2)
							{
								makemove((i-1)*7+j);
								Log.d("move", "close opp vc");
								return 1;	
							} else if (isfree((i)*7+j-1) && board[i-1][j]==2){
								makemove((i)*7+j-1);
								Log.d("move", "close opp vc");
								return 1;
							}
							
						}
						
						if (board[i][j]==1 && inbound(i-1,j+2) && board[i-1][j+2]==1 ){
							if (isfree((i-1)*7+j+1) && board[i][j]==2)
							{
								makemove((i-1)*7+j+1);
								Log.d("move", "close opp vc");
								return 1;	
							} else if (isfree((i)*7+j) && board[i-1][j+1]==2){
								makemove((i)*7+j+1);
								Log.d("move", "close opp vc");
								return 1;
							}
							
						}
						
						if (board[i][j]==1 && inbound(i-2,j+1) && board[i-2][j+1]==1 ){
							if (isfree((i-1)*7+j+1) && board[i-1][j]==2)
							{
								makemove((i-1)*7+j+1);
								Log.d("move", "close opp vc");
								return 1;	
							} else if (isfree((i-1)*7+j) && board[i-1][j+1]==2){
								makemove((i-1)*7+j);
								Log.d("move", "close opp vc");
								return 1;
							}
							
						}
						
					}
				}
		////
		
			//try curl
		int mv = curl(inp);
		if (mv>=0){
			makemove(mv);
			Log.d("move", "curl");
			return 1;
		}		
				
		//if nothing happens try to fill virtual connections
		
				
		for (int i = 0; i<7; i++){
			for (int j = 0; j<7; j++){
				if (board[i][j]==2 && inbound(i-1,j-1) && board[i-1][j-1]==2 && board[i-1][j]==0 && board[i][j-1]==0 ){
					makemove((i-1)*7+j);
					Log.d("move", "close my vc");
					return 1;
				}
				
				/*if (board[i][j]==2 && inbound(i-1,j-1) && board[i-1][j-1]==2 && board[i-1][j]==0 && board[i][j-1]==0 ){
					makemove((i-1)*7+j);
					return id;
				}*/
				
				if (board[i][j]==2 && inbound(i-1,j+2) && board[i-1][j+2]==2 && board[i-1][j+1]==0 && board[i][j+1]==0 ){
					makemove((i-1)*7+j+1);
					Log.d("move", "close my vc");
					return 1;
				}
				
				if (board[i][j]==2 && inbound(i-2,j+1) && board[i-2][j+1]==2 && board[i-1][j]==0 && board[i-1][j+1]==0 ){
					makemove((i-1)*7+j);
					Log.d("move", "close my vc");
					return 1;
				}
			}
		}
		
		/*for (int i=0;i<6;i++){
			if (board[1][i]==2 && isfree(i) && isfree(i+1)){
				makemove(i);
				Log.d("moves", "close my vc");
				return 1;
			}
			if (board[6][i]==2 && isfree(i) && isfree(i+1)){
				makemove(i);
				Log.d("moves", "close my vc");
				return 1;
			}
		}*/
		
		
		
		//if nothing happens try to block opponent
		int x, y;
		x = inp/7;
		y=inp%7;
		
		
		
		
		
		
		
		//omg over conc on bottom part
		omg = false;
		
		for (int i=0;i<6;i++){
			if (board[1][i]==2 && isfree(i) && isfree(i+1)){
				makemove(i);
				Log.d("moves", "close my vc");
				return 1;
			}
			if (board[5][i]==2 && isfree(i) && isfree(i+1)){
				makemove(i);
				Log.d("moves", "close my vc");
				return 1;
			}
		}
		
		//if nothing else happens move in one direction
		//int x, y;
		Random randomGenerator = new Random();
		
		for (int i = -1; i<2; i++){
			for (int j=-1; j<2 ;j++){
				if (!(i==0 || (i==-1 && j==-1) || (i==1 && j==1)) && isfree((pm/7 + i)*7+(j+(pm%7)))){
					
					//if (board[i+pm/7][(pm%7) + (j+1)%2] == 1)
					{makemove(7*(i+pm/7)+j+(pm%7));
					Log.d("move", "around " +Integer.toString(i) + " " + Integer.toString(j));
					return 1;}
				}
			}
		}
		
		
		
		//if nothing else happens make random move
		Log.d("move", "Random");
		do{
			x=randomGenerator.nextInt(7);
			y=randomGenerator.nextInt(7);
		} while (board[x][y] !=0);
		makemove(7*x+y);
		return 1;

		
		
	}
		
	public boolean inbound(int x, int y){
		if (x>=0 && x <7 && y>=0 && y<7) return true;
		return false;
	}
	
	public boolean isfree(int pos){
		
		if ( inbound(pos/7, pos%7) && board[pos/7][pos%7] ==0) return true;
		return false;
	}
	
	public int curl(int inp){
		int x =inp/7;
		int y = inp%7;
		
		if ( inbound(x-1,y) && board[x-1][y]==2 || inbound(x+1,y-1) && board [x+1][y-1] ==2 ){
			if (isfree(x*7 + y-1)) return x*7 + y-1;
		}
		
		if ( inbound(x-1,y+1) && board[x-1][y+1]==2 || inbound(x+1,y) && board [x+1][y] ==2 ){
			if (isfree(x*7 + y+1)) return x*7 + y+1;
		}
		
		return -1;
		
	}



	
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

					v.setImageResource(R.drawable.hex2);
					board[cell1][cell2] = 1;
					checkboard(0,cell1,cell2);
			//	} else {
					
				if(virtualfiller()==0)
				cur = masterfunction(cur, position);
				else
					Log.d("move", "virtualfiller");
					
					
					
				}
				checkboard(1, cell1, cell2);
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
						k.setImageResource(R.drawable.hex2win);
					} else {
						k.setImageResource(R.drawable.hex1win);
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
		if (x - 1 >= 0 && y + 1 <= 6 && board[x - 1][y + 1] == player)
			recurse(player, x - 1, y + 1);
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
