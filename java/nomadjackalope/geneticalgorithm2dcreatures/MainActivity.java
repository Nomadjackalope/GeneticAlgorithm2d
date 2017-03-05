package nomadjackalope.geneticalgorithm2dcreatures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView output;
    TextView turnlabel;
    ParentButton[] buttons = new ParentButton[9];
    DNA parent1;
    GeneticAlgorithm myGA = new GeneticAlgorithm();
    DNA[] dna;

    int turn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.win).setVisibility(View.GONE);

        output = (TextView) findViewById(R.id.output);
        turnlabel = (TextView) findViewById(R.id.turn);

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new ParentButton();
        }

        buttons[0].button = (Button) findViewById(R.id.button);
        buttons[1].button = (Button) findViewById(R.id.button2);
        buttons[2].button = (Button) findViewById(R.id.button3);
        buttons[3].button = (Button) findViewById(R.id.button4);
        buttons[4].button = (Button) findViewById(R.id.button5);
        buttons[5].button = (Button) findViewById(R.id.button6);
        buttons[6].button = (Button) findViewById(R.id.button7);
        buttons[7].button = (Button) findViewById(R.id.button8);
        buttons[8].button = (Button) findViewById(R.id.button9);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonView = (Button) v;
                for (int i = 0; i < buttons.length; i++) {
                    if(buttonView.equals(buttons[i].button)) {
                        if(parent1 == null) {
                            parent1 = buttons[i].dna;
                        } else {
                            myGA.breed(parent1, buttons[i].dna);
                            runGAOnce();
                            parent1 = null;
                        }
                    }
                }
            }
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].button.setOnClickListener(clickListener);
        }

        reset();


    }

    public void runGAOnce() {
        myGA.draw();
        output.setText(myGA.word, 0, myGA.word.length);
        turnlabel.setText(String.valueOf(turn));
        turn++;

        dna = myGA.draw();

        for (int i = 0; i < dna.length; i++) {
            buttons[i].dna = dna[i];
            buttons[i].button.setText(String.valueOf(buttons[i].dna.genes));
        }

        if(String.valueOf(myGA.word).equals(DNA.target)) {
            findViewById(R.id.win).setVisibility(View.VISIBLE);
            findViewById(R.id.win).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.GONE);
                }
            });
        }
    }

    public void reset() {
        turn = 1;
        Random random = new Random();
        DNA.target = DNA.targets[random.nextInt(5)];
        System.out.println("MA| target: " + DNA.target);
        myGA = new GeneticAlgorithm();
        dna = myGA.draw();

        for (int i = 0; i < dna.length; i++) {
            buttons[i].dna = dna[i];
            buttons[i].button.setText(String.valueOf(buttons[i].dna.genes));
        }

        output.setText(myGA.word, 0, myGA.word.length);
        turnlabel.setText(String.valueOf(turn));
        turn++;
    }
}
