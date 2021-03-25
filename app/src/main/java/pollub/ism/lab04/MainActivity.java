package pollub.ism.lab04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.content.Context;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    public String[][] wartosci = {{"a","b","c"},{"d","e","f"},{"g","h","i"}}; // litery alfabetu - tablica jest pusta
    private static String[][] KEY ={{"KEY11","KEY12","KEY13"},{"KEY21","KEY22","KEY23"},{"KEY31","KEY32","KEY33"}};
    public String KEY_ZNAK="Widocznosc przycisku";
    public String KEY_KROK="krok";
    public int wygrana=0;
    public TableRow wiersz1=null, wiersz2=null, wiersz3=null;
    int krok=0; // zmienna liczaca ile ruchow zostalo wykonanych, jezeli w 9 krokach nikt nie wygral tzn ze jest remis
    public String znak="X";
    private Button restart=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wiersz1 = (TableRow) findViewById(R.id.wiersz1) ;
        wiersz2 = (TableRow) findViewById(R.id.wiersz2) ;
        wiersz3 = (TableRow) findViewById(R.id.wiersz3) ;
        restart=findViewById(R.id.restart);
        restart.setVisibility(View.INVISIBLE);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        if(wygrana==1) block();

    }
    public void kliknieciePrzycisku(View view) {

            String nazwa = view.getResources().getResourceEntryName(view.getId());
            Button przycisk = (Button) findViewById(view.getId());
            if(wygrana==1) block();
            if (przycisk.getText() != "X" && przycisk.getText() != "O"&&wygrana!=1) {
                przycisk.setText(znak);
                krok++;
                int x = Integer.parseInt(nazwa.substring(7, 8)) - 1;
                int y= Integer.parseInt(nazwa.substring(9, 10)) - 1;
                wartosci[x][y] = znak;
                wygrana(znak);
                if (znak == "X") znak = "O";
                else znak = "X";
            }

        if(krok==9) {
            Toast.makeText(this,"Remis", Toast.LENGTH_LONG).show();
            restart.setVisibility(View.VISIBLE);
        }
        }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_KROK, krok);
        outState.putInt("wygrana", wygrana);
        for(int i=0;i<3;i++)
        for(int j=0;j<3;j++) {
            System.out.println("zapisywane wartosci " + wartosci[i][j]);
            outState.putString(KEY[i][j], wartosci[i][j].toString());
        }
        if(restart.getVisibility()== View.VISIBLE) outState.putInt(KEY_ZNAK,1);
        }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for(int i=0;i<3;i++)
       for(int j=0;j<3;j++) {
           wartosci[i][j] = savedInstanceState.getString(KEY[i][j]);
           System.out.println("wartosc "+wartosci[i][j]);
           String x,y,nazwa;
           int a=i+1, b=j+1;
           x=String.valueOf(a);
           y=String.valueOf(b);
           nazwa="button_"+x+"_"+y;
           int id= getResources().getIdentifier(nazwa, "id", getPackageName());
           if(wartosci[i][j]=="X"||wartosci[i][j]=="O")
           {
               Button klawisz= findViewById(id);
               klawisz.setText(wartosci[i][j]);
           }
       }
        if(savedInstanceState.getInt(KEY_ZNAK)==1)
            restart.setVisibility(View.VISIBLE);
        krok=savedInstanceState.getInt(KEY_KROK);
        wygrana=savedInstanceState.getInt("wygrana");
    }
    public void wygrana(String znak)
    {
        if(wartosci[0][0]==wartosci[0][1]&&wartosci[0][1]==wartosci[0][2]&&wartosci[0][2]==znak)  {Toast.makeText(this,"Wygrały "+znak, Toast.LENGTH_LONG).show(); wygrana =1;}
        if(wartosci[1][0]==wartosci[1][1]&&wartosci[1][1]==wartosci[1][2]&&wartosci[1][2]==znak)  {Toast.makeText(this,"Wygrały "+znak, Toast.LENGTH_LONG).show(); wygrana =1;}
        if(wartosci[2][0]==wartosci[2][1]&&wartosci[2][1]==wartosci[2][2]&&wartosci[2][2]==znak)  {Toast.makeText(this,"Wygrały "+znak, Toast.LENGTH_LONG).show(); wygrana =1;}

        if(wartosci[0][0]==wartosci[1][0]&&wartosci[1][0]==wartosci[2][0]&&wartosci[2][0]==znak)  {Toast.makeText(this,"Wygrały "+znak, Toast.LENGTH_LONG).show(); wygrana =1;}
        if(wartosci[0][1]==wartosci[1][1]&&wartosci[1][1]==wartosci[2][1]&&wartosci[2][1]==znak)  {Toast.makeText(this,"Wygrały "+znak, Toast.LENGTH_LONG).show(); wygrana =1;}
        if(wartosci[0][2]==wartosci[1][2]&&wartosci[1][2]==wartosci[2][2]&&wartosci[2][2]==znak)  {Toast.makeText(this,"Wygrały "+znak, Toast.LENGTH_LONG).show(); wygrana =1;}

        if(wartosci[0][0]==wartosci[1][1]&&wartosci[1][1]==wartosci[2][2]&&wartosci[2][2]==znak)  {Toast.makeText(this,"Wygrały "+znak, Toast.LENGTH_LONG).show(); wygrana =1;}
        if(wartosci[0][2]==wartosci[1][1]&&wartosci[1][1]==wartosci[2][0]&&wartosci[0][2]==znak)  {Toast.makeText(this,"Wygrały "+znak, Toast.LENGTH_LONG).show(); wygrana =1;}

        if(wygrana==1) {
            block();
        }

    }
    public void block()
    {
        for(int i=0;i<wiersz1.getChildCount();i++) {
            View v = wiersz1.getChildAt(i);
            if(v instanceof Button) v.setClickable(false);
        }
        for(int i=0;i<wiersz2.getChildCount();i++) {
            View v = wiersz2.getChildAt(i);
            if(v instanceof Button) v.setClickable(false);
        }
        for(int i=0;i<wiersz3.getChildCount();i++) {
            View v = wiersz3.getChildAt(i);
            if(v instanceof Button) v.setClickable(false);
        }
        restart.setVisibility(View.VISIBLE);
    }
}