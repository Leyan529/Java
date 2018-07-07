package easy.ai;
import java.util.*;

public class GeneticAlgorithmTest {
  public static void main(String[] args) {
	Chromosome prototype = new TestChromosome("123456789"); //�w�]�V����쫬
    GeneticAlgorithm.run(prototype, 10, 200);
  }
}

class TestChromosome extends Chromosome { // TestChromosome 
  static Random random = new Random();
  
  static String answer = "987654321";
  String gene; // gene �ۤv��������]

  public TestChromosome(String pStr) { gene=pStr; }

  public Chromosome crossover(Chromosome spouse) {
    int cutIdx = random.nextInt(gene.length()); //�H�����@�ӽs�X���װ����
    TestChromosome tspouse = (TestChromosome) spouse; // spouse��Q��]
    // �^�ǥѦۤv&��Q������l�N��]
    return new TestChromosome(gene.substring(0, cutIdx)+tspouse.gene.substring(cutIdx));
  }

  public void mutate() { //���ܨ��
	// �H���D��@�Ӧ�m�A�ö�J�H�����ͪ��ƭȡA�A��̫�@�ӼƦr�c��
    int selIdx = random.nextInt(gene.length());
    gene = gene.substring(0,selIdx)+randomDigit()+gene.substring(selIdx+1);
  }

  public char randomDigit() {
	// ���F��ܦr���A�Ӱ����ഫ (�_�h�|��ܦ�ASCII�ƭ�)
	char c = (char) ('0'+(char)random.nextInt(10));  
    return c ;    
  }

  public Chromosome randomInstance() { //��N�V���鲣�ͨ��
    StringBuffer randomGene = new StringBuffer();
    for (int i=0; i<answer.length(); i++) randomGene.append(randomDigit());
    return new TestChromosome(randomGene.toString());    
  }

  public double calcFitness() { //�A�X�ר�ƭp��(�Panswer���ǶO�{��)
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

  // �мg�V���骺toString�禡�A�L�X�V���骺�A�X��&��]�s�X
  public String toString() { return fitness+" : "+gene; }
}