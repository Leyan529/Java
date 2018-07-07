package easy.ai;
import java.util.*;

public class GeneticAlgorithm {
  public static void run(Chromosome prototype, int size, int maxGen) {
	Population pop = new Population(); // Population族群	
    pop.initialize(prototype, size); // Population族群初始化
    for (int genIdx=0; genIdx<maxGen; genIdx++) { // 跑maxGen個子代
      pop = pop.reproduction();
      System.out.println("=============pop==============\n"+pop.toString());
    }
  }  
}

class Population extends Vector { // Population族群類別
  static Random random = new Random();

  public void initialize(Chromosome prototype, int popSize) {
	  // Population族群初始化函數
    clear(); // 清除所有族群
    for (int i=0; i<popSize; i++) { // 產生size個初代染色體，並加入族群
      Chromosome newChrom = prototype.randomInstance();
      newChrom.calcFitness();
      add(newChrom);
    }
  }
  
  public Chromosome selection() { //挑選函數(從族群裡隨機挑選欲交配的親代)
    int shoot  = random.nextInt((size()*size()) / 2);
    int select = (int) Math.floor(Math.sqrt(shoot*2));
    return (Chromosome) get(select);
  }

  public void sort() {
    TreeMap map = new TreeMap(); //建立一個TreeMap排序結構 (依key值作為排序)
    for (int i=0; i<size(); i++) {
      // 取出族群中每一對染色體，並加入排序結構
      Chromosome chromNow = (Chromosome) get(i);
      map.put(new Double(chromNow.fitness+i*0.000000000001), chromNow);
    }
    // 清除原有族群，並將排序後族群重新加入
    clear(); 
    addAll(map.values());
  }

  public Population reproduction() {
    sort();
    Population newPop = new Population();
    for (int i=0; i<size(); i++) {
       Chromosome parent1 = selection();
       Chromosome parent2 = selection();
       //產生child基因 為parent1與parent2交配產生
       Chromosome child   = parent1.crossover(parent2); 
	   double prob = random.nextFloat(); // prob 突變機率
       if (prob < 0.2) child.mutate(); //若突變機率<0.2就突變
       child.calcFitness(); //計算新產出子代的適合度
       newPop.add(child);   //將新產生的子代加入族群
    }
    return newPop;
  }

  public String toString() {
  	return super.toString().replace(',', '\n');
  }
}

abstract class Chromosome {
  public double fitness; // 染色體的適合度
  abstract public double calcFitness(); // // 計算染色體的適合度
  abstract public Chromosome crossover(Chromosome spouse); // 交錯函數
  abstract public void mutate(); // 突變函數
  abstract public Chromosome randomInstance(); // 初代染色體產生函數
  //abstract public Chromosome copy();
}