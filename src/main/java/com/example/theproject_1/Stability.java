package com.example.theproject_1;

public class Stability {

    public static double F_ratio;
    public static double P_ratio;
    public static double C_ratio;
    public static double S_ratio;

    private static double[] S_P;
    private static double[] S_F;
    private static double[] C_P;
    private static double[] C_F;

    Stability(int a, int b, int c){
        C_F = new double[]{ a-b/2-c, a-b/2-c };
        C_P = new double[]{0, 0};
        S_F = new double[]{a-b/2, a-b/2};
        S_P = new double[]{a-b, a};
    }

    public static boolean checkStability(int counter_C, int counter_S, int counter_F, int counter_P){

        double F_size = counter_F;
        double P_size = counter_P;
        double C_size = counter_C;
        double S_size = counter_S;

        if (F_size == 0.0 || P_size == 0.0 || C_size == 0.0 || S_size == 0.0)
            return false;

        F_ratio = (F_size/(F_size + P_size));
        P_ratio = (P_size/(F_size + P_size));
        C_ratio = (C_size/(C_size + S_size));
        S_ratio = (S_size/(C_size + S_size));

        double Coy_gain = (C_F[0]*(F_ratio) + C_P[0]*(P_ratio));
        double Fast_gain = (S_F[0]*(F_ratio) + S_P[0]*(P_ratio));
        double Faith_gain = (C_F[1]*(C_ratio) + S_F[1]*(S_ratio));
        double Phil_gain = (C_P[1]*(C_ratio) + S_P[1]*(S_ratio));

        if ((int) (Faith_gain*100) != (int) (Phil_gain*100) || (int) (Coy_gain*100) != (int) (Fast_gain*100))
            return false;
        System.out.println("Coy percentage= " + (int) (C_ratio*100+1) + "%");
        System.out.println("Fast percentage= " + (int) (S_ratio*100) + "%");
        System.out.println("Faithful percentage= " + (int) (F_ratio*100+1) + "%");
        System.out.println("Philanderer percentage= " + (int) (P_ratio*100) + "%");
        return true;
    }
}