package kostka;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import sun.security.acl.WorldGroupImpl;


public class Data extends JFrame {
   
  
int [] reseni;


  boolean h = false;


String s;

    JPanel Graphicpanel,controlpanel,settingspanel,commandpanel;     
   
    JButton random, solve,  setc;
    JButton [] controlbutt,colorbutt,colorselect;
    JPanel [] cbp,cmp;
        
    int SWITCH;
    public int[][][][] kostka;
    int[][] rotace1, rotace2;
    boolean set, konec;

    public Data() {
        
        reseni=new int [1000];
      
      s =" ";
        
        create();
        
        
       set();
       randomizer();
        solver();
        
    }

    // ZÁKLADNÍ FUNKCE & PARAMETRY 
    private void create() {
        SWITCH=1;
        kostka = new int[10][5][4][3];
        rotace1 = new int[3][3];
        rotace2 = new int[4][3];
        konec = false;
         for (int i = 0; i < 1000; i++) {
          reseni[i]=0;  }
       
      for(int m=0;m<10;m++){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i < 3) {
            kostka[m][0][i][j] = 1;
            kostka[m][1][i][j] = 2 + i;
            kostka[m][2][i][j] = 2 + i;
            kostka[m][3][i][j] = 2 + i;
            kostka[m][4][i][j] = 6;

             } else {

              kostka[m][1][i][j] = 2 + i;
              kostka[m][2][i][j] = 2 + i;
              kostka[m][3][i][j] = 2 + i;
                    }
                }
            }
      }
      tisk();
    }
    private String tisk() {
        String t;
        t=""+"\n";
    t+="            "+kostka[SWITCH][3][0][0]+"  "+kostka[SWITCH][3][0][1]+"  "+kostka[SWITCH][3][0][2]+"\n";
    t+="            "+kostka[SWITCH][2][0][0]+"  "+kostka[SWITCH][2][0][1]+"  "+kostka[SWITCH][2][0][2]+"\n";
    t+="            "+kostka[SWITCH][1][0][0]+"  "+kostka[SWITCH][1][0][1]+"  "+kostka[SWITCH][1][0][2]+"\n\n";
    
    t+=kostka[SWITCH][3][3][2]+"  "+kostka[SWITCH][2][3][2]+"  "+kostka[SWITCH][1][3][2]+"     ";
    t+=kostka[SWITCH][0][2][0]+"  "+kostka[SWITCH][0][2][1]+"  "+kostka[SWITCH][0][2][2]+"    ";
    t+=kostka[SWITCH][1][1][0]+"  "+kostka[SWITCH][2][1][0]+"  "+kostka[SWITCH][3][1][0]+"      ";
    t+=kostka[SWITCH][4][2][0]+"  "+kostka[SWITCH][4][2][1]+"  "+kostka[SWITCH][4][2][2]+" \n";
    
    t+=kostka[SWITCH][3][3][1]+"  "+kostka[SWITCH][2][3][1]+"  "+kostka[SWITCH][1][3][1]+"     ";
    t+=kostka[SWITCH][0][1][0]+"  "+kostka[SWITCH][0][1][1]+"  "+kostka[SWITCH][0][1][2]+"    ";
    t+=kostka[SWITCH][1][1][1]+"  "+kostka[SWITCH][2][1][1]+"  "+kostka[SWITCH][3][1][1]+"      ";
    t+=kostka[SWITCH][4][1][0]+"  "+kostka[SWITCH][4][1][1]+"  "+kostka[SWITCH][4][1][2]+" \n";
    
    t+=kostka[SWITCH][3][3][0]+"  "+kostka[SWITCH][2][3][0]+"  "+kostka[SWITCH][1][3][0]+"     ";
    t+=kostka[SWITCH][0][0][0]+"  "+kostka[SWITCH][0][0][1]+"  "+kostka[SWITCH][0][0][2]+"    ";
    t+=kostka[SWITCH][1][1][2]+"  "+kostka[SWITCH][2][1][2]+"  "+kostka[SWITCH][3][1][2]+"      ";
    t+=kostka[SWITCH][4][0][0]+"  "+kostka[SWITCH][4][0][1]+"  "+kostka[SWITCH][4][0][2]+" \n\n";
            
    t+="            "+kostka[SWITCH][1][2][2]+"  "+kostka[SWITCH][1][2][1]+"  "+kostka[SWITCH][1][2][0]+"\n";
    t+="            "+kostka[SWITCH][2][2][2]+"  "+kostka[SWITCH][2][2][1]+"  "+kostka[SWITCH][2][2][0]+"\n";
    t+="            "+kostka[SWITCH][3][2][2]+"  "+kostka[SWITCH][3][2][1]+"  "+kostka[SWITCH][3][2][0]+"\n";
        System.out.println(t);        

       return t;
            }  
    private void clear() {
       
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 3) {
                    rotace2[i][j] = 0;

                } else {
                    rotace1[i][j] = 0;
                    rotace2[i][j] = 0;
                }
            }
        }
   
    }
    private void clearall() {
        System.out.println("clear all");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    kostka[SWITCH][i][j][k] = 0;

                }
            }

        }
        System.out.println("all cleared");
    }

    // VLASTNÍ ROTACE
    private void rotace1l() {
        int[][] a;
        a = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = rotace1[i][j];
            }
        }
        rotace1[0][0] = a[0][2];
        rotace1[0][1] = a[1][2];
        rotace1[0][2] = a[2][2];
        rotace1[1][0] = a[0][1];
        rotace1[1][2] = a[2][1];
        rotace1[2][0] = a[0][0];
        rotace1[2][1] = a[1][0];
        rotace1[2][2] = a[2][0];

    }
    private void rotace1r() {

        int[][] a;
        a = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = rotace1[i][j];
            }
        }
        rotace1[0][0] = a[2][0];
        rotace1[0][1] = a[1][0];
        rotace1[0][2] = a[0][0];
        rotace1[1][0] = a[2][1];
        rotace1[1][2] = a[0][1];
        rotace1[2][0] = a[2][2];
        rotace1[2][1] = a[1][2];
        rotace1[2][2] = a[0][2];

    }
    private void rotace2l() {
        int[][] a;
        a = new int[4][3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = rotace2[i][j];
            }
        }
        for (int j = 0; j < 3; j++) {
            rotace2[0][j] = a[3][j];
            rotace2[1][j] = a[0][j];
            rotace2[2][j] = a[1][j];
            rotace2[3][j] = a[2][j];
        }
    }
    private void rotace2r() {

        int[][] a;
        a = new int[4][3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = rotace2[i][j];
            }

        }
        for (int j = 0; j < 3; j++) {
            rotace2[0][j] = a[1][j];
            rotace2[1][j] = a[2][j];
            rotace2[2][j] = a[3][j];
            rotace2[3][j] = a[0][j];
        }
    }

    // DEFINICE ROTACÍ 1. ČÁST
    private void r_A_B_C_D() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 3) {
                    rotace2[i][j] = kostka[SWITCH][1][i][j];

                } else {
                    rotace2[i][j] = kostka[SWITCH][1][i][j];
                    rotace1[i][j] = kostka[SWITCH][0][i][j];
                }
            }

        }
    }
    private void w_A_B_C_D() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 3) {
                    kostka[SWITCH][1][i][j] = rotace2[i][j];
                    colorbutt[ts_FIELD_to_INT(GETFIELD(1, i, j))].setBackground(ts_INT_to_COL(kostka[SWITCH][1][i][j]));
                } else {
                    kostka[SWITCH][1][i][j] = rotace2[i][j];
                    kostka[SWITCH][0][i][j] = rotace1[i][j];
                    colorbutt[ts_FIELD_to_INT(GETFIELD(1, i, j))].setBackground(ts_INT_to_COL(kostka[SWITCH][1][i][j]));
                    colorbutt[ts_FIELD_to_INT(GETFIELD(0, i, j))].setBackground(ts_INT_to_COL(kostka[SWITCH][0][i][j]));
                }
            }

        }
    }
    private void r_AE_BF_CG_DH() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                rotace2[i][j] = kostka[SWITCH][2][i][j];
            }
        }
    }
    private void w_AE_BF_CG_DH() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                kostka[SWITCH][2][i][j] = rotace2[i][j];
                colorbutt[ts_FIELD_to_INT(GETFIELD(2, i, j))].setBackground(ts_INT_to_COL(kostka[SWITCH][2][i][j]));
            }
        }
    }
    private void r_E_F_G_H() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 3) {
                    rotace2[i][j] = kostka[SWITCH][3][i][j];

                } else {
                    rotace2[i][j] = kostka[SWITCH][3][i][j];
                    rotace1[i][j] = kostka[SWITCH][4][i][j];
                }
            }

        }
    }
    private void w_E_F_G_H() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 3) {
                    kostka[SWITCH][3][i][j] = rotace2[i][j];
                    colorbutt[ts_FIELD_to_INT(GETFIELD(3, i, j))].setBackground(ts_INT_to_COL(kostka[SWITCH][3][i][j]));
                } else {
                    kostka[SWITCH][3][i][j] = rotace2[i][j];
                    kostka[SWITCH][4][i][j] = rotace1[i][j];
                    colorbutt[ts_FIELD_to_INT(GETFIELD(3, i, j))].setBackground(ts_INT_to_COL(kostka[SWITCH][3][i][j]));
                    colorbutt[ts_FIELD_to_INT(GETFIELD(4, i, j))].setBackground(ts_INT_to_COL(kostka[SWITCH][4][i][j]));
                }
            }

        }
    }

    // DEFINICE ROTACÍ 2.ČÁST
    private void r_A_D_H_E() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotace1[i][j] = kostka[SWITCH][i + 1][3][j];
            }

        }
        for (int i = 0; i < 3; i++) {

            rotace2[0][i] = kostka[SWITCH][0][i][0];
            rotace2[1][i] = kostka[SWITCH][i + 1][0][0];
            rotace2[2][i] = kostka[SWITCH][4][2 - i][0];
            rotace2[3][i] = kostka[SWITCH][3 - i][2][2];
        }
    }
    private void w_A_D_H_E() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                kostka[SWITCH][i + 1][3][j] = rotace1[i][j];
                colorbutt[ts_FIELD_to_INT(GETFIELD(1+i, 3, j))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i][3][j]));
            }
        }
        for (int i = 0; i < 3; i++) {

            kostka[SWITCH][0][i][0] = rotace2[0][i];
            kostka[SWITCH][i + 1][0][0] = rotace2[1][i];
            kostka[SWITCH][4][2 - i][0] = rotace2[2][i];
            kostka[SWITCH][3 - i][2][2] = rotace2[3][i];
            colorbutt[ts_FIELD_to_INT(GETFIELD(  0,  i,0))].setBackground(ts_INT_to_COL(kostka[SWITCH][0  ][i  ][0]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(1+i,  0,0))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i][0  ][0]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(  4,2-i,0))].setBackground(ts_INT_to_COL(kostka[SWITCH][4  ][2-i][0]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(3-i,  2,2))].setBackground(ts_INT_to_COL(kostka[SWITCH][3-i][2  ][2]));
        }
    }
    private void r_AB_CD_GH_EF() {
        for (int i = 0; i < 3; i++) {

            rotace2[0][i] = kostka[SWITCH][0][i][1];
            rotace2[1][i] = kostka[SWITCH][i + 1][0][1];
            rotace2[2][i] = kostka[SWITCH][4][2 - i][1];
            rotace2[3][i] = kostka[SWITCH][3 - i][2][1];
        }
    }
    private void w_AB_CD_GH_EF() {
        for (int i = 0; i < 3; i++) {

            kostka[SWITCH][0][i][1] =   rotace2[0][i];
            kostka[SWITCH][i+1][0][1] = rotace2[1][i];
            kostka[SWITCH][4][2-i][1] = rotace2[2][i];
            kostka[SWITCH][3-i][2][1] = rotace2[3][i];
            colorbutt[ts_FIELD_to_INT(GETFIELD(  0,  i,1))].setBackground(ts_INT_to_COL(kostka[SWITCH][0  ][i  ][1]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(1+i,  0,1))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i][0  ][1]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(  4,2-i,1))].setBackground(ts_INT_to_COL(kostka[SWITCH][4  ][2-i][1]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(3-i,  2,1))].setBackground(ts_INT_to_COL(kostka[SWITCH][3-i][2  ][1]));
        }
    }
    private void r_B_C_G_F() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                rotace1[i][j] = kostka[SWITCH][1 + i][1][2 - j];
            }

        }
        for (int i = 0; i < 3; i++) {
           
            rotace2[0][i] = kostka[SWITCH][0][i][2];
            rotace2[1][i] = kostka[SWITCH][1+i][0][2];
            rotace2[2][i] = kostka[SWITCH][4][2 - i][2];
            rotace2[3][i] = kostka[SWITCH][3 - i][2][0];
        }
    }
    private void w_B_C_G_F() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                kostka[SWITCH][i + 1][1][2 - j] = rotace1[i][j];
                colorbutt[ts_FIELD_to_INT(GETFIELD(  1+i,  1,2-j))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i ][1  ][2-j]));
            }
        }
        for (int i = 0; i < 3; i++) {

            kostka[SWITCH][0][i][2] = rotace2[0][i];
            kostka[SWITCH][1 + i][0][2] = rotace2[1][i];
            kostka[SWITCH][4][2 - i][2] = rotace2[2][i];
            kostka[SWITCH][3 - i][2][0] = rotace2[3][i];
            colorbutt[ts_FIELD_to_INT(GETFIELD(  0,  i,2))].setBackground(ts_INT_to_COL(kostka[SWITCH][0  ][i  ][2]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(1+i,  0,2))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i][0  ][2]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(  4,2-i,2))].setBackground(ts_INT_to_COL(kostka[SWITCH][4  ][2-i][2]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(3-i,  2,0))].setBackground(ts_INT_to_COL(kostka[SWITCH][3-i][2  ][0]));
        }
    }

    // DEFINICE ROTACÍ 3.ČÁST
    private void r_A_B_F_E() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                rotace1[i][j] = kostka[SWITCH][1 + i][2][2 - j];
              
            }

        }
        for (int i = 0; i < 3; i++) {

            rotace2[0][i] = kostka[SWITCH][0][0][i];
            rotace2[1][i] = kostka[SWITCH][1 + i][1][2];
            rotace2[2][i] = kostka[SWITCH][4][0][2 - i];
            rotace2[3][i] = kostka[SWITCH][3 - i][3][0];
           
        }
    }
    private void w_A_B_F_E() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                kostka[SWITCH][i + 1][2][2 - j] = rotace1[i][j];
                  colorbutt[ts_FIELD_to_INT(GETFIELD(  1+i,  2,2-j))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i ][2  ][2-j]));
            }
        }
        for (int i = 0; i < 3; i++) {

            kostka[SWITCH][0][0][i] = rotace2[0][i];
            kostka[SWITCH][ 1 + i][1][2] = rotace2[1][i];
            kostka[SWITCH][4][0][2 - i] = rotace2[2][i];
            kostka[SWITCH][3 - i][3][0] = rotace2[3][i];
            colorbutt[ts_FIELD_to_INT(GETFIELD(  0,  0,i))].setBackground(ts_INT_to_COL(kostka[SWITCH][0  ][0  ][i]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(1+i,  1,2))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i][1  ][2]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(  4,0,2-i))].setBackground(ts_INT_to_COL(kostka[SWITCH][4  ][0][2-i]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(3-i,  3,0))].setBackground(ts_INT_to_COL(kostka[SWITCH][3-i][3  ][0]));
        }
    }
    private void r_AD_BC_FG_EH() {
        for (int i = 0; i < 3; i++) {

            rotace2[0][i] = kostka[SWITCH][0][1][i];
            rotace2[1][i] = kostka[SWITCH][ 1 + i][1][1];
            rotace2[2][i] = kostka[SWITCH][4][1][2 - i];
            rotace2[3][i] = kostka[SWITCH][3 - i][3][1];
        }
    }
    private void w_AD_BC_FG_EH() {
        for (int i = 0; i < 3; i++) {

            kostka[SWITCH][0][1][i] = rotace2[0][i];
            kostka[SWITCH][ 1 + i][1][1] = rotace2[1][i];
            kostka[SWITCH][4][1][2 - i] = rotace2[2][i];
            kostka[SWITCH][3 - i][3][1] = rotace2[3][i];
            colorbutt[ts_FIELD_to_INT(GETFIELD(  0,  1,i))].setBackground(ts_INT_to_COL(kostka[SWITCH][0  ][1  ][i]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(1+i,  1,1))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i][1  ][1]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(  4,1,2-i))].setBackground(ts_INT_to_COL(kostka[SWITCH][4  ][1][2-i]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(3-i,  3,1))].setBackground(ts_INT_to_COL(kostka[SWITCH][3-i][3  ][1]));
        }
    }
    private void r_D_C_G_H() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                rotace1[i][j] = kostka[SWITCH][ 1 + i][0][j];
            }

        }
        for (int i = 0; i < 3; i++) {

            rotace2[0][i] = kostka[SWITCH][0][2][i];
            rotace2[1][i] = kostka[SWITCH][ 1 + i][1][0];
            rotace2[2][i] = kostka[SWITCH][4][2][2 - i];
            rotace2[3][i] = kostka[SWITCH][3 - i][3][2];
        }
    }
    private void w_D_C_G_H() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                kostka[SWITCH][ 1 + i][0][j] = rotace1[i][j];
                 colorbutt[ts_FIELD_to_INT(GETFIELD(1+i,  0,j))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i][0  ][j]));
            }

        }
        for (int i = 0; i < 3; i++) {

            kostka[SWITCH][0][2][i] = rotace2[0][i];
            kostka[SWITCH][ 1 + i][1][0] = rotace2[1][i];
            kostka[SWITCH][4][2][2 - i] = rotace2[2][i];
            kostka[SWITCH][3 - i][3][2] = rotace2[3][i];
            colorbutt[ts_FIELD_to_INT(GETFIELD(  0,  2,i))].setBackground(ts_INT_to_COL(kostka[SWITCH][0  ][2  ][i]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(1+i,  1,0))].setBackground(ts_INT_to_COL(kostka[SWITCH][1+i][1  ][0]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(  4,2,2-i))].setBackground(ts_INT_to_COL(kostka[SWITCH][4  ][2][2-i]));
            colorbutt[ts_FIELD_to_INT(GETFIELD(3-i,  3,2))].setBackground(ts_INT_to_COL(kostka[SWITCH][3-i][3  ][2]));
        }
    }
    


    private void rotuj(int b) {
          
        switch (b) {
            case 0:

                break;
            case 1:
                r_A_B_C_D();
                rotace1l();
                rotace2l();
                w_A_B_C_D();
                break;
            case 2:
                r_AE_BF_CG_DH();
                rotace2l();
                w_AE_BF_CG_DH();
                break;
            case 3:
                r_E_F_G_H();
                rotace1l();
                rotace2l();
                w_E_F_G_H();
                break;
            case 4:
                r_A_D_H_E();
                rotace1l();
                rotace2r();
                w_A_D_H_E();
                break;
            case 5:
                r_AB_CD_GH_EF();
                rotace2r();
                w_AB_CD_GH_EF();
                break;
            case 6:
                r_B_C_G_F();
                rotace1l();
                rotace2r();
                w_B_C_G_F();
                break;
            case 7:
                r_A_B_F_E();
                rotace1l();
                rotace2r();
                w_A_B_F_E();
                break;
            case 8:
                r_AD_BC_FG_EH();
                rotace2r();
                w_AD_BC_FG_EH();
                break;
            case 9:
                r_D_C_G_H();
                rotace1l();
                rotace2r();
                w_D_C_G_H();
                break;
            case 10:
                r_A_B_C_D();
                rotace1r();
                rotace2r();
                w_A_B_C_D();
                break;
            case 11:
                r_AE_BF_CG_DH();
                rotace2r();
                w_AE_BF_CG_DH();
                break;
            case 12:
                r_E_F_G_H();
                rotace1r();
                rotace2r();
                w_E_F_G_H();
                break;
            case 13:
                r_A_D_H_E();
                rotace1r();
                rotace2l();
                w_A_D_H_E();
                break;
            case 14:
                r_AB_CD_GH_EF();
                rotace2l();
                w_AB_CD_GH_EF();
                break;
            case 15:
                r_B_C_G_F();
                rotace1r();
                rotace2l();
                w_B_C_G_F();
                break;
            case 16:
                r_A_B_F_E();
                rotace1r();
                rotace2l();
                w_A_B_F_E();
                break;
            case 17:
                r_AD_BC_FG_EH();
                rotace2l();
                w_AD_BC_FG_EH();
                break;
            case 18:
                r_D_C_G_H();
                rotace1r();
                rotace2l();
                w_D_C_G_H();
                break;
        }
        
        clear();
       
//        if (b>9){System.out.print(b+" ");
//            
//        } else  {System.out.print(b+"  ");
//        }
    }
//    Kontrolní bloky
   
    private boolean check2 (){
       
                 if (kostka[SWITCH][0][0][1]!=1) {return false; }
         else    if (kostka[SWITCH][0][1][0]!=1) {return false; }
         else    if (kostka[SWITCH][0][1][2]!=1) {return false; }
         else return kostka[SWITCH][0][2][1] == 1;
       
    }  
    private boolean check3(){
         
                if(kostka[SWITCH][1][0][1]!=kostka[SWITCH][2][0][1]){return false;}
           else if(kostka[SWITCH][1][1][1]!=kostka[SWITCH][2][1][1]){return false;}
           else if(kostka[SWITCH][1][2][1]!=kostka[SWITCH][2][2][1]){return false;}
           else if(kostka[SWITCH][1][3][1]!=kostka[SWITCH][2][3][1]){return false;}
           return true; 
        }
    private boolean check4(){
                if ( kostka[SWITCH][0][0][0]!=1){return false;
        } else  if ( kostka[SWITCH][0][0][2]!=1){return false;
        } else  if ( kostka[SWITCH][0][2][0]!=1){return false;
        } else  if ( kostka[SWITCH][0][2][2]!=1){return false;
        } else  if ( kostka[SWITCH][1][0][0]!= kostka[SWITCH][2][0][1]){return false;
        } else  if ( kostka[SWITCH][1][0][2]!= kostka[SWITCH][2][0][1]){return false;
        } else  if ( kostka[SWITCH][1][1][0]!= kostka[SWITCH][2][1][1]){return false;
        } else  if ( kostka[SWITCH][1][1][2]!= kostka[SWITCH][2][1][1]){return false;
        } else  if ( kostka[SWITCH][1][2][0]!= kostka[SWITCH][2][2][1]){return false;
        } else  if ( kostka[SWITCH][1][2][2]!= kostka[SWITCH][2][2][1]){return false;
        } else  if ( kostka[SWITCH][1][3][0]!= kostka[SWITCH][2][3][1]){return false;
     } else  return  kostka[SWITCH][1][3][2]== kostka[SWITCH][2][3][1];
    } 
    private boolean check5(){
                if ( kostka[SWITCH][2][0][0]!= kostka[SWITCH][2][0][1]){return false;
        } else  if ( kostka[SWITCH][2][0][2]!= kostka[SWITCH][2][0][1]){return false;
        } else  if ( kostka[SWITCH][2][1][0]!= kostka[SWITCH][2][1][1]){return false;
        } else  if ( kostka[SWITCH][2][1][2]!= kostka[SWITCH][2][1][1]){return false;
        } else  if ( kostka[SWITCH][2][2][0]!= kostka[SWITCH][2][2][1]){return false;
        } else  if ( kostka[SWITCH][2][2][2]!= kostka[SWITCH][2][2][1]){return false;
        } else  if ( kostka[SWITCH][2][3][0]!= kostka[SWITCH][2][3][1]){return false;
     } else  return  kostka[SWITCH][2][3][2]== kostka[SWITCH][2][3][1];
    } 
    private boolean check6(){
                    if(kostka[SWITCH][4][0][0]!=6){return false;
          } else    if(kostka[SWITCH][4][0][2]!=6){  return false;
          } else    if(kostka[SWITCH][4][2][0]!=6){  return false;
          } else    return kostka[SWITCH][4][2][2] == 6;
        
       
    }
    private boolean check7(){
                    if(kostka[SWITCH][4][0][1]!=6){return false;
      } else        if(kostka[SWITCH][4][1][0]!=6){  return false;
      } else        if(kostka[SWITCH][4][1][2]!=6){  return false;
      } else    return kostka[SWITCH][4][2][1] == 6;
        
       
    }
    private boolean check8(){
                if ( kostka[SWITCH][3][0][0]!= kostka[SWITCH][2][0][1]){return false;
        } else  if ( kostka[SWITCH][3][0][2]!= kostka[SWITCH][2][0][1]){return false;
        } else  if ( kostka[SWITCH][3][1][0]!= kostka[SWITCH][2][1][1]){return false;
        } else  if ( kostka[SWITCH][3][1][2]!= kostka[SWITCH][2][1][1]){return false;
        } else  if ( kostka[SWITCH][3][2][0]!= kostka[SWITCH][2][2][1]){return false;
        } else  if ( kostka[SWITCH][3][2][2]!= kostka[SWITCH][2][2][1]){return false;
        } else  if ( kostka[SWITCH][3][3][0]!= kostka[SWITCH][2][3][1]){return false;
     } else  return  kostka[SWITCH][3][3][2]== kostka[SWITCH][2][3][1];
       
    }
    private boolean check9(){
              if   (kostka[SWITCH][3][0][1] !=kostka[SWITCH][2][0][1]){return false;
      } else  if   (kostka[SWITCH][3][1][1] !=kostka[SWITCH][2][1][1]){return false;
      } else  if   (kostka[SWITCH][3][2][1] !=kostka[SWITCH][2][2][1]){return false;
      } else return kostka[SWITCH][3][3][1] ==kostka[SWITCH][2][3][1];
       
       
    }
    
    private void randomizer() {
        
        int e,c;
        int[] d;
        e = (int) (Math.random() * 200 + 100);
        d = new int[e];
        for (int i = 0; i < d.length; i++) {
            c = (int) (Math.random() * 18 + 1);
            rotuj(c);
            d[i] = c;
            
        }
        for (int i = 0; i < d.length; i++) {

            
            if (d[i] < 10) {
                
                s+=d[i]+"  ";
            } else {
                s+=d[i]+" ";
            }
                  
            
        }
        System.out.println(s);
        System.out.println(tisk());
        
    }

     
   // řešení - logika  
    public void solver (){
         solve_1_0();
          tisk();
         solve_2_0();
          tisk();
         solve_3_0();
         tisk();
         solve_4_0();
          tisk();
          }
         
//  0= basic 1 = complex
    private void solve_1_0 (){
       
                       if(kostka[1][0][1][1]==1)   {  } 
                  else if(kostka[1][2][0][1]==1)   {rotuj(4);rotuj(5);rotuj(6); }
                  else if(kostka[1][2][1][1]==1)   {rotuj(7);rotuj(8);rotuj(9);  }
                  else if(kostka[1][2][2][1]==1)   {rotuj(13);rotuj(14);rotuj(15); }
                  else if(kostka[1][2][3][1]==1)   {rotuj(16);rotuj(17);rotuj(18);  }
                  else if(kostka[1][4][1][1]==1)   {rotuj(4);rotuj(5);rotuj(6);rotuj(4);rotuj(5);rotuj(6); }
             
                  
                  if (kostka[1][2][0][1]==4) {rotuj(1);rotuj(2);rotuj(3);rotuj(1);rotuj(2);rotuj(3);}  
                  else if(kostka[1][2][1][1]==4) {rotuj(1);rotuj(2);rotuj(3);}
                  else if(kostka[1][2][3][1]==4) {rotuj(10);rotuj(11);rotuj(12);}
                 
       
                  
      
  }
    private void solve_1_1 (){
       
                       if(kostka[1][0][1][1]==kostka[2][0][1][1])       {  } 
                  else if(kostka[1][2][0][1]==kostka[2][0][1][1])   {rotuj(4);rotuj(5);rotuj(6); }
                  else if(kostka[1][2][1][1]==kostka[2][0][1][1])   {rotuj(7);rotuj(8);rotuj(9);  }
                  else if(kostka[1][2][2][1]==kostka[2][0][1][1])   {rotuj(13);rotuj(14);rotuj(15); }
                  else if(kostka[1][2][3][1]==kostka[2][0][1][1])   {rotuj(16);rotuj(17);rotuj(18);  }
                  else if(kostka[1][4][1][1]==kostka[2][0][1][1])   {rotuj(4);rotuj(5);rotuj(6);rotuj(4);rotuj(5);rotuj(6); }
                  
                  
                  if (kostka[1][2][0][1]==4) {rotuj(1);rotuj(2);rotuj(3);rotuj(1);rotuj(2);rotuj(3);}  
                  else if(kostka[1][2][1][1]==4) {rotuj(1);rotuj(2);rotuj(3);}
                  else if(kostka[1][2][3][1]==4) {rotuj(10);rotuj(11);rotuj(12);}
                 if (kostka[1][0][1][1]==1)       {  } 
                  tisk();
                  
      
  }
    private void solve_2_0 (){
      
                
                  
                 while(check2()==false)  {
                   
                      while(kostka[1][0][0][1]==1){rotuj(1);}
                         
                   
 
                             if (kostka[1][1][2][1]==1) { rotuj(7) ; rotuj(11); rotuj(7) ;} 
                        else if (kostka[1][2][2][0]==1) { rotuj(2) ; rotuj(16); }
                        else if (kostka[1][2][2][2]==1) { rotuj(11); rotuj(7) ; }
                        else if (kostka[1][3][2][1]==1) { rotuj(16); rotuj(11); rotuj(7) ;}
                        
                        else if (kostka[1][1][1][1]==1) { rotuj(6) ; rotuj(7) ; }
                        else if (kostka[1][2][1][0]==1) { rotuj(2) ; rotuj(2) ; rotuj(16);}
                        else if (kostka[1][2][1][2]==1) { rotuj(7) ; }
                        else if (kostka[1][3][1][1]==1) { rotuj(3) ; rotuj(16); rotuj(2) ; rotuj(7);}
                        
                        else if (kostka[1][1][3][1]==1) { rotuj(4) ; rotuj(14); }
                        else if (kostka[1][2][3][0]==1) { rotuj(16); }
                        else if (kostka[1][2][3][2]==1) { rotuj(11); rotuj(11); rotuj(7) ; }
                        else if (kostka[1][3][3][1]==1) { rotuj(12); rotuj(16); rotuj(11); rotuj(7);}
                        
                        else if (kostka[1][2][0][1]==1) { rotuj(18); rotuj(2) ; rotuj(7);}
                        else if (kostka[1][2][0][0]==1) { rotuj(11); rotuj(16); }
                        else if (kostka[1][2][0][2]==1) { rotuj(2) ; rotuj(7) ; }
                        else if (kostka[1][3][0][1]==1) { rotuj(12); rotuj(12); rotuj(16); rotuj(11); rotuj(7);}
                        
                        else if (kostka[1][4][0][1]==1) { rotuj(7) ; rotuj(7) ;}
                        else if (kostka[1][4][1][0]==1) {rotuj(12); rotuj(7) ; rotuj(7) ;}
                        else if (kostka[1][4][1][2]==1) { rotuj(3) ; rotuj(7) ; rotuj(7) ;}
                        else if (kostka[1][4][2][1]==1) { rotuj(12); rotuj(12); rotuj(7) ; rotuj(7) ;}
                        
                        
                       
  }} 
    private void solve_2_1 (){
   
                
                  
                 while(check2()==false)  {
                     
                     while(kostka[1][0][0][1]==1){rotuj(1);}
                         
                   
 
                            if (kostka[1][1][2][1]==1) { rotuj(7) ; rotuj(11); rotuj(7) ;} 
                        else if (kostka[1][2][2][0]==1) { rotuj(2) ; rotuj(16); }
                        else if (kostka[1][2][2][2]==1) { rotuj(11); rotuj(7) ; }
                        else if (kostka[1][3][2][1]==1) { rotuj(16); rotuj(11); rotuj(7) ;}
                        
                        else if (kostka[1][1][1][1]==1) { rotuj(6) ; rotuj(7) ; }
                        else if (kostka[1][2][1][0]==1) { rotuj(2) ; rotuj(2) ; rotuj(16);}
                        else if (kostka[1][2][1][2]==1) { rotuj(7) ; }
                        else if (kostka[1][3][1][1]==1) { rotuj(3) ; rotuj(16); rotuj(2) ; rotuj(7);}
                        
                        else if (kostka[1][1][3][1]==1) { rotuj(4) ; rotuj(14); }
                        else if (kostka[1][2][3][0]==1) { rotuj(16); }
                        else if (kostka[1][2][3][2]==1) { rotuj(11); rotuj(11); rotuj(7) ; }
                        else if (kostka[1][3][3][1]==1) { rotuj(12); rotuj(16); rotuj(11); rotuj(7);}
                        
                        else if (kostka[1][2][0][1]==1) { rotuj(18); rotuj(2) ; rotuj(7);}
                        else if (kostka[1][2][0][0]==1) { rotuj(11); rotuj(16); }
                        else if (kostka[1][2][0][2]==1) { rotuj(2) ; rotuj(7) ; }
                        else if (kostka[1][3][0][1]==1) { rotuj(12); rotuj(12); rotuj(16); rotuj(11); rotuj(7);}
                        
                        else if (kostka[1][4][0][1]==1) { rotuj(7) ; rotuj(7) ;}
                        else if (kostka[1][4][1][0]==1) {rotuj(12); rotuj(7) ; rotuj(7) ;}
                        else if (kostka[1][4][1][2]==1) { rotuj(3) ; rotuj(7) ; rotuj(7) ;}
                        else if (kostka[1][4][2][1]==1) { rotuj(12); rotuj(12); rotuj(7) ; rotuj(7) ;}
                        
                        
                            
  }}
    private void solve_3_0 (){
      int a,b;
      
      a=b=0;
         while(check2()==false || check3()==false){
            
                   if(check2()==true){
                    if(kostka[1][1][0][1]!=kostka[1][2][0][1]){rotuj(9);rotuj(9);a=0;
             }else  if(kostka[1][1][1][1]!=kostka[1][2][1][1]){rotuj(6);rotuj(6);a=1;
             }else  if(kostka[1][1][2][1]!=kostka[1][2][2][1]){rotuj(7);rotuj(7);a=2;
             }else  if(kostka[1][1][3][1]!=kostka[1][2][3][1]){rotuj(4);rotuj(4);a=3;
             }}
                   
                    if(kostka[1][3][a][1]==kostka[1][2][0][1]){b=0;
             }else  if(kostka[1][3][a][1]==kostka[1][2][1][1]){b=1;
             }else  if(kostka[1][3][a][1]==kostka[1][2][2][1]){b=2;
             }else  if(kostka[1][3][a][1]==kostka[1][2][3][1]){b=3;
             }
                  
                    if (a==3&&b==0)          {rotuj(3);
             } else if (a==0&&b==3)          {rotuj(12);
             } else if ((a-b)==2 || (b-a)==2){rotuj(3);rotuj(3);
             } else if ((b-a)==1)            {rotuj(3);
             } else if ((a-b)==1)            {rotuj(12);
             }
                
    switch(b){
        case 0: rotuj(9);rotuj(9);a=0;break;
        case 1: rotuj(6);rotuj(6);a=1;break;
        case 2: rotuj(7);rotuj(7);a=2;break;
        case 3: rotuj(4);rotuj(4);a=3;break;
    }
    
         }
      
  }
    private void solve_3_1 (){
     
                 
  }
    private void solve_4_0 (){
      while(check4()==false){
         
            if(kostka[1][3][2][0]==1) {
      }else if(kostka[1][3][2][2]==1) {
          
      }else if(kostka[1][3][3][0]==1) {rotuj(12);
      }else if(kostka[1][3][3][2]==1) {rotuj(12);
      }else if(kostka[1][3][0][0]==1) {rotuj(12);rotuj(12);
      }else if(kostka[1][3][0][2]==1) {rotuj(12);rotuj(12);
      }else if(kostka[1][3][1][0]==1) {rotuj(3 );
      }else if(kostka[1][3][1][2]==1) {rotuj(3 );
      
      }else if(kostka[1][4][0][0]==1) {rotuj(3 );rotuj(6 );rotuj(3 );rotuj(15);
      }else if(kostka[1][4][0][2]==1) {rotuj(3 );rotuj(3 );rotuj(6 );rotuj(3 );rotuj(15);
      }else if(kostka[1][4][2][0]==1) {rotuj(6 );rotuj(3 );rotuj(15);
      }else if(kostka[1][4][2][2]==1) {rotuj(12);rotuj(6 );rotuj(3 );rotuj(15);
      
      }else if(kostka[1][1][0][0]==1) {rotuj(9 );rotuj(12);rotuj(18);rotuj(12);
      }else if(kostka[1][1][0][2]==1) {rotuj(18);rotuj(3 );rotuj(9 );rotuj(3 );
      }else if(kostka[1][1][1][0]==1) {rotuj(18);rotuj(12);rotuj(9 );rotuj(3 );
      }else if(kostka[1][1][1][2]==1) {rotuj(6 );rotuj(3 );rotuj(15);
      }else if(kostka[1][1][2][0]==1) {rotuj(6 );rotuj(12);rotuj(15);
      }else if(kostka[1][1][2][2]==1) {rotuj(4 );rotuj(3 );rotuj(13);
      }else if(kostka[1][1][3][0]==1) {rotuj(7 );rotuj(12);rotuj(16);rotuj(12);
      }else if(kostka[1][1][3][2]==1) {rotuj(9 );rotuj(3 );rotuj(18);rotuj(12);
      
      }else if(kostka[1][0][0][0]==1 &&  kostka[1][1][2][2]!=kostka[1][2][2][1]  && kostka[1][1][3][0]!=kostka[1][2][3][1]) {rotuj(4 );rotuj(11);rotuj(13);rotuj(2 );
      }else if(kostka[1][0][0][2]==1 &&  kostka[1][1][2][0]!=kostka[1][2][2][1]  && kostka[1][1][1][2]!=kostka[1][2][1][1]) {rotuj(6 );rotuj(2 );rotuj(15);rotuj(11);
      }else if(kostka[1][0][2][0]==1 &&  kostka[1][1][0][0]!=kostka[1][2][0][1]  && kostka[1][1][3][2]!=kostka[1][2][3][1]) {rotuj(9 );rotuj(12);rotuj(18);
      }else if(kostka[1][0][2][2]==1 &&  kostka[1][1][0][2]!=kostka[1][2][0][1]  && kostka[1][1][1][0]!=kostka[1][2][1][1]) {rotuj(18);rotuj(3 );rotuj(9 );
      }       
            
      if (kostka[1][3][2][0]==1) {
          
                   if(kostka[1][4][0][2]==kostka[1][2][2][1] && kostka[1][3][1][2]==kostka[1][2][1][1]){}
             else  if(kostka[1][4][0][2]==kostka[1][2][3][1] && kostka[1][3][1][2]==kostka[1][2][2][1]){rotuj(10);rotuj(11);}
             else  if(kostka[1][4][0][2]==kostka[1][2][0][1] && kostka[1][3][1][2]==kostka[1][2][3][1]){rotuj(1 );rotuj(2 );rotuj(1 );rotuj(2 ); }
             else  if(kostka[1][4][0][2]==kostka[1][2][1][1] && kostka[1][3][1][2]==kostka[1][2][0][1]){rotuj(1 );rotuj(2 );}
             
          rotuj(3 );rotuj(6 );rotuj(12);rotuj(15);
     } else if(kostka[1][3][2][2]==1) {
         
                   if(kostka[1][4][0][0]==kostka[1][2][2][1] && kostka[1][3][3][0]==kostka[1][2][3][1]){System.out.print("B1 "); }
             else  if(kostka[1][4][0][0]==kostka[1][2][3][1] && kostka[1][3][3][0]==kostka[1][2][0][1]){rotuj(10);rotuj(11);System.out.print("B2 ");}
             else  if(kostka[1][4][0][0]==kostka[1][2][0][1] && kostka[1][3][3][0]==kostka[1][2][1][1]){rotuj(1 );rotuj(2 );rotuj(1 );rotuj(2 );System.out.print("B3 ");}
             else  if(kostka[1][4][0][0]==kostka[1][2][1][1] && kostka[1][3][3][0]==kostka[1][2][2][1]){rotuj(1 );rotuj(2 ); System.out.print("B4 ");}
             
          rotuj(12);rotuj(4 );rotuj(3 );rotuj(13);           
      }

      }
  }
    private void solve_4_1 (){
      
  }
    private void solve_5_0 (){
        int a,b;
        a=b=0;
      while(check4()==false || check5()== false){
            if (kostka[SWITCH][3][0][1]!=6 && kostka[SWITCH][4][2][1]!=6 ) {rotuj(3);rotuj(3);
     } else if (kostka[SWITCH][3][1][1]!=6 && kostka[SWITCH][4][1][2]!=6 ) {rotuj(3);
     } else if (kostka[SWITCH][3][2][1]!=6 && kostka[SWITCH][4][0][1]!=6 ) {
     } else if (kostka[SWITCH][3][3][1]!=6 && kostka[SWITCH][4][1][0]!=6 ) {rotuj(12);
     } else{
             if (kostka[SWITCH][2][0][0]!=kostka[SWITCH][2][0][1] || kostka[SWITCH][2][3][2]!=kostka[SWITCH][2][3][1]) {
      } else if (kostka[SWITCH][2][1][0]!=kostka[SWITCH][2][1][1] || kostka[SWITCH][2][0][2]!=kostka[SWITCH][2][0][1]) {
      } else if (kostka[SWITCH][2][2][0]!=kostka[SWITCH][2][2][1] || kostka[SWITCH][2][1][2]!=kostka[SWITCH][2][1][1]) {
      } else if (kostka[SWITCH][2][3][0]!=kostka[SWITCH][2][3][1] || kostka[SWITCH][2][2][2]!=kostka[SWITCH][2][2][1]) {
      }
     }
              
            if (kostka[SWITCH][3][0][1]!=6 && kostka[SWITCH][4][2][1]!=6 ) {rotuj(3);rotuj(3);
     } else if (kostka[SWITCH][3][1][1]!=6 && kostka[SWITCH][4][1][2]!=6 ) {rotuj(3);
     } else if (kostka[SWITCH][3][2][1]!=6 && kostka[SWITCH][4][0][1]!=6 ) {
     } else if (kostka[SWITCH][3][3][1]!=6 && kostka[SWITCH][4][1][0]!=6 ) {rotuj(12);
     } 
      }
  }
    private void solve_5_1 (){
      
  }
    private void solve_6_0 (){
      while(check6()==false){
             if (kostka[1][4][0][0]==6) {rotuj(3);
      } else if(kostka[1][3][2][2]==6){rotuj(13);rotuj(1);rotuj(4);rotuj(10);rotuj(13);rotuj(1);rotuj(4);rotuj(10);rotuj(3);
      } else if(kostka[1][3][3][0]==6){rotuj(1);rotuj(13);rotuj(10);rotuj(4);rotuj(1);rotuj(13);rotuj(10);rotuj(4);rotuj(3);
      }
  }
  }
    private void solve_6_1 (){
      
  }
    private void solve_7_0 (){
      
  }
    private void solve_7_1 (){
      
  }
    private void solve_8_0 (){
      
  }
    private void solve_8_1 (){
      
  }
    private void solve_9_0 (){
      
  }
    private void solve_9_1 (){
      
  }
  
 
  
  
  
//  Grafika- SWING
  

     private void set() {
  CONTROLBUTTON();
  COLORBUTTON();
  COLORSELECT();
  setCBP();
  setSize(1200, 500);
        setLocationRelativeTo(null);
  
        
 setBackground(Color.MAGENTA);
  setVisible(true);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  add(cbp[0],BorderLayout.CENTER);
    }
     private void setCBP() {
      cbp=new JPanel[10];
      cbp[0]=new JPanel();
      cbp[0].setBackground(Color.MAGENTA);
      cbp[0].setLayout(new BoxLayout(cbp[0], BoxLayout.Y_AXIS));
    for (int j = 1; j < 10; j++) {
            cbp[j]=new JPanel();
            cbp[j].setBackground(Color.MAGENTA);
            cbp[j].setLayout(new BoxLayout(cbp[j], BoxLayout.X_AXIS));
            if (j<4) {
                for (int k = 1; k < 4; k++) {
                  cbp[j].add(Box.createRigidArea(new Dimension(20, 0))); 
                  cbp[j].add(colorbutt[(j-1)*3+k]);
                }
            
        } else if(j<7) {
            cbp[j].add(Box.createRigidArea(new Dimension(220, 0))); 
                for (int l = 1; l < 5; l++) {
                    for (int k = 1; k < 4; k++) {
                  cbp[j].add(Box.createRigidArea(new Dimension(20, 0))); 
                  cbp[j].add(colorbutt[(j-4)*3+k+l*9]);
                }
                    cbp[j].add(Box.createRigidArea(new Dimension(20, 0))); 
                }
           
            
        }else{
            for (int k = 1; k < 4; k++) {
                  cbp[j].add(Box.createRigidArea(new Dimension(20, 0))); 
                  cbp[j].add(colorbutt[(j-7)*3+k+45]);}
        }
            cbp[0].add(cbp[j]);
            cbp[0].add(Box.createRigidArea(new Dimension(0, 20)));
            
        }
    }
    
    
     private void setkostka() {
        int n;
        for (int i = 1; i < 55; i++) {
             n=ts_INT_to_FIELD(i);
            kostka[SWITCH][GETx(n)][GETy(n)][GETz(n)]=ts_COL_to_Int(colorbutt[i].getBackground());
        }

        
    }
     private void setbarva(){
        int n;
        for (int i = 1; i < 55; i++) {
             n=ts_INT_to_FIELD(i);
            colorbutt[i].setBackground(ts_INT_to_COL(kostka[SWITCH][GETx(n)][GETy(n)][GETz(n)]));
        }


     
     
    }

    
 //         tlačítka
     private void CONTROLBUTTON (){
       controlbutt=new JButton[19];
       
       for (int j = 0; j < 19; j++) {
           String q;
           
           if (j<10) {q="  "+j+" ";
               
           } else {q=" "+j+" ";
           }
           
           controlbutt[j]=new JButton(q);
            controlbutt[j].setName(""+j);
           controlbutt[j].setBackground(Color.WHITE);
           controlbutt[j].addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent e) {
                   
                   rotuj(GETi(e));
               }
           });
           
       }
   }
     private void COLORBUTTON (){
       colorbutt=new JButton[55];
       for (int j = 0; j < 55; j++) {
           colorbutt[j]= new JButton("  ");
           colorbutt[j].setName(""+j);
           colorbutt[j].addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent e) {
                   if (colorbutt[0].isVisible()==true) {
                       int a=GETi(e);
                       colorbutt[a].setBackground(colorbutt[0].getBackground());
                      kostka[SWITCH][GETx(a)][GETy(a)][GETz(a)]=ts_COL_to_Int(colorbutt[a].getBackground());
                   }
 
                  
                   }

           });
       }
   }
     private void COLORSELECT (){
      colorselect= new JButton[7];
       for (int j = 0; j < 7; j++) {
       if (j==0) {
           colorselect[0]=new JButton("DONE");
           colorselect[0].setBackground(Color.CYAN);
           colorselect[0].addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent e) {
                 
               }
           });
           
       } else {
           colorselect[j]=new JButton("  ");
           colorselect[j].setName(""+j);
           colorselect[j].setBackground(ts_INT_to_COL(j));
           colorselect[j].addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent e) {
                colorbutt[0].setBackground(ts_INT_to_COL(GETi(e)));
               }
           });
       }
       }
   }
     private void Buttrandom() {
        random = new JButton("Randomizer");
        random.setBackground(Color.BLUE);
        random.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                randomizer();
                setbarva();
            }
        });
    }
     private void Buttsolve() {
        solve = new JButton("SOLVE!");
        solve.setBackground(Color.BLUE);
        solve.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            solver();
               
                setbarva();
            }
        });
    }
    
    
     // utilities
     private int GETi(ActionEvent e) {
       int i=new Integer(((JButton) e.getSource()).getName());
              return i;
               }
     private int GETx(int a){
          a=(a-a%100)/100;
         return a;
     }
     private int GETy(int a){
           a=(a%100-a%10)/10;
         return a;
     }
     private int GETz(int a){
         a=a%10;
         return a;
     }
     private int GETFIELD(int a,int b,int c){
         return 100*a+10*b+c;
     }
     
   
//    barva <==> číslo barvy
    private Color ts_INT_to_COL (int   a){
       Color col;
        
            switch (a) {
                case 1:
                    col = Color.WHITE;
                    break;
                case 2:
                    col = Color.BLUE;
                    break;
                case 3:
                    col = Color.ORANGE;
                    break;
                case 4:
                    col = Color.GREEN;
                    break;
                case 5:
                    col = Color.RED;
                    break;
                default:
                    col = Color.YELLOW;
                    
                
            }
return col;
        } 
    private int   ts_COL_to_Int (Color a){
        int barva=0;
               if (a==Color.WHITE) {barva=1;
        } else if(a==Color.BLUE)   {barva=2;
        } else if(a==Color.ORANGE) {barva=3;
        } else if(a==Color.GREEN)  {barva=4;
        } else if(a==Color.RED)    {barva=5;
        } else if(a==Color.YELLOW) {barva=6;
        }
        
        
         return barva;
}
    
    
//    číslo tlačítka<==>souřadnice na kostce ve 2D
    private int ts_INT_to_FIELD(int a){
        int x,y,z;
          int n;
               if (a<10) {n=a; y=0; z=(n-1)%3;x=3-(n-1)/3;
        } else if (a<19) {n=a-9 ; y=3;z=2-(n-1)/3;x=3-(n-1)%3;
        } else if (a<28) {n=a-18;x=0;z=(n-1)%3;y=2-(n-1)/3;
        } else if (a<37) {n=a-27;y=1;z=(n-1)/3;x=(n-1)%3+1;
        } else if (a<46) {n=a-36;x=4;z=(n-1)%3;y=2-(n-1)/3;
        } else           {n=a-45;y=2;z=2-(n-1)%3;x=(n-1)/3+1;          
        }
       
       return GETFIELD(x, y, z);
    }
    private int ts_FIELD_to_INT(int a){
        int x,y,z;
        x=GETx(a);
        y=GETy(a);
        z=GETz(a);
          int n = 0;
               if (x==0) {n=19+(2-y)*3+z;
            
        } else if(x<4){
            switch (y){
                 case 0:n=(3-x)*3+z+1;   break;
                 case 1:n=27+x+3*z;      break;
                 case 2:n=48-z+(x-1)*3;  break;
                 case 3:n=10-x+(3-z)*3;  break;
        }
        
        }else{
            n=37+(2-y)*3+z;
            
        }
   return n;
    }
    
    
    // 3D grafika - JPCT
    private void panel3d(){
       
    }
}


