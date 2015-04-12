import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SerializowanaTablica {
	private int rozmiar;
	private int zakres;
	private int tab[];
	
	public SerializowanaTablica(int rozmiar){
		this.rozmiar = rozmiar;
		this.zakres = 30;
		wypelnij();
	}
	
	public SerializowanaTablica(int rozmiar, int maksymalnaWartosc){
		this.rozmiar = rozmiar;
		this.zakres = maksymalnaWartosc;
		wypelnij();
	}
	
	private void wypelnij(){
		int i;
		tab = new int[rozmiar];
		// Wypelnianie tablicy liczbami losowymi z dynamicznie ustalanego zakresu
		
		for (i=0; i< rozmiar; i++) 
			tab[i] = (int)(Math.random()*zakres);
		
				
		System.out.println("");
	}
	
	public void sortuj() {
	    boolean zmiana = false;
	    do {
	        zmiana = false;
	        for (int a = 0; a < rozmiar-1; a++) {
	            if (tab[a] > tab[a + 1]) {
	                int tmp = tab[a];
	                tab[a] = tab[a + 1];
	                tab[a + 1] = tmp;
	                zmiana = true;
	            }
	        }
	    } while (zmiana);
	}
	
	public void wypisz(){
		for (int i=0; i< rozmiar; i++)
			System.out.print(tab[i]+" ");
		System.out.println();
	}
	
	
	
	
	public void save(String path){
		try {
			DataOutputStream eventStream = 		// Strumien zapisujacy liczby
				new DataOutputStream(new FileOutputStream(path)); 
			
			eventStream.writeInt(rozmiar);
			for (int i=0; i< rozmiar; i++){
				//strumienTablicy.writeChars(eventy.get(i).getName());
				eventStream.writeInt(tab[i]);
				
			}
			eventStream.close();

		}
		catch (IOException io)												
			{System.out.println(io.getMessage());}

		catch (Exception se)
			{System.err.println("blad sec");}
		
	}
	
	public void load(String path){
		try {
			DataInputStream eventStream = 
				new DataInputStream(new FileInputStream(path));
			
			int rozmiar = eventStream.readInt();
			this.rozmiar = rozmiar;
			tab = new int[rozmiar];

			for(int i=0; i<rozmiar; i++){
				tab[i] = eventStream.readInt();
			}
			eventStream.close();
		}
		catch (FileNotFoundException io)												
			{System.out.println(io.getMessage());}
	
		catch (IOException io)												
			{System.out.println(io.getMessage());} 
	}
}
