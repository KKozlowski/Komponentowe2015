public class ArgReader {
	int numberOfArgs;
	String[] args;
	
	public ArgReader(String[] args){
		numberOfArgs = args.length;
		this.args = args;
		for(int i=0; i<numberOfArgs; i++)
			System.out.println("Argument" +i+": " + args[i]);
		if(numberOfArgs >=3)
			oblicz(args[0], args[1], args[2]);
	}
	
	public void oblicz(String one, String two, String sign){
		double pierwsza, druga;
		double wynik;
		try{
			pierwsza = Double.parseDouble(one);
			druga = Double.parseDouble(two);
		} catch(NumberFormatException f){
			System.out.println("Ktorys z argumentow nie jest liczba.");
			return;
		}
		
		if(sign.equals("/"))
			wynik = pierwsza/druga;
		else if(sign.equals("*"))
			wynik = pierwsza*druga;
		else if(sign.equals("+"))
			wynik = pierwsza+druga;
		else if(sign.equals("-"))
			wynik = pierwsza-druga;
		else{
			System.out.println("Nie podano prawidlowego znaku dzialania.");
			return;
		}
		System.out.println(wynik);
	}
}
