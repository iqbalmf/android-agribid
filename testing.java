/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belajarava;
import paketbaru.NewClass;
/**
 *
 * @author Favz
 */
public class Belajarava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
    //1
    System.out.println("Hai dunia, nama gue Iqbal. gue baru belajar java haha");
    
    //2
    operasibilangan ob1 = new operasibilangan();
    
    ob1.bilangan1 = 50;
    ob1.bilangan2 = 40;
    ob1.jumlah();
    ob1.tampil();
    
    
    //3
    NewClass kelas = new NewClass();
    kelas.cetakhelloworld();
    
    //4
    ob1.bilangan3 = 100;
    ob1.bilangan4 = 23;
    ob1.tampilan();
    
    //5 
    kelas.cetakkata("hello ini pake parameter");
            
    }   
}
