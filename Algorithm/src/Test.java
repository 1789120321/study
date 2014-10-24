import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Test {

	public static int[][] STATE_TABLE = new int[8][13];

	// 初始状态和终结状态
	public final static int INIT_STATE = 0;
	public final static int RESULT_STATE = -1;
	public final static int ERROR_STATE = -2;

	// 输入内容
	public final static int LETTER = 1;
	public final static int ZERO = 2;
	public final static int DIGITAL = 3;
	public final static int SYMBOL = 4;
	public final static int LESS_THAN = 5;
	public final static int MORE_THAN = 6;
	public final static int LEFT_BRACE = 7;
	public final static int RIGHT_BRACE = 8;
	public final static int COLON = 9;
	public final static int EQUAL = 10;
	public final static int SPACE = 11;
	public final static int OTHERS = 12;

	// 关键字
	public final static String KEY_WORD_1 = "program";
	public final static String KEY_WORD_2 = "var";
	public final static String KEY_WORD_3 = "int";
	public final static String KEY_WORD_4 = "double";
	public final static String KEY_WORD_5 = "long";
	public final static String KEY_WORD_6 = "String";
	public final static String KEY_WORD_7 = "const";
	public final static String KEY_WORD_8 = "begin";
	public final static String KEY_WORD_9 = "while";
	public final static String KEY_WORD_10 = "do";
	public final static String KEY_WORD_11 = "if";
	public final static String KEY_WORD_12 = "then";
	public final static String KEY_WORD_13 = "begin";
	public final static String KEY_WORD_14 = "end";
	public final static String KEY_WORD_15 = "write";
	public final static String KEY_WORD_16 = "read";

	// 运算符
	public final static String OPERATOR_1 = "+";
	public final static String OPERATOR_2 = "-";
	public final static String OPERATOR_3 = "*";
	public final static String OPERATOR_4 = "/";
	public final static String OPERATOR_5 = "=";
	public final static String OPERATOR_6 = ">";
	public final static String OPERATOR_7 = "<";
	public final static String OPERATOR_8 = ":";
	public final static String OPERATOR_9 = ">=";
	public final static String OPERATOR_10 = "<=";
	public final static String OPERATOR_11 = "!=";
	public final static String OPERATOR_12 = "-=";
	public final static String OPERATOR_13 = "+=";
	public final static String OPERATOR_14 = "==";

	// 分界符
	public final static char DELIMITER_1 = '(';
	public final static char DELIMITER_2 = ')';
	public final static char DELIMITER_3 = ',';
	public final static char DELIMITER_4 = ';';
	public final static char DELIMITER_5 = '.';
	public final static char DELIMITER_6 = '>';
	public final static char DELIMITER_7 = '<';
	public final static char DELIMITER_8 = '=';
	public final static char DELIMITER_9 = '{';
	public final static char DELIMITER_10 = '}';

	private static Set<String> keyWordSet = new HashSet<String>();
	private static Set<String> operatorSet = new HashSet<String>();
	private static Set<Character> delimiterSet = new HashSet<Character>();

	// 初始化状态转换表
	public static void initStateTable() {
		// 行状态
		STATE_TABLE[0][0] = 0;
		STATE_TABLE[1][0] = 1;
		STATE_TABLE[2][0] = 2;
		STATE_TABLE[3][0] = 3;
		STATE_TABLE[4][0] = 4;
		STATE_TABLE[5][0] = 5;
		STATE_TABLE[6][0] = 6;
		STATE_TABLE[7][0] = 7;

		// 列输入
		STATE_TABLE[0][1] = 1;
		STATE_TABLE[0][2] = 2;
		STATE_TABLE[0][3] = 3;
		STATE_TABLE[0][4] = 4;
		STATE_TABLE[0][5] = 5;
		STATE_TABLE[0][6] = 6;
		STATE_TABLE[0][7] = 7;
		STATE_TABLE[0][8] = -2;
		STATE_TABLE[0][9] = 4;
		STATE_TABLE[0][10] = 4;
		STATE_TABLE[0][11] = 0;
		STATE_TABLE[0][12] = -2;

		// 状态转换
		STATE_TABLE[1][1] = 1;
		STATE_TABLE[1][2] = 1;
		STATE_TABLE[1][3] = 1;
		STATE_TABLE[1][4] = -1;
		STATE_TABLE[1][5] = -1;
		STATE_TABLE[1][6] = -1;
		STATE_TABLE[1][7] = -1;
		STATE_TABLE[1][8] = -2;
		STATE_TABLE[1][9] = -1;
		STATE_TABLE[1][10] = -1;
		STATE_TABLE[1][11] = -1;
		STATE_TABLE[1][12] = -2;

		STATE_TABLE[2][1] = -2;
		STATE_TABLE[2][2] = -2;
		STATE_TABLE[2][3] = -2;
		STATE_TABLE[2][4] = -1;
		STATE_TABLE[2][5] = -1;
		STATE_TABLE[2][6] = -1;
		STATE_TABLE[2][7] = -1;
		STATE_TABLE[2][8] = -2;
		STATE_TABLE[2][9] = -1;
		STATE_TABLE[2][10] = -1;
		STATE_TABLE[2][11] = -1;
		STATE_TABLE[2][12] = -2;

		STATE_TABLE[3][1] = -2;
		STATE_TABLE[3][2] = 3;
		STATE_TABLE[3][3] = 3;
		STATE_TABLE[3][4] = -1;
		STATE_TABLE[3][5] = -1;
		STATE_TABLE[3][6] = -1;
		STATE_TABLE[3][7] = -1;
		STATE_TABLE[3][8] = -2;
		STATE_TABLE[3][9] = -1;
		STATE_TABLE[3][10] = -1;
		STATE_TABLE[3][11] = -1;
		STATE_TABLE[3][12] = -2;

		STATE_TABLE[4][1] = -1;
		STATE_TABLE[4][2] = -1;
		STATE_TABLE[4][3] = -1;
		STATE_TABLE[4][4] = -1;
		STATE_TABLE[4][5] = -1;
		STATE_TABLE[4][6] = -1;
		STATE_TABLE[4][7] = -1;
		STATE_TABLE[4][8] = -2;
		STATE_TABLE[4][9] = -1;
		STATE_TABLE[4][10] = 4;
		STATE_TABLE[4][11] = -1;
		STATE_TABLE[4][12] = -2;

		STATE_TABLE[5][1] = -1;
		STATE_TABLE[5][2] = -1;
		STATE_TABLE[5][3] = -1;
		STATE_TABLE[5][4] = -1;
		STATE_TABLE[5][5] = -1;
		STATE_TABLE[5][6] = -1;
		STATE_TABLE[5][7] = -1;
		STATE_TABLE[5][8] = -2;
		STATE_TABLE[5][9] = -1;
		STATE_TABLE[5][10] = 4;
		STATE_TABLE[5][11] = -1;
		STATE_TABLE[5][12] = -2;

		STATE_TABLE[6][1] = -1;
		STATE_TABLE[6][2] = -1;
		STATE_TABLE[6][3] = -1;
		STATE_TABLE[6][4] = -1;
		STATE_TABLE[6][5] = -1;
		STATE_TABLE[6][6] = -1;
		STATE_TABLE[6][7] = -1;
		STATE_TABLE[6][8] = -2;
		STATE_TABLE[6][9] = -1;
		STATE_TABLE[6][10] = 4;
		STATE_TABLE[6][11] = -1;
		STATE_TABLE[6][12] = -2;

		STATE_TABLE[7][1] = 7;
		STATE_TABLE[7][2] = 7;
		STATE_TABLE[7][3] = 7;
		STATE_TABLE[7][4] = 7;
		STATE_TABLE[7][5] = 7;
		STATE_TABLE[7][6] = 7;
		STATE_TABLE[7][7] = 7;
		STATE_TABLE[7][8] = 0;
		STATE_TABLE[7][9] = 7;
		STATE_TABLE[7][10] = 7;
		STATE_TABLE[7][11] = 7;
		STATE_TABLE[7][12] = 7;
	}

	// 初始化关键字集合
	public static void initKeyWord() {
		keyWordSet.add(KEY_WORD_1);
		keyWordSet.add(KEY_WORD_2);
		keyWordSet.add(KEY_WORD_3);
		keyWordSet.add(KEY_WORD_4);
		keyWordSet.add(KEY_WORD_5);
		keyWordSet.add(KEY_WORD_6);
		keyWordSet.add(KEY_WORD_7);
		keyWordSet.add(KEY_WORD_8);
		keyWordSet.add(KEY_WORD_9);
		keyWordSet.add(KEY_WORD_10);
		keyWordSet.add(KEY_WORD_11);
		keyWordSet.add(KEY_WORD_12);
		keyWordSet.add(KEY_WORD_13);
		keyWordSet.add(KEY_WORD_14);
		keyWordSet.add(KEY_WORD_15);
		keyWordSet.add(KEY_WORD_16);
	}

	// 初始化运算符集合
	public static void initOperator() {
		operatorSet.add(OPERATOR_1);
		operatorSet.add(OPERATOR_2);
		operatorSet.add(OPERATOR_3);
		operatorSet.add(OPERATOR_4);
		operatorSet.add(OPERATOR_5);
		operatorSet.add(OPERATOR_6);
		operatorSet.add(OPERATOR_7);
		operatorSet.add(OPERATOR_8);
		operatorSet.add(OPERATOR_9);
		operatorSet.add(OPERATOR_10);
		operatorSet.add(OPERATOR_11);
		operatorSet.add(OPERATOR_12);
		operatorSet.add(OPERATOR_13);
		operatorSet.add(OPERATOR_14);
	}

	// 初始化分界符集合
	public static void initDelimiter() {
		delimiterSet.add(DELIMITER_1);
		delimiterSet.add(DELIMITER_2);
		delimiterSet.add(DELIMITER_3);
		delimiterSet.add(DELIMITER_4);
		delimiterSet.add(DELIMITER_5);
		// delimiterSet.add(DELIMITER_6);
		// delimiterSet.add(DELIMITER_7);
		// delimiterSet.add(DELIMITER_8);
		// delimiterSet.add(DELIMITER_9);
		// delimiterSet.add(DELIMITER_10);
	}

	// 判断是否为字母
	public static boolean isLetter(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			return true;
		}
		return false;
	}

	// 判断是否为非零数字
	public static boolean isNumber(char c) {
		if ((c >= '1' && c <= '9')) {
			return true;
		}
		return false;
	}

	// 判断是否为数字零
	public static boolean isZero(char c) {
		if (c == '0') {
			return true;
		}
		return false;
	}

	// 判断是否为单符号
	public static boolean isSymbol(char c) {
		if (delimiterSet.contains(c)) {
			return true;
		}
		return false;
	}

	// 判断是否为冒号
	public static boolean isColon(char c) {
		if (c == ':') {
			return true;
		}
		return false;
	}

	// 判断是否为等号
	public static boolean isEqual(char c) {
		if (c == '=') {
			return true;
		}
		return false;
	}

	// 判断是否为空格
	public static boolean isSpace(char c) {
		if (c == ' ') {
			return true;
		}
		return false;
	}

	// 判断是否为小于号
	public static boolean isLessThan(char c) {
		if (c == '<') {
			return true;
		}
		return false;
	}

	// 判断是否为大于号
	public static boolean isMoreThan(char c) {
		if (c == '>') {
			return true;
		}
		return false;
	}

	// 判断是否为左花括号
	public static boolean isLeftBrace(char c) {
		if (c == '{') {
			return true;
		}
		return false;
	}

	// 判断是否为右花括号
	public static boolean isRightBrace(char c) {
		if (c == '}') {
			return true;
		}
		return false;
	}

	// 判断是否为特殊符号
	public static boolean isOthers(char c) {
		if (c == '$' || c == '@' || c == '#') {
			return true;
		}
		return false;
	}

	// 判断是否为关键字
	public static boolean isKeyWord(String str) {
		return keyWordSet.contains(str);
	}

	// 判断是否为操作符
	public static boolean isOperator(String str) {
		return operatorSet.contains(str);
	}

	// 设置当前状态
	public static int setCurrentState(int line, int column) {
		return STATE_TABLE[line][column];
	}

	// 输出单词
	public static StringBuffer print(Deque<Character> deque) {
		StringBuffer sb = new StringBuffer();
		while (!deque.isEmpty()) {
			sb.append(deque.pollFirst());
		}
		return sb;
	}

	// 初始化所有内容
	static {
		initStateTable();
		initKeyWord();
		initOperator();
		initDelimiter();
	}

	// 运行
	public static void run(String str) {
		int currentState = INIT_STATE;
		int i = 0;
		char c = ' ';
		Deque<Character> deque = new LinkedList<Character>();
		StringBuffer result = new StringBuffer();
		while (i < str.length()) {
			c = str.charAt(i);
			// 判断是否为字母
			if (isLetter(c)) {
				currentState = setCurrentState(currentState, LETTER);
			}
			// 判断是否为数字零
			else if (isZero(c)) {
				currentState = setCurrentState(currentState, ZERO);
			}
			// 判断是否为非零数字
			else if (isNumber(c)) {
				currentState = setCurrentState(currentState, DIGITAL);
			}
			// 判断是否为单字符
			else if (isSymbol(c)) {
				currentState = setCurrentState(currentState, SYMBOL);
			}
			// 判断是否为冒号
			else if (isColon(c)) {
				currentState = setCurrentState(currentState, COLON);
			}
			// 判断是否为等号
			else if (isEqual(c)) {
				currentState = setCurrentState(currentState, EQUAL);
			}
			// 判断是否为空格
			else if (isSpace(c)) {
				currentState = setCurrentState(currentState, SPACE);
			}
			// 判断是否为小于号
			else if (isLessThan(c)) {
				currentState = setCurrentState(currentState, LESS_THAN);
			}
			// 判断是否为大于号
			else if (isMoreThan(c)) {
				currentState = setCurrentState(currentState, MORE_THAN);
			}
			// 判断是否为左花括号
			else if (isLeftBrace(c)) {
				currentState = setCurrentState(currentState, LEFT_BRACE);
			}
			// 判断是否为右花括号
			else if (isRightBrace(c)) {
				currentState = setCurrentState(currentState, RIGHT_BRACE);
			}
			// 判断是否为特殊符号
			else if (isOthers(c)) {
				currentState = setCurrentState(currentState, OTHERS);
			}

			i++;

			// 对当前状态做处理
			if (currentState > 0) {
				deque.addLast(c);
			} else if (currentState == 0) {
				;
			} else if (currentState == -1) {
				// 输出正确内容
				result.append(print(deque) + " ");
				if (i != 0 && i % 20 == 0)
					result.append("\n");
				currentState = 0;
				i--;
			} else if (currentState == -2) {
				// 输出错误信息
				result.append("\n错误单词：");
				currentState = 0;
				i--;
			}
		}
	}

	public static void main(String[] args) {
		Test.run("dd2<>=>=>>");
		String a = null;
		System.out.println(a == null);
	}
}
