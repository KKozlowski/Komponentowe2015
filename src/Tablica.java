import java.util.Arrays;
//import java.util.Date;

//LAB2
public class Tablica {
	int[] pierwsza = {1,2,3,4,5};
	
	int dlugoscDrugiej = 15;
	int[] druga = new int[dlugoscDrugiej];
	
	int [] randomowa;
	public Tablica(){

		for(int i = 0; i< druga.length; i++){
			druga[i] = 2*i+1;
		}
		
		WypiszIntTab(druga);
	}
	
	public void WypiszIntTab(int[] tab){
		for(int i : tab){
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
	public void Randomizuj(int zakresStart, int zakresEnd){
		Inputter inp = new Inputter();
		int rozmiar = inp.GetInt("Ile liczb wylosowac?");
		randomowa = new int[rozmiar];
		for(int i = 0; i< randomowa.length; i++)
			randomowa[i] = (int)(Math.random()*(zakresEnd-zakresStart) + zakresStart);
		
		WypiszIntTab(randomowa);
		int[] kopia=randomowa.clone();
		long przed, po;
		
		System.out.println("SORTOWANIE: Arrays.sort");
		przed = System.nanoTime();
		Arrays.sort(randomowa);
		po = System.nanoTime();
		
		System.out.println("Czas operacji "+(po - przed)/1000 + " mikrosekund");
		WypiszIntTab(randomowa);
		
		System.out.println();
		System.out.println("SORTOWANIE: Babelkowa metoda");
		przed = System.nanoTime();
		babelekInt(kopia);
		po = System.nanoTime();
		System.out.println("Czas operacji "+(po - przed)/1000 + " mikrosekund");
		WypiszIntTab(kopia);
	}
	
	public void babelekInt(int[] tab) {
	    boolean zmiana = false;
	    do {
	        zmiana = false;
	        for (int a = 0; a < tab.length - 1; a++) {
	            if (tab[a] > tab[a + 1]) {
	                int tmp = tab[a];
	                tab[a] = tab[a + 1];
	                tab[a + 1] = tmp;
	                zmiana = true;
	            }
	        }
	    } while (zmiana);
	}
}
