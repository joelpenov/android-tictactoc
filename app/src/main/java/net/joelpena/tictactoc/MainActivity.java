package net.joelpena.tictactoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int [] playedPieces;
    private int [][] winningCombinations;
    private Piece currentPieceType;
    private boolean gameIsOver;
    private int numberOfPlays = 0;

    public MainActivity(){
        playedPieces = new int[9];
        winningCombinations =
                new int[][]{{0,1,2}, {3,4,5}, {6,7,8},{0,4,8},{2,4,6},{0,3,6},{1,4,7},{2,5,8}};
        resetStates();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playPiece(View piece){

        if(gameIsOver) return;

        ImageView currentPiece = (ImageView) piece;
        int currentPieceKey = Integer.parseInt(currentPiece.getTag().toString());
        boolean isAlreadyPlayed = ArrayHelper.thisPositionWasPlayed(playedPieces, currentPieceKey);

        if(isAlreadyPlayed) return;

        numberOfPlays += 1;

        playedPieces[currentPieceKey] = currentPieceType == Piece.YELLOW ?
                Piece.YELLOW.getValue() : Piece.RED.getValue();

        boolean someoneWin = isAWinningPlay(winningCombinations, currentPieceType.getValue());
        if(someoneWin){
            finishGame(currentPieceType.toString() + " wins the game!");
        }

        displayCurrentPiece(currentPiece);

        if(numberOfPlays == 9 && !someoneWin){
            finishGame("It's a draw!");
        }

    }

    public void restartGame(){
        resetStates();
        hideMenu();
        clearImagesBackground();
    }

    public void resetStates(){
        currentPieceType = Piece.RED;
        gameIsOver = false;
        resetPlays();
        gameIsOver = false;
        numberOfPlays = 0;
    }

    private void clearImagesBackground(){
        LinearLayout  board = (LinearLayout) findViewById(R.id.board);
        for (int i = 0 ; i < board.getChildCount(); i++){
            LinearLayout currentLayout = (LinearLayout) board.getChildAt(i);
            for (int j = 0 ; j < currentLayout.getChildCount(); j++){
                ((ImageView) currentLayout.getChildAt(j)).setImageResource(0);
            }

        }
    }

    private void finishGame(String message){
        gameIsOver = true;
        TextView winnerLabel = (TextView) findViewById(R.id.winnerLabel);
        winnerLabel.setText(message);
        showMenu();
    }

    public void restartGameButton(View button){
        restartGame();
    }

    private void setMenuVisibility(int value){
        LinearLayout menu = (LinearLayout) findViewById(R.id.menu);
        menu.setVisibility(value);
    }

    private void hideMenu(){
        setMenuVisibility(View.GONE);
    }

    private void showMenu(){
        setMenuVisibility(View.VISIBLE);
    }

    private void displayCurrentPiece(ImageView currentPiece){
        int backgroundDrawable = R.drawable.redx;
        if(currentPieceType == Piece.YELLOW) {
            backgroundDrawable = R.drawable.yellowx;
            currentPieceType = Piece.RED;
        }else {
            currentPieceType = Piece.YELLOW;
        }

        float newScale = 0.9f;
        currentPiece.setImageResource(backgroundDrawable);
        currentPiece.animate().scaleX(newScale).scaleY(newScale).setDuration(200);
    }

    private boolean isAWinningPlay(int [][] combinations, int key) {
        for (int [] winningCombination : combinations) {
            boolean isInArray = true;
            for (int position : winningCombination){
                if(!(playedPieces[position] == key)) isInArray = false;
            }

            if(isInArray) return true;
        }

        return false;
    }

    private void resetPlays(){
        for (int i = 0; i < playedPieces.length; i++)
            playedPieces[i] = -1;
    }
}
