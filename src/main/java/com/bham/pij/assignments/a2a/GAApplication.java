package com.bham.pij.assignments.a2a;
import java.util.Random;

public class GAApplication {
	protected final static int POPULATION_SIZE = 500;
	protected final static int PARENT_SIZE = 100;
	protected final static float CROSSOVER = 0.03f;
	protected final static float MUTATION = 0.003f;
	
	Individual [] individual = new Individual[POPULATION_SIZE];
	int goalLength;
	Random rand = new Random();
	public void run() {
		mutation(individual);
		//sorting for getting parents
		sorting();
		crossover(selectingParents());
		sorting();
	}
	
	public Individual getBest() {
		return individual[0];
	}
	
	public void mutation(Individual[] individual) {
		
	}
	
	public void sorting() {
		double highestFitness = 0;
		double nextFitness = 0;
		Individual temp;
		for(int i = 0; i < POPULATION_SIZE - 1; i++) {
			for(int j = i + 1; j < POPULATION_SIZE; j++) {
				highestFitness = individual[i].getFitness();
				nextFitness = individual[j].getFitness();
				if(highestFitness < nextFitness) {
					temp = individual[j];
					individual[j] = individual[i];
					individual[i] = temp;
				}
			}
		}
	}
	
	public Individual[] selectingParents() {
		Individual[] parents = new Individual[PARENT_SIZE];
		//selecting parents
		for(int i = 0; i < PARENT_SIZE; i++) {
			parents[i] = individual[i];
		}
		
		return parents;
	}
	
	public void crossover(Individual[] parents) {
		
	}
}