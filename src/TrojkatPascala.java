public class TrojkatPascala {
	private int liczbaWierszy;
	private int[][] wartosci;

	public TrojkatPascala(){
		Inputter inp = new Inputter();
		liczbaWierszy = inp.GetInt("Ile wierszy trojkata?");
		wartosci = new int[liczbaWierszy][liczbaWierszy];
		wylicz();
	}
	
	public TrojkatPascala(int ile) {
		liczbaWierszy = ile;
		wartosci = new int[liczbaWierszy][liczbaWierszy];
		wylicz();
	}

	public void print() {
		for (int i = 0; i < liczbaWierszy; i++) {
			for (int m = 0; m <= liczbaWierszy / 2 - i + 3; m++)
				System.out.print(' ');

			for (int k = 0; k < i + 1; k++) {
				System.out.print(Integer.toString(wartosci[i][k]) + ' ');
			}
			System.out.println();

		}
	}

	public int silnia(int n) {
		int fin = 1;
		for (int i = 1; i <= n; i++)
			fin *= i;
		return fin;
	}

	public int newton(int n, int k) {
		return silnia(n) / (silnia(k) * silnia(n - k));
	}
	
	private void wylicz(){
		//wartosci[0][0] = 1;
		for (int i = 1; i < liczbaWierszy; i++) {
			wartosci[i][0] = 1;
			wartosci[i][i] = 1;
			for (int k = 1; k < i; k++) {
				wartosci[i][k] = wartosci[i-1][k]+wartosci[i-1][k-1];
			}
		}
	}
}
