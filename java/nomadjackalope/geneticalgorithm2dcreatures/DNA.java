package nomadjackalope.geneticalgorithm2dcreatures;

import java.util.Random;

/**
 * Created by benjamin on 3/3/17.
 *
 *
 */
public class DNA {
    char[] genes;
    float fitness;
    Random random = new Random();

    static String[] targets = {"To be or not to be", "Fish sticks and custard", "I love you", "Betcha can't guess", "Honey, where are my pants?"};

    static String target = "to be or not to be";

    //Create DNA randomly.
    DNA() {
        genes = new char[target.length()];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = (char) (random.nextInt(128 + 32) - 32);
        }
    }

    //Calculate fitness.
    void fitness() {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }
        fitness = (float)(score)/target.length();
    }

    //Crossover
    DNA crossover(DNA partner) {
        DNA child = new DNA();//genes.length);
//        int midpoint = random.nextInt(genes.length);
//        for (int i = 0; i < genes.length; i++) {
//            if (i > midpoint) child.genes[i] = genes[i];
//            else              child.genes[i] = partner.genes[i];
//        }
        int half = 0;
        for (int i = 0; i < genes.length; i++) {
            // 1st parent
            if(random.nextFloat() > 0.5f && half < genes.length / 2) {
                half++;
                child.genes[i] = genes[i];
            } else {
                child.genes[i] = partner.genes[i];
            }
        }
        return child;
    }

    //Mutation
    void mutate(float mutationRate) {
        for (int i = 0; i < genes.length; i++) {
            if (random.nextFloat() < mutationRate) {
                genes[i] = (char) (random.nextInt(128 + 32) - 32);
            }
        }
    }

    //Convert to String—PHENOTYPE.
//    String getPhrase() {
//        return new String(genes);
//    }

}