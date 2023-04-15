package example.tictactoe;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//https://www.youtube.com/watch?v=CCQTD7ptYqY&t=156s

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private  Button resetGame;

    private int playerONeScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;


    // player state indication
    // p1 => 0
    // p2 => 1
    // empty => 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, // winning in rows
            {0,3,6}, {1,4,7}, {2,5,8}, // winning in columns
            {0,4,8}, {2,4,6} // winning in cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for (int i = 0; i < buttons.length ; i++) {
            String buttonId = "btn_" + i;
            // get the resource as we can't get it with string
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerONeScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }

    @Override
    public void onClick(View view) {
        Button b = (Button)view;
        if(!(b.getText().toString().equals(""))){
            return;
        }

        String buttonId = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length()-1, buttonId.length()));

        if(activePlayer){
            b.setText("X");
//            b.setTextColor(Color.parseColor("#"));
            gameState[gameStatePointer] =0;
        } else {
            b.setText("O");
//            b.setTextColor(Color.parseColor("#"));
            gameState[gameStatePointer] =1;
        }

        roundCount ++;

        if(checkWinner()){
            if(activePlayer){
                playerONeScoreCount ++;
                updatePlayerScore();
                Toast.makeText(this, "Player One won!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount ++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if (roundCount == 9) {
            playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
        } else {
            activePlayer =! activePlayer;
        }

        if (playerONeScoreCount >  playerTwoScoreCount) {
            playerStatus.setText(("PLayer one is winning"));
        } else if (playerONeScoreCount <  playerTwoScoreCount) {
            playerStatus.setText(("PLayer Two is winning"));
        } else {
            playerStatus.setText(("Tied"));

        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerONeScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText((""));
                updatePlayerScore();
            }
        });
    }
    
    
    public boolean checkWinner(){
        boolean winnerResult = false;
        for (int [] winningPosition : winningPositions) {
           if( gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                   gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                   gameState[winningPosition[0]] != 2){
               winnerResult = true;
           }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerONeScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        roundCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}