package com.example.pruben.myapplication;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public int[][] getBoard(){
        int[][] board = new int[9][9];
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                StringBuilder sb = new StringBuilder();
                sb.append("cell");
                sb.append(i+1);
                sb.append(j+1);
                String id_name = sb.toString();
                int id = getResources().getIdentifier(id_name, "id", getPackageName());
                EditText editText = findViewById(id);
                if (editText.getText().equals(null) || editText.getText().length()==0){
                    board[i][j] = 0;
                    editText.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    board[i][j] = Integer.parseInt(editText.getText().toString());
                    editText.setTextColor(Color.parseColor("#000000"));
                }
            }
        }
        return board;
    }

    public boolean checkUnique(int[] vals){
        boolean[] boolArray = new boolean[10];
        for (int i=0; i<10; i++){
            boolArray[i] = false;
        }
        for (int i=0; i<9; i++){
            if (vals[i] != 0){
                if (boolArray[vals[i]]){
                    return false;
                } else {
                    boolArray[vals[i]] = true;
                }
            }
        }
        return true;
    }

    public boolean rowValid(int[][] board, int row){
        int[] vals = board[row];
        return checkUnique(vals);
    }

    public boolean colValid(int[][] board, int col){
        int[] vals = new int[9];
        for (int i=0; i<9; i++){
            vals[i] = board[i][col];
        }
        return checkUnique(vals);
    }

    public boolean blockValid(int[][] board, int row, int col){
        int[] vals = new int[9];
        int count = 0;
        int upper_row = 3 * (int) Math.floor((float) row / 3);
        int left_col = 3 * (int) Math.floor((float) col/ 3);

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                vals[count] = board[upper_row + i][left_col + j];
                count++;
            }
        }
        return checkUnique(vals);
    }

    public boolean isValid(int[][] board, int row, int col){
        if (rowValid(board, row) && colValid(board, col) && blockValid(board, row, col)){
            return true;
        } else {
            return false;
        }
    }

    public int[] pos2rowCol(int pos){
        int[] res = new int[2];
        res[0] = (int) Math.floor((float) pos / 9);
        res[1] = pos % 9;
        return res;
    }

    public boolean solve_sudoku(int[][] board, int pos){
        if (pos == 81){
            return true;
        }
        int[] rowCol = pos2rowCol(pos);
        int row = rowCol[0];
        int col = rowCol[1];
        if (board[row][col] != 0){
            return solve_sudoku(board, pos+1);
        } else {
            for (int i=1; i<10; i++){
                board[row][col] = i;
                StringBuilder sb = new StringBuilder();
                sb.append("cell");
                sb.append(row+1);
                sb.append(col+1);
                String id_name = sb.toString();
                int id = getResources().getIdentifier(id_name, "id", getPackageName());
                EditText editText = findViewById(id);
                editText.setText(Integer.toString(i));
                if (isValid(board, row, col)){
                    if (solve_sudoku(board, pos+1)){
                        return true;
                    }
                }
            }
            board[row][col] = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("cell");
            sb.append(row+1);
            sb.append(col+1);
            String id_name = sb.toString();
            int id = getResources().getIdentifier(id_name, "id", getPackageName());
            EditText editText = findViewById(id);
            editText.setText("");
            return false;
        }
    }

    public void runAll(View v){
        int[][] board = getBoard();
        solve_sudoku(board, 0);
    }

    public void clearAll(View v){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                StringBuilder sb = new StringBuilder();
                sb.append("cell");
                sb.append(i+1);
                sb.append(j+1);
                String id_name = sb.toString();
                int id = getResources().getIdentifier(id_name, "id", getPackageName());
                EditText editText = findViewById(id);
                editText.setText("");
                editText.setTextColor(Color.parseColor("#000000"));
            }
        }
    }

    public void clearSolved(View v){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                StringBuilder sb = new StringBuilder();
                sb.append("cell");
                sb.append(i+1);
                sb.append(j+1);
                String id_name = sb.toString();
                int id = getResources().getIdentifier(id_name, "id", getPackageName());
                EditText editText = findViewById(id);
                System.out.print(editText.getTextColors());
                if (editText.getCurrentTextColor() == Color.parseColor("#000000")){

                } else {
                    editText.setText("");
                    editText.setTextColor(Color.parseColor("#000000"));
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
