package com.bham.pij.assignments.a2a;

public class Individual extends GAApplication{
	private double fitness = 0;
	private char[] genes;
	private double mathResult;
	
	public Individual(char[] genes) {
		this.genes = genes;
	}
	
	public void setMathResult(double mathResult) {
		this.mathResult = mathResult;
	}
	
	public double getMathResult() {
		return mathResult;
	}
	
	public void setChromosome(int index, char[] genes) {
		for(int i = index; i < genes.length; i++) {
			this.genes[i] = genes[i];
		}
	}
	
	public char[] getChromosome() {
		return genes;
	}
	
	public void setGene(char gene, int index) {
		this.genes[index] = gene;
	}
	
	public char getGene(int index) {
		return genes[index];
	}
	
	public void setFitness(double calculatedFitness) {
		fitness = calculatedFitness;	
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public String toString() {
		char[] str = getChromosome();
		String result = "";
		for(int i = 0; i < str.length; i++) {
			result += str[i];
		}
		return result;
	}
}