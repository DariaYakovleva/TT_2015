import java.util.ArrayList;
import java.util.List;


public class LambdaParser {

	private final char LAM = '\\';
//	private final char DOT = '.';
	private final char OPEN = '(';
	private final char QUOTE = '\'';

	private String lexem;
	private int nextperm;
	Expression res;
	private List<String> variables = new ArrayList<String>();
	

	private Expression atom() {
		while (lexem.charAt(nextperm) == ' ') nextperm++;
		Expression a;
		if (lexem.charAt(nextperm) >= 'a' && lexem.charAt(nextperm) <= 'z') {
			String val = "";
			while ((lexem.charAt(nextperm) >= 'a' && lexem.charAt(nextperm) <= 'z') || 
					(lexem.charAt(nextperm) >= '0' && lexem.charAt(nextperm) <= '9')
				|| (lexem.charAt(nextperm) == QUOTE)){
				val += lexem.charAt(nextperm);
				nextperm++;
			}
			a = new Variable(val);
			if (!variables.contains(val)) variables.add(val);
		}  else if (lexem.charAt(nextperm) == OPEN) {
			nextperm++;
			a = expr();
			nextperm++;
		} else {
			a = null;
		}
		while (lexem.charAt(nextperm) == ' ') nextperm++;
		return a;
	}
	
	private Expression application() {
		Expression a = atom();
		Expression b = atom();
//		while (lexem.charAt(nextperm) == OPEN || (lexem.charAt(nextperm) >= 'a' || lexem.charAt(nextperm) <= 'z')) {
		while (b != null) {
			a = new Application(a, b);
			b = atom();
		}
		return a;
	}

	private Expression expr() {
		Expression a = application();
		while (lexem.charAt(nextperm) == ' ') nextperm++;
		if (lexem.charAt(nextperm) == LAM) {
			nextperm++;
			Expression var = atom();
			nextperm++;
			Expression b = new Lambda(var, expr());
			if (a == null) return b;
			a = new Application(a, b);
		}
		if (a == null) System.err.println("a = NULL, cur = " + lexem.charAt(nextperm) + ", num = " + nextperm);
		return a;
	}

	LambdaParser(String a) {
		nextperm = 0;
		lexem = a.replaceAll("\\s", " ").concat("$");
		res = expr();
	}
	
	public List<String> getVariables() {
		return variables;
	}

	public static Expression parse(String a) {
		return (new LambdaParser(a)).res;
	}

}
