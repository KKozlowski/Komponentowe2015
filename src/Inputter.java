import javax.swing.JOptionPane;

import java.io.*;

public class Inputter {
	public int getInt(String request){
		String ile;
		ile = JOptionPane.showInputDialog(request);
		return Integer.parseInt(ile);
	}
	
	public String getString(String request){
		String str;
		str = JOptionPane.showInputDialog(request);
		return str;
	}
	
	public void saveLoad(){
		// ZAPIS DO PLIKU TEKSTOWEGO 
		File plik = new File("pliczek.txt"); 		// Obiekt klasy File reprezentuje plik 
									// w pamieci programu
		String tekstDoZapisania = new String("ABCDEFGHIJ");	// Tekst, na ktorym bedziemy operowac 

		try	{
			plik.createNewFile();					// Utworzenie pliku pod sciezka zapisana w plik 
			FileWriter strumienZapisu = new FileWriter(plik);	// Konstrukcja i otwarcie strumienia
			strumienZapisu.write(tekstDoZapisania, 0, 7);		// Zapis do pliku liter od 0 do 7 z txt  
			strumienZapisu.close(); 				// Zamkniecie strumienia
		}

		// Instrukcje lapiace wyjatki
		catch (IOException io)												
			{System.out.println(io.getMessage());}

		catch (Exception se)
			{System.err.println("blad sec");}
		

		//	ODCZYT Z PLIKU TEKSTOWEGO 
		char buf[] = new char[10]; 					// bufor (tablica) na odczytane znaki
		try {
			FileReader strumienOdczytu = new FileReader(plik);	// Konstrukcja i otwarcie strumienia odczytujacego
			strumienOdczytu.read(buf, 0, 7);			// Odczytanie znakow od 0 do 7 ze strumienia do bufora  
		}	
		
		catch (FileNotFoundException io)												
			{System.out.println(io.getMessage());}
		
		catch (IOException io)												
		{System.out.println(io.getMessage());} 
		
		String tekstOdczytany = new String(buf);			// Konwersja zawartosci bufora na String
		System.out.println("Odczytalem znaki: "+ tekstOdczytany); 	// Wydruk

	}	
}
