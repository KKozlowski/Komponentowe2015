import java.io.File;
import java.io.IOException;


public class Klasa {

	public static void main(String[] args) {
		SerializowanaTablica ser = new SerializowanaTablica(15,70);
		ser.wypisz();
		ser.save("data/inty.bin");
		ser.load("data/inty.bin");
		ser.wypisz();
		ser.sortuj();
		ser.wypisz();
		
		EventContainer kont = new EventContainer();
		kont.getEvents();
		kont.saveToBin();
		kont.loadFromBin();
		kont.print();
		kont.saveObjects();
		kont.loadObjects();
		kont.print();
		
		
	}

}


// TODO Auto-generated method stub
// System.out.println("Hello world !!!");

// dodawanie liczb
/*
 * double a=4; // deklaracja zmiennej double b=5; int c=0;
 * System.out.println(a+b);
 * 
 * // dzielenie z instrukcja warunkowa if (b!=0)
 * System.out.println(a/b); else
 * System.out.println("Nie dzielimy przez zero!");
 * 
 * System.out.println(a*b); System.out.println(c+b);
 */

// Wprowadzanie liczb przez okna dialogowe
/*
 * String txt1; //deklaracja zmiennej tekstowej txt1 =
 * JOptionPane.showInputDialog("Wprowadz pierwsza liczbe");
 * 
 * String txt2; //deklaracja zmiennej tekstowej txt2 =
 * JOptionPane.showInputDialog("Wprowadz druga liczbe");
 * 
 * System.out.println(txt1 + txt2); // ???
 * 
 * 
 * 
 * // Konwersja tekstu na liczbe double liczba1 =
 * Double.parseDouble(txt1); int liczba2 = Integer.parseInt(txt2);
 * 
 * System.out.println(liczba1 + liczba2); System.out.println(silnia(3));
 */

//Inputter inp = new Inputter();
//int liczbaWierszy = inp.GetInt("Ile wierszy trojkata?");

//TrojkatNewtona trojkat = new TrojkatNewtona(liczbaWierszy);
//TrojkatPascala trojkat = new TrojkatPascala();
//trojkat.print();


//Arg2 argumenty = new Arg2(args);
/*Tablica tab = new Tablica();
tab.Randomizuj(Integer.parseInt(args[0]), Integer.parseInt(args[1]));*/