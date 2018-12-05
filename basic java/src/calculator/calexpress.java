package calculator;

import java.util.*;

public class calexpress{
	static Map<String, Integer> op = new HashMap<String,Integer>();
	static Scanner input = new Scanner(System.in);
	static Set<String> correctchrset = new HashSet<String>();
	static String[] correctchrs = {"7","8","9","/","(",")",
		     "4","5","6","x","^","!","1","2","3","+", "0",".","-"};
	
	public static void initCollection() {
		//�����������Ӧ�����ȼ�������ֵ��У�����ԽС�����ȼ�Խ��
		op.put(")",0);
		op.put("(", 1);
		op.put("!", 2);
		op.put("^", 2);
		op.put("x", 3);
		op.put("/", 3);
		op.put("+", 4);
		op.put("-", 4);
		
		//������������ּ��뵽������
		for(int i = 0;i < correctchrs.length; i++) {
			correctchrset.add(correctchrs[i]);
		}
	}
	//�Ƚ�op1��op2�����ȼ�����op1������op2�򷵻�true�����򷵻�false
	public static boolean cmpPriority(String op1,String op2) {
		if(op.get(op1) < op.get(op2)) {
			return true;
		}
		return false;
		
	}
	//���м���
	public static String getFloat(String expr,int start) {
		int index = start;
		boolean flag = false;
		char c;
		for(index = start; index < expr.length(); index++) {
			c = expr.charAt(index);
			if(c == '-' || c == '+' || c == 'x' || c == '/' || c == '^' || c == ')' || c == '!') {
				break;
			}		
		}
		
		return (expr.substring(start, index));
	}
	
	//������������һ���������������
	public static float calculate(float num1,float num2,String op) {
		switch(op) {
		case "+":return (num1+num2);
		case "-":return (num1-num2);
		case "x":return (num1*num2);
		case "/":return (num1/num2);
		case "^":return ((float) Math.pow(num1, num2));
		default:return -404040404;
		}
	}
	
	//��������������һ������������������㲢�����ѹ��ջ
	public static float getNumsOp2Cal(ArrayList<String> op,int pop,ArrayList<Float> num,int pnum) {
		//����һ��������
		String operator = op.remove(pop);
		pop--;
		//��������������
		float num1 = num.remove(pnum);
		pnum--;
		float num2 = num.remove(pnum);
		pnum--;
		if((num2 == 0f && operator == "/") || (num1 < 0 && operator == "^")) {
			System.out.println("������󣡣���");
			return -404040404;
		}
		//���������������ջ
		float result = calculate(num2,num1,operator);
		pnum++;
		num.add(pnum,result);	
		return result;
	}
	
	//����׳�
	public static int factorial(int n) {
		if(n == 1) {
			return 1;
		}else {
			return factorial(n-1)*n;
		}
	}
	
	public static float calculate(String express) {
		if(express.isEmpty()) {
			return -404040404;
		}
		initCollection();
		//��������ջ�����ڴ�Ų������Ͳ�����
		int pnumstack = -1;
		int popstack = -1;
		ArrayList<String> opstack = new ArrayList<String>(30);
		ArrayList<Float> numstack = new ArrayList<Float>(50);
		//���ʽ
		//String express = "";
		//express = input.next();
		
		//�Ա��ʽ�������
		int count = 0;
		int len = express.length();
		//float num1 = 0.0f,num2 = 0.0f,result = 0f;
		//String operator = "";
		String tmp = "";
		while(count < len) {
			//�����ȡ�ַ������ʽ�е��ַ�����ת��Ϊ�ַ���
			tmp = String.valueOf(express.charAt(count));
			
			//������ʽ�д��ڷǷ��ַ����˳���
			if(!correctchrset.contains(tmp)) {
				System.out.println("���ʽ�д��ڷǷ��ַ������������룡");
				return -404040404;
			}
			//���������
			if(Character.isDigit(tmp.charAt(0)) || tmp == ".") {
				//System.out.println("in  if");
				tmp = getFloat(express,count);
				pnumstack++;
				numstack.add(pnumstack,Float.parseFloat(tmp));	
				count += tmp.length();
				continue;
			}

			//����������������Ҫ�����ж��Ƿ���Ҫ�������㣬��׼�ǵ�ǰ�������ջ����������ȼ���
			if(!Character.isDigit(tmp.charAt(0))) {
				//�ڽ��������ӽ�ջǰ���ȱȽϵ�ǰ���������ջ���������				
				if(popstack == -1 || opstack.get(popstack).equals("(") || ( cmpPriority(tmp,opstack.get(popstack)) && (!tmp.equals(")")) ) ) {
					//��ջΪ�գ�����tmp��"("������tmp���ȼ�����ջ����������ȼ���tmp����")"����ֱ����ջ
					//��tmp���ȼ�����ջ����������ȼ���tmp��")"������ջ�����Ƕ���������Ű����ı��ʽ���м���
					popstack++;
					opstack.add(popstack,tmp);
				}else if(!cmpPriority(tmp,opstack.get(popstack)) || tmp.equals(")") ) {					
					//����ǰ����������ȼ���������Ҫ����ջ��������Լ�������������������
					//�����ǰ���������)����Ҫһֱ�����������ջ��ջ����(
					if(tmp.equals(")") ) {
						while(popstack >= 0) {
							//��������ջ�в����������������������ջΪ���򷵻ش�����-404040404
							if(popstack == -1 || pnumstack < 0) {
								return -404040404;
							}
							if(opstack.get(popstack).equals("!") && pnumstack >= 0) {
								opstack.remove(popstack);
								popstack--;
								int num = Math.round(numstack.remove(pnumstack));
								if(num < 0) {
									return -404040404;
								}
								float result = factorial(num);
								pnumstack++;
								numstack.add(pnumstack,result);
								
								continue;
							}
							getNumsOp2Cal(opstack,popstack,numstack,pnumstack);
							popstack--;
							pnumstack--;
							pnumstack--;
							pnumstack++;
							if(popstack >= 0 && opstack.get(popstack).equals("(") ) {
								//������(�������������(����
								opstack.remove(popstack);
								popstack--;
								if(count == len-1 ) {
									continue;
								}
								break;
							}else if(popstack < 0 && count != len-1) {
								//�����㵽���Ҳû����(������������Ų�ƥ�䣡
								System.out.println("�����Ų�ƥ�䣡");
								return -404040404;
								//System.exit(-1);
							}
						}	
					}else {
						//һ�����ֻ��Ҫ����������Ͳ�����ֱ����ǰ�ַ��������ȼ���ջ����������ȼ��߻��������ջΪ��
						while( popstack >= 0 && !opstack.get(popstack).equals("(") && !cmpPriority(tmp,opstack.get(popstack)) ){						
							//��������ջ���������ջΪ���򷵻ش�����-404040404
							if(popstack == -1 || pnumstack < 0) {
								return -404040404;
							}
							if(opstack.get(popstack).equals("!") && pnumstack >= 0) {
								opstack.remove(popstack);
								popstack--;
								int num = Math.round(numstack.remove(pnumstack));
								if(num < 0) {
									return -404040404;
								}
								float result = factorial(num);
								pnumstack++;
								numstack.add(pnumstack,result);
								
								continue;
							}
							getNumsOp2Cal(opstack,popstack,numstack,pnumstack);
							popstack--;
							pnumstack--;
							pnumstack--;
							pnumstack++;						
						}
						//������Ϻ���Ҫ��tmp��ջ
						popstack++;
						opstack.add(popstack,tmp);
					}				
				}								
			}
			count++;
		}

		while(popstack != -1) {			
			//��������ջ���������ջΪ���򷵻ش�����-404040404
			if(popstack == -1 || pnumstack < 0) {
				return -404040404;
			}
			if(opstack.get(popstack).equals("!") && pnumstack >= 0) {
				opstack.remove(popstack);
				popstack--;
				int num = Math.round(numstack.remove(pnumstack));
				pnumstack--;
				if(num < 0) {
					return -404040404;
				}
				float result = factorial(num);
				pnumstack++;
				numstack.add(pnumstack,result);
				
				continue;
			}
			getNumsOp2Cal(opstack,popstack,numstack,pnumstack);
			popstack--;
			pnumstack--;
			pnumstack--;
			pnumstack++;
		}
		//test:1+2+3+(2+2)^3+0.5x1+(1+1.5)x(1+1)	
		if(popstack != -1) {
			System.out.println("������������"+"\n"+opstack.toString());		
			return -404040404;
			//System.exit(-1);
		}
		return numstack.get(0);
		//System.out.println(express+" = "+numstack.get(0));
	}
}