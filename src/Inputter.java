import javax.swing.JOptionPane;


public class Inputter {
	public int GetInt(String request){
		String ile;
		ile = JOptionPane.showInputDialog(request);
		return Integer.parseInt(ile);
	}
	
	public String GetString(String request){
		String str;
		str = JOptionPane.showInputDialog(request);
		return str;
	}
}
