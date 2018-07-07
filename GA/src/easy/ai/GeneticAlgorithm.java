package easy.ai;
import java.util.*;

public class GeneticAlgorithm {
  public static void run(Chromosome prototype, int size, int maxGen) {
	Population pop = new Population(); // Population�ڸs	
    pop.initialize(prototype, size); // Population�ڸs��l��
    for (int genIdx=0; genIdx<maxGen; genIdx++) { // �]maxGen�Ӥl�N
      pop = pop.reproduction();
      System.out.println("=============pop==============\n"+pop.toString());
    }
  }  
}

class Population extends Vector { // Population�ڸs���O
  static Random random = new Random();

  public void initialize(Chromosome prototype, int popSize) {
	  // Population�ڸs��l�ƨ��
    clear(); // �M���Ҧ��ڸs
    for (int i=0; i<popSize; i++) { // ����size�Ӫ�N�V����A�å[�J�ڸs
      Chromosome newChrom = prototype.randomInstance();
      newChrom.calcFitness();
      add(newChrom);
    }
  }
  
  public Chromosome selection() { //�D����(�q�ڸs���H���D�����t���˥N)
    int shoot  = random.nextInt((size()*size()) / 2);
    int select = (int) Math.floor(Math.sqrt(shoot*2));
    return (Chromosome) get(select);
  }

  public void sort() {
    TreeMap map = new TreeMap(); //�إߤ@��TreeMap�Ƨǵ��c (��key�ȧ@���Ƨ�)
    for (int i=0; i<size(); i++) {
      // ���X�ڸs���C�@��V����A�å[�J�Ƨǵ��c
      Chromosome chromNow = (Chromosome) get(i);
      map.put(new Double(chromNow.fitness+i*0.000000000001), chromNow);
    }
    // �M���즳�ڸs�A�ñN�Ƨǫ�ڸs���s�[�J
    clear(); 
    addAll(map.values());
  }

  public Population reproduction() {
    sort();
    Population newPop = new Population();
    for (int i=0; i<size(); i++) {
       Chromosome parent1 = selection();
       Chromosome parent2 = selection();
       //����child��] ��parent1�Pparent2��t����
       Chromosome child   = parent1.crossover(parent2); 
	   double prob = random.nextFloat(); // prob ���ܾ��v
       if (prob < 0.2) child.mutate(); //�Y���ܾ��v<0.2�N����
       child.calcFitness(); //�p��s���X�l�N���A�X��
       newPop.add(child);   //�N�s���ͪ��l�N�[�J�ڸs
    }
    return newPop;
  }

  public String toString() {
  	return super.toString().replace(',', '\n');
  }
}

abstract class Chromosome {
  public double fitness; // �V���骺�A�X��
  abstract public double calcFitness(); // // �p��V���骺�A�X��
  abstract public Chromosome crossover(Chromosome spouse); // ������
  abstract public void mutate(); // ���ܨ��
  abstract public Chromosome randomInstance(); // ��N�V���鲣�ͨ��
  //abstract public Chromosome copy();
}