package br.com.solimar.desafiojogodavelha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int[] idsBoard = new int[]{ R.id.botao1, R.id.botao2, R.id.botao3, R.id.botao4,
            R.id.botao5, R.id.botao6, R.id.botao7, R.id.botao8, R.id.botao9 };
    private static final String PLAYER = "O";
    private static final String BOT = "X";
    private static final String EMPTY = "";
    private static final String DRAW = "EMPATES";
    private boolean isGameOver = false;
    private Integer moves = 0;
    private String turn;
    private Button btnNewGame;
    private TextView tvPlayer, tvBot, tvDraw;
    Map<String,Integer> mapPlacar = new HashMap<>();

    private static final int [][] winMatrix = new int[][]{
            {1,2,3},
            {4,5,6},
            {7,8,9},
            {1,4,7},
            {2,5,8},
            {3,6,9},
            {1,5,9},
            {3,5,7}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    private void initialize() {
        btnNewGame = (Button)findViewById(R.id.btnNewGame);
        tvDraw = (TextView) findViewById(R.id.tvDraw);
        tvBot = (TextView) findViewById(R.id.tvBot);
        tvPlayer = (TextView) findViewById(R.id.tvPlayer);
        mapPlacar.put(PLAYER,0);
        mapPlacar.put(BOT,0);
        mapPlacar.put(DRAW,0);
        atualizaPlacar();
    }

    private void addListeners() {
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn = PLAYER;
                isGameOver = false;
                moves = 0;
                clearBorad();
            }
        });

        for(int idTabuleiro: idsBoard){
            getButtonBoard(idTabuleiro).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isGameOver && moves <9)
                        moveHuman(v);
                }
            });

        }
    }

    private boolean moveHuman(View v) {
        if(turn == null || turn.equals(PLAYER)){
            if(((Button) v).getText().equals(EMPTY)) {
                ((Button) v).setText(PLAYER);
                turn = BOT;
                moves++;
                checkGameOver();
                moveBot();
            }else{
                Toast.makeText(GameActivity.this,R.string.campo_marcado,Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            Toast.makeText(GameActivity.this,R.string.aguarde_sua_vez,Toast.LENGTH_LONG).show();
        }
        return false;
    }


    private void moveBot() {
        if(!isGameOver && moves <9) {
            Random mRnd = new Random();
            while (true) {
                int index = mRnd.nextInt(9);
                Button btn = getButtonBoard(idsBoard[index]);
                if (btn.getText().equals(EMPTY)) {
                    btn.setText(BOT);
                    turn = PLAYER;
                    moves++;
                    checkGameOver();
                    break;
                }
            }
        }
    }

    private void checkGameOver(){

        for(int i =0; i<=7; ++i) {
            String move1 = getButtonBoard(idsBoard[winMatrix[i][0]-1]).getText().toString();
            String move2 = getButtonBoard(idsBoard[winMatrix[i][1]-1]).getText().toString();
            String move3 = getButtonBoard(idsBoard[winMatrix[i][2]-1]).getText().toString();

            if(!move1.equals(EMPTY) && !move2.equals(EMPTY) && !move3.equals(EMPTY)){
                if(move1.equals(move2)&& move2.equals(move3)){
                    isGameOver = true;
                    Toast.makeText(GameActivity.this,getString(R.string.jogador_venceu,move1),Toast.LENGTH_LONG).show();
                    mapPlacar.put(move1,mapPlacar.get(move1)+1);
                    atualizaPlacar();
                }
            }

        }

        if(moves >=9 && !isGameOver){
            mapPlacar.put(DRAW,mapPlacar.get(DRAW)+1);
            atualizaPlacar();
            Toast.makeText(GameActivity.this,R.string.jogo_empatado,Toast.LENGTH_LONG).show();
        }
    }

    private void atualizaPlacar() {
        tvBot.setText(getString(R.string.placar_bot,mapPlacar.get(BOT)));
        tvDraw.setText(getString(R.string.placar_draw,mapPlacar.get(DRAW)));
        tvPlayer.setText(getString(R.string.placar_player,mapPlacar.get(PLAYER)));
    }

    private Button getButtonBoard(int id){
        return (Button) findViewById(id);
    }

    private void clearBorad(){
        for(int idTabuleiro: idsBoard){
            getButtonBoard(idTabuleiro).setText(EMPTY);
        }
    }
}
