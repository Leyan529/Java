package smulateFire;

public class smulateFireAlgorithmTest {
	public static final int T = 100;// 初始化溫度
    public static final double Tmin = 1e-8;// 溫度的下界
    public static final int k = 100;// 反覆運算的次數
    public static final double delta = 0.98;// 溫度的下降率
    
    
    

	public static void main(String[] args) {
		int init_y = 0;
		smulateFireAlgorithm s = new smulateFireAlgorithm();		
		System.out.println("最優解為：" + s.run(init_y));
	}
}


