package easy.ai;
import java.util.*;

public class GeneticAlgorithmTest {
  public static void main(String[] args) {
	Chromosome prototype = new TestChromosome("123456789"); //預設染色體原型
    GeneticAlgorithm.run(prototype, 10, 200);
  }
}

class TestChromosome extends Chromosome { // TestChromosome 
  static Random random = new Random();
  
  static String answer = "987654321";
  String gene; // gene 自己本身的基因

  public TestChromosome(String pStr) { gene=pStr; }

  public Chromosome crossover(Chromosome spouse) {
    int cutIdx = random.nextInt(gene.length()); //隨機取一個編碼長度做交錯
    TestChromosome tspouse = (TestChromosome) spouse; // spouse伴侶基因
    // 回傳由自己&伴侶交錯的子代基因
    return new TestChromosome(gene.substring(0, cutIdx)+tspouse.gene.substring(cutIdx));
  }

  public void mutate() { //突變函數
	// 隨機挑選一個位置，並塞入隨機產生的數值，再把最後一個數字剃除
    int selIdx = random.nextInt(gene.length());
    gene = gene.substring(0,selIdx)+randomDigit()+gene.substring(selIdx+1);
  }

  public char randomDigit() {
	// 為了顯示字元，而做的轉換 (否則會顯示成ASCII數值)
	char c = (char) ('0'+(char)random.nextInt(10));  
    return c ;    
  }

  public Chromosome randomInstance() { //初代染色體產生函數
    StringBuffer randomGene = new StringBuffer();
    for (int i=0; i<answer.length(); i++) randomGene.append(randomDigit());
    return new TestChromosome(randomGene.toString());    
  }

  public double calcFitness() { //適合度函數計算(與answer的匹費程度)
    int rzFitness = 0;
    for (int i=0; i<answer.length(); i++) {
      if (answer.charAt(i) == gene.charAt(i))
        rzFitness = rzFitness + 1;
    }
    fitness = rzFitness;
    return rzFitness;
  }

 /* public Chromosome copy() {
    Chromosome rzCopy = new TestChromosome(gene);
    rzCopy.calcFitness();
    return rzCopy;
  }*/

  // 覆寫染色體的toString函式，印出染色體的適合度&基因編碼
  public String toString() { return fitness+" : "+gene; }
}