//package com.bham.pij.assignments.a2atests;

import com.bham.pij.assignments.a2a.BinaryMaximiser;
import com.bham.pij.assignments.a2a.GAApplication;
import com.bham.pij.assignments.a2a.Maths;
import com.bham.pij.assignments.a2a.Weasel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GATest {

    private static final int BIN_GOAL_1 = 4;
    private static final int BIN_GOAL_2 = 32;
    private static final int BIN_GOAL_3 = 400;
    
    private static final String WEASEL_GOAL_1 = "Weasel";
    private static final String WEASEL_GOAL_2 = "Methinks it is like a weasel";
    private static final String WEASEL_GOAL_3 = "And ten thousand peoploids split into small tribes, " + 
            "coveting the highest of the sterile skyscrapers, " + 
            "like packs of dogs assaulting the glass fronts of Love-Me Avenue.";
    
    private static final int MATHS_GOAL_1 = 32;
    private static final int MATHS_GOAL_2 = 1000;
    private static final int MATHS_GOAL_3 = 123456;
    
    private static final int MATHS_CHROM_LEN = 12;
    
    private static final int MAX_GENS = 2500;
    
    private static final int MAX_RUNS = 5;
    
    @Test
    void testBinaryGoal_1() {
        GAApplication gap = new BinaryMaximiser(BIN_GOAL_1);
        int gens = 0;
        double fitness = Double.MIN_VALUE;
        
        do {
            gap.run();
            gens++;
            fitness = gap.getBest().getFitness();
            System.out.println(fitness);
        }while (fitness != BIN_GOAL_1 && gens < MAX_GENS);
        
        assertTrue(gens >= 1);
        assertTrue(fitness == BIN_GOAL_1);
    }

    @Test
    void testBinaryGoal_32() {
        GAApplication gap = new BinaryMaximiser(BIN_GOAL_2);
        int gens = 0;
        double fitness = Double.MIN_VALUE;
        
        do {
            gap.run();
            gens++;
            fitness = gap.getBest().getFitness();
            System.out.println(fitness);
        }while (fitness != BIN_GOAL_2 && gens < MAX_GENS);
        
        assertTrue(gens >= 5);
        assertTrue(fitness == BIN_GOAL_2);
    }
    
    @Test
    void testBinaryGoal_400() {
        GAApplication gap = new BinaryMaximiser(BIN_GOAL_3);
        int gens = 0;
        double fitness = Double.MIN_VALUE;
        
        do {
            gap.run();
            gens++;
            fitness = gap.getBest().getFitness();
            System.out.println(fitness);
        }while (fitness != BIN_GOAL_3 && gens < MAX_GENS);
        
        assertTrue(gens >= 50);
        assertTrue(fitness == BIN_GOAL_3);
    }
    
    @Test
    void testWeaselGoal_1() {
        GAApplication gap = new Weasel(WEASEL_GOAL_1);
        int gens = 0;
        double fitness = Double.MAX_VALUE;
        String best = null;
        do {
            gap.run();
            best = gap.getBest().toString();
            gens++;
            fitness = gap.getBest().getFitness();
            System.out.println(best);
            System.out.println(gens);
        }while (!(best.equals(WEASEL_GOAL_1)) && gens < MAX_GENS);
        
        assertTrue(gens >= 3);
        assertTrue(best.equals(WEASEL_GOAL_1));
    }

    @Test
    void testWeaselGoal_2() {
        GAApplication gap = new Weasel(WEASEL_GOAL_2);
        int gens = 0;
        double fitness = Double.MAX_VALUE;
        String best = null;
        do {
            gap.run();
            best = gap.getBest().toString();
            gens++;
            fitness = gap.getBest().getFitness();
            System.out.println(best);
            System.out.println(gens);
        }while (!(best.equals(WEASEL_GOAL_2)) && gens < MAX_GENS);
        
        assertTrue(gens >= 20);
        assertTrue(best.equals(WEASEL_GOAL_2));
    }

    @Test
    void testWeaselGoal_3() {
        GAApplication gap = new Weasel(WEASEL_GOAL_3);
        int gens = 0;
        double fitness = Double.MAX_VALUE;
        String best = null;
        do {
            gap.run();
            best = gap.getBest().toString();
            gens++;
            fitness = gap.getBest().getFitness();
            System.out.println(best);
            System.out.println(gens);
        }while (!(best.equals(WEASEL_GOAL_3)) && gens < (MAX_GENS * 6));
        
        assertTrue(gens >= 50);
        assertTrue(best.equals(WEASEL_GOAL_3));
    }

    @Test
    void testMathsGoal_1() {
        int gens = 0;
        double fitness = Double.MIN_VALUE;
        String best = null;

        for (int i = 0; i < MAX_RUNS; i++) {    
            GAApplication gap = new Maths(MATHS_GOAL_1, MATHS_CHROM_LEN);
            do {
                gap.run();
                best = gap.getBest().toString();
                gens++;
                fitness = gap.getBest().getFitness();
                System.out.println(best);
                
            }while ((fitness != MATHS_GOAL_1) && gens < MAX_GENS);
            
            if (gens >= 2 && fitness == MATHS_GOAL_1) {
                assertTrue(true);
                return;
            }

            gens = 0;
            fitness = Double.MIN_VALUE;
            best = null;
        }
        
        assertTrue(false);
    }
    
    @Test
    void testMathsGoal_2() {

        int gens = 0;
        double fitness = Double.MIN_VALUE;
        String best = null;

        for (int i = 0; i < MAX_RUNS; i++) {    
            GAApplication gap = new Maths(MATHS_GOAL_2, MATHS_CHROM_LEN+2);
            do {
                gap.run();
                best = gap.getBest().toString();
                gens++;
                fitness = gap.getBest().getFitness();
                System.out.println(best);
                
            }while ((fitness != MATHS_GOAL_2) && gens < MAX_GENS);
            
            if (gens >= 5 && fitness == MATHS_GOAL_2) {
                assertTrue(true);
                return;
            }

            gens = 0;
            fitness = Double.MIN_VALUE;
            best = null;
        }
        
        assertTrue(false);
    }
    
    @Test
    void testMathsGoal_3() {
        int gens = 0;
        double fitness = Double.MIN_VALUE;
        String best = null;

        for (int i = 0; i < MAX_RUNS; i++) {    
            GAApplication gap = new Maths(MATHS_GOAL_3, MATHS_CHROM_LEN+2);
            do {
                gap.run();
                best = gap.getBest().toString();
                gens++;
                fitness = gap.getBest().getFitness();
                System.out.println(best);
                
            }while ((fitness != MATHS_GOAL_3) && gens < MAX_GENS);
            
            if (gens >= 10 && fitness == MATHS_GOAL_3) {
                assertTrue(true);
                return;
            }

            gens = 0;
            fitness = Double.MIN_VALUE;
            best = null;
        }
        
        assertTrue(false);
    }
}
