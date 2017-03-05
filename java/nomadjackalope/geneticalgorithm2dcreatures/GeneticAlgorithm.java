package nomadjackalope.geneticalgorithm2dcreatures;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by benjamin on 3/3/17.
 *
 */
public class GeneticAlgorithm {

    float mutationRate = 0.001f;
    int totalPopulation = 5000;
    Random random = new Random();

    float maxFitness = 0;
    char[] word = "fail".toCharArray();

    //Population array
    DNA[] population;
    //Mating pool ArrayList
    ArrayList<DNA> matingPool;

    public GeneticAlgorithm() {
        init();
    }

    void init() {
        //Step 1: Initialize Population
        population = new DNA[totalPopulation];
        for (int i = 0; i < population.length; i++) {
            population[i] = new DNA();
        }
    }

    DNA[] parentOptions = new DNA[9];

    DNA[] draw() {
        //Step 2: Selection

        //Step 2a: Calculate fitness.
        for (int i = 0; i < population.length; i++) {
            population[i].fitness();
            // Stop if we have the right answer
//            if(population[i].fitness > 0.9999) {
//                done = true;
//            }
            if(population[i].fitness > maxFitness) {
                maxFitness = population[i].fitness;
                word = population[i].genes;
            }
        }

        //Step 2b: Build mating pool.
        matingPool = new ArrayList<>();

        for (int i = 0; i < population.length; i++) {
            //Add each member n times according to its fitness score.
            int n = (int)(population[i].fitness * 100);
            for (int j = 0; j < n; j++) {
                matingPool.add(population[i]);
            }

            // make a top 9 list
            int k = 0;
            while(k < parentOptions.length) {
                if (parentOptions[k] != null) {
                    if (population[i].fitness > parentOptions[k].fitness) {
                        for (int m = parentOptions.length - 1; m > k + 1; m--) {
                            if (parentOptions != null) {
                                parentOptions[m] = parentOptions[m-1];
                            }
                        }
                        parentOptions[k] = population[i];
                        break;
                    }
                } else {
                    parentOptions[k] = population[i];
                }
                k++;
            }
        }


        // We let the user choose this step
        //Step 3: Reproduction
//        for (int i = 0; i < parentOptions.length; i++) {
//                parentOptions[i] = matingPool.get(random.nextInt(matingPool.size()));
//        }

//        for (int i = 0; i < population.length; i++) {
//            int a = random.nextInt(matingPool.size());
//            int b = random.nextInt(matingPool.size());
//            DNA partnerA = matingPool.get(a);
//            DNA partnerB = matingPool.get(b);
//            //Step 3a: Crossover
//            DNA child = partnerA.crossover(partnerB);
//            //Step 3b: Mutation
//            child.mutate(mutationRate);
//
//            //Note that we are overwriting the population with the new children. When draw() loops, we will perform all the same steps with the new population of children.
//            population[i] = child;
//        }

        return parentOptions;
    }

    void breed(DNA parent1, DNA parent2) {
        //Note that we are overwriting the population with the new children. When draw() loops, we will perform all the same steps with the new population of children.
        for (int i = 0; i < population.length; i++) {

            // a percent of the time we just use the standard method. otherwise we use the user's choices
            if(random.nextFloat() > 0.8f) {
                int a = random.nextInt(matingPool.size());
                int b = random.nextInt(matingPool.size());
                parent1 = matingPool.get(a);
                parent2 = matingPool.get(b);
            }

            //Step 3a: Crossover
            DNA child = parent1.crossover(parent2);
            //Step 3b: Mutation
            child.mutate(mutationRate);


            population[i] = child;
        }
    }
}
