package com.bham.pij.assignments.a2a;

import java.util.ArrayList;

public class Weasel extends GAApplication{
	private final int ASCIIMAX = 126;
	private final int ASCIIMIN = 32;
	char[] goal;
	Individual[] parents = new Individual[PARENT_SIZE];
	
	public void run() {
		super.run();
	}
	
	public Weasel(String goal) {
		setGoalLength(goal);
		this.goal = new char[goalLength];
		setGoal(goal);
		for(int i = 0; i < POPULATION_SIZE; i++) {
			char[] chromosome = new char[goalLength];
			chromosome = generateChromosome();
			individual[i] = new Individual(chromosome);
			individual[i].setFitness(calcFitness(individual[i]));	
		}
		for(int j = 0; j < PARENT_SIZE; j++) {
			parents[j] = new Individual(generateChromosome());
		}
	}
	
	public void mutation(Individual[] individual) {
		//String genes = "";
		super.mutation(individual);
		char gene;
		char newGene;
		for(int i = 0; i < POPULATION_SIZE; i++) {
			for(int point = 0; point < goalLength; point++) {
				if(rand.nextDouble() < MUTATION) {
					gene = individual[i].getGene(point);
					double prob = rand.nextDouble();
					if(prob <= 0.5) {
						if(gene + 1 > ASCIIMAX) {
							newGene = (char) ASCIIMIN;
						}
						else {
							newGene = (char) (gene + 1);
							individual[i].setGene(newGene, point);
							individual[i].setFitness(calcFitness(individual[i]));
						}
					}
					else if(prob > 0.5) {
						if(gene - 1 < ASCIIMIN) {
							newGene = (char) ASCIIMAX;
						}
						else {
							newGene = (char) (gene - 1);
							individual[i].setGene(newGene, point);
							individual[i].setFitness(calcFitness(individual[i]));
						}	
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
			if(index1 < index2) {
				int tempNum = index1;
				index1 = index2;
				index2 = tempNum;
			}
			
			if(index1 == index2) {
				continue;
			}
			
			Individual indiv1 = new Individual(generateChromosome());
			Individual indiv2 = new Individual(generateChromosome());
			if(rand.nextDouble() <= CROSSOVER) {
				int point = rand.nextInt(goalLength);
				for(int j = 0; j < goalLength; j++) {
					if(j < point) {
						indiv1.setGene(parentList.get(index1).getGene(j), j);
						indiv2.setGene(parentList.get(index2).getGene(j), j);
					}
					else {
						indiv1.setGene(parentList.get(index1).getGene(j), j);
						indiv2.setGene(parentList.get(index2).getGene(j), j);
					}
				}
			}
			else {
				for(int j = 0; j < goalLength; j++) {
					indiv1.setGene(parentList.get(index1).getGene(j), j);
					indiv2.setGene(parentList.get(index2).getGene(j), j);
				}
			parentList.remove(index1);
			parentList.remove(index2);	
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
		int gap = 0;
		double distance = 0;
		int goalFitness = 0;
		for(int j = 0; j < goalLength; j++) {
			goalFitness += (int) goal[j];
		}
		for(int i = 0; i < goalLength; i++) {
			gap = Math.abs(goal[i] - individual.getGene(i));
			distance += gap;
		}
		distance = goalFitness - distance;
		
		return Math.pow(distance / goalFitness, 100);
	}
	
	public void setGoal(String goal) {
		for(int i = 0; i < goalLength; i++) {
			this.goal[i] = goal.charAt(i);
		}
	}
	
	public void setGoalLength(String goal) {
		goalLength = goal.length();
	}
	
	public char[] getGoal() {
		return goal;
	}
	
	public char[] generateChromosome() {
		char[] genes = new char [goalLength];
		for (int i = 0; i < goalLength; i++) {
			char gene = (char) (' ' + rand.nextInt(95));
			genes[i] = gene;
		}
		return genes;
	}
}
