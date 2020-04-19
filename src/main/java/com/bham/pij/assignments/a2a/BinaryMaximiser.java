package com.bham.pij.assignments.a2a;

import java.util.ArrayList;

public class BinaryMaximiser extends GAApplication {
	char[] goal;
	Individual[] parents = new Individual[PARENT_SIZE];
	public void run() {
		super.run();
	}
	
	public BinaryMaximiser(int BINARY_GOAL) {
		setGoalLength(BINARY_GOAL);
		setGoal();
		char[] chromosome = new char[goalLength];
		for(int i = 0; i < POPULATION_SIZE; i++) {
			chromosome = generateChromosome();
			individual[i] = new Individual(chromosome);
			individual[i].setFitness(calcFitness(individual[i]));
			
		}
		for(int j = 0; j < PARENT_SIZE; j++) {
			chromosome = generateChromosome();
			parents[j] = new Individual(chromosome);
		}
	}
	
	public void mutation(Individual[] individual) {
		super.mutation(individual);
		char gene;
		for(int i = 0; i < POPULATION_SIZE; i++) {
			for(int point = 0; point < goalLength; point++) {
				if(rand.nextDouble() <= MUTATION) {
					gene = individual[i].getGene(point);
					if(gene == '1') {
						individual[i].setGene('0', point);
						individual[i].setFitness(calcFitness(individual[i]));
					}
					else if(gene == '0') {
						individual[i].setGene('1', point);
						individual[i].setFitness(calcFitness(individual[i]));
					}
				}
			}
		}
	}
	
	public void crossover(Individual[] parents) {
		Individual[] offsets = new Individual[PARENT_SIZE];
		ArrayList<Individual> parentList = new ArrayList<Individual>();
		ArrayList<Individual> offsetList = new ArrayList<Individual>();
		
		//shifting array to arraylist
		for(int i = 0; i < parents.length; i++) {
			parentList.add(parents[i]);
		}
		while(parentList.size() != 0) {
			int index1 = rand.nextInt(parentList.size());
			int index2 = rand.nextInt(parentList.size());
			char[] chromosome1 = new char[goalLength];
			char[] chromosome2 = new char[goalLength];
			if(index1 < index2) {
				int tempNum = index1;
				index1 = index2;
				index2 = tempNum;
			}
			
			if(index1 == index2) {
				continue;
			}
			parentList.remove(index1);
			parentList.remove(index2);
			
			Individual indiv1 = new Individual(chromosome1);
			Individual indiv2 = new Individual(chromosome2);
			if(rand.nextDouble() <= CROSSOVER) {
				int point = rand.nextInt(goalLength);
				for(int j = 0; j < goalLength; j++) {
					if(j < point) {
						indiv1.setGene(parents[index1].getGene(j), j);
						indiv2.setGene(parents[index2].getGene(j), j);
					}
					else {
						indiv1.setGene(parents[index1].getGene(j), j);
						indiv2.setGene(parents[index2].getGene(j), j);
					}
				}
			}
			else {
				for(int j = 0; j < goalLength; j++) {
					indiv1.setGene(parents[index1].getGene(j), j);
					indiv2.setGene(parents[index2].getGene(j), j);
				}
			}
			offsetList.add(indiv1);
			offsetList.add(indiv2);
		}
		offsets = offsetList.toArray(offsets);
		
		for(int k = 0; k < PARENT_SIZE; k++) {
			offsets[k].setFitness(calcFitness(offsets[k]));
			this.individual[individual.length - 1 - k] = offsets[k];
		}
	}
	
	public double calcFitness(Individual individual) {
		double fitness = 0;
		for (int i = 0; i < goalLength; i++) {
			if (goal[i] == individual.getGene(i)) {
				fitness++;
			}
		}
		return fitness;
	}
	
	public void setGoal() {
		for(int i = 0; i < goalLength; i++) {
			goal[i] = '1';
		}
	}
	
	public void setGoalLength(int BINARY_GOAL) {
		goalLength = BINARY_GOAL;
		goal = new char[goalLength];
	}
	
	public char[] getGoal() {
		return goal;
	}
	
	public char[] generateChromosome() {
		char[] genes = new char [goalLength];
		for (int i = 0; i < goalLength; i++) {
			char gene = (char) ('0' + rand.nextInt(2));
			genes[i] = gene;
		}
		return genes;
	}
}

