package com.bham.pij.assignments.a2a;

import java.util.ArrayList;

public class Maths extends GAApplication{
	int goal;
	int chromLength;
	char[] characters = "+-*/0123456789".toCharArray();
	final double invalid = Double.MIN_VALUE;
	
	public Maths(int goal, int chromLength) {
		setGoal(goal);
		setChromLength(chromLength);
		for(int i = 0; i < POPULATION_SIZE; i++) {
			char[] chromosome = generateChromosome();
			individual[i] = new Individual(chromosome);
			individual[i].setFitness(calcFitness(individual[i]));
		}
	}
	
	public void run() {
		fitnessInitialize(individual);
		super.run();
		convertFitness(individual);
	}
	
	public void mutation(Individual[] individual) {
		super.mutation(individual);
		char gene;
		char newGene;
		for(int i = 0; i < POPULATION_SIZE; i++) {
			for(int point = 0; point < chromLength; point++) {
				if(rand.nextDouble() < MUTATION) {
					gene = individual[i].getGene(point);
					int index = 0;
					for(int j = 0; j < characters.length; j++) {
						if(gene == characters[j]) {
							index = j;
						}
					}
					
					double prob = rand.nextDouble();
					if(prob <= 0.5) {
						index++;
						if(index >= characters.length) {
							index = 0;
						}
						
						newGene = characters[index];
						individual[i].setGene(newGene, point);
						individual[i].setFitness(calcFitness(individual[i]));
					}
					else {
						index--;
						if(index < 0) {
							index = characters.length - 1;
						}
						newGene = characters[index];
						individual[i].setGene(newGene, point);
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
				int point = rand.nextInt(chromLength);
				for(int j = 0; j < chromLength; j++) {
					if(j < point) {
						indiv1.setGene(parentList.get(index1).getGene(j), j);
						indiv2.setGene(parentList.get(index2).getGene(j), j);
					}
					else {
						indiv1.setGene(parentList.get(index1).getGene(j), j);
						indiv2.setGene(parentList.get(index1).getGene(j), j);
					}
				}
			}
			else {
				for(int j = 0; j < chromLength; j++) {
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
	
	public void convertFitness(Individual[] individual) {
		
		for(int i = 0; i < POPULATION_SIZE; i++) {
			double result = individual[i].getMathResult();
			individual[i].setFitness(result);
		}
	}
	
	public void fitnessInitialize(Individual[] individual) {
		for(int i = 0; i < POPULATION_SIZE; i++) {
			individual[i].setFitness(calcFitness(individual[i]));
		}
	}
	
	public double calcFitness(Individual individual) {
		ArrayList<Character> operators = new ArrayList<Character>();
		ArrayList<String> numbers = new ArrayList<String>();
		double fitness = 0;
		double result = 0;
		String number = "";
		
		for(int i = 0; i < chromLength; i++) {
			char gene = individual.getGene(i);
			if(isOperator(gene)) {
				if(number == "" || i == chromLength - 1) {
					return invalid;
				}
				else {
					numbers.add(number);
					operators.add(gene);
					number = "";
				}
			}
			else {
				number += Character.toString(gene);
				if(i == chromLength - 1)
					numbers.add(number);
			}
		}
		
		if(operators.size() >= numbers.size()) {
			return invalid;
		}
		else {
			try {
				if(operators.size() == 0) {
					fitness = Integer.parseInt(numbers.get(0));
				}
				result = calculation(operators, numbers);
				individual.setMathResult(result);
				
				//fitness is expressed in the percentage which means how close to the goal
				if(result > goal) {
					fitness = goal / result;
				}
				else {
					fitness = result / goal;
				}
				
				return fitness;
			}
			catch(NumberFormatException e){
				return Integer.MIN_VALUE;
			}
		}
	}
	
	public double calculation(ArrayList<Character> operators, ArrayList<String> numbers) {
		char operator;
		int firstValue;
		int secondValue;
		int result;
		for(int i = 0; i < operators.size(); i++) {
			operator = operators.get(i);
			switch(operator) {
			case '+':
				firstValue = Integer.parseInt(numbers.get(0));
				secondValue = Integer.parseInt(numbers.get(1));
				result = firstValue + secondValue;
				numbers.set(0, Integer.toString(result));
				numbers.remove(1);
				break;
			
			case '-':
				firstValue = Integer.parseInt(numbers.get(0));
				secondValue = Integer.parseInt(numbers.get(1));
				result = firstValue - secondValue;
				numbers.set(0, Integer.toString(result));
				numbers.remove(1);
				break;
				
			case '*':
				firstValue = Integer.parseInt(numbers.get(0));
				secondValue = Integer.parseInt(numbers.get(1));
				result = firstValue * secondValue;
				numbers.set(0, Integer.toString(result));
				numbers.remove(1);
				break;
				
			case '/':
				try {
					firstValue = Integer.parseInt(numbers.get(0));
					secondValue = Integer.parseInt(numbers.get(1));
					result = firstValue / secondValue;
					numbers.set(0, Integer.toString(result));
					numbers.remove(1);
					break;
				}
				catch(ArithmeticException e) {
					return Integer.MAX_VALUE;
				}
			}
		}
		
		return Integer.parseInt(numbers.get(0));
	}
	
	public boolean isOperator(char gene) {
		if(gene == '+' || gene == '-' || gene == '*' || gene == '/') {
			return true;
		}
		else
			return false;
	}
	
	public void setGoal(int goal) {
		this.goal = goal;
	}
	
	public void setChromLength(int chromLength) {
		this.chromLength = chromLength;
	}
	
	public int getGoal() {
		return goal;
	}
	
	public char[] generateChromosome() {
		char[] genes = new char [chromLength];
		for (int i = 0; i < chromLength; i++) {
			int index = rand.nextInt(characters.length);
			char gene = characters[index];
			genes[i] = gene;
		}
		return genes;
	}
}
