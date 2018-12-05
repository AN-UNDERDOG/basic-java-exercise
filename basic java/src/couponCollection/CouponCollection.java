package couponCollection;

public class CouponCollection{
	static int MAX_GET = 1000;
	
	public static void main(String[] args) {
		/*
		 * cards:���ڱ�ʾһ��52�ŵ��˿���
		 * alreadyget:��ʾ�Ѿ��õ��Ļ�ɫ
		 * count:�Ѿ����г�ȡ�Ĵ���
		 */
		
		int[] cards = new int[52];
		int[] alreadyget = new int[] {-1,-1,-1,-1};
		int count= 0;
		int num= -1;
		int[] tempget = new int[] {-1,-1};
		//Ϊÿ���Ʊ��Ϊ1��52
		for(int i = 0; i < cards.length; i++) {
			cards[i] = i+1;
			
		}
		
		for(count = 1; count <= MAX_GET; count++) {
			num = getRandomCard(cards);
			tempget = mapPoker2Num(num);
			if(tempget[0] == -1 || tempget[1] == -1) {
				continue;
			}
			if(alreadyget[tempget[0]-1] < 1 || alreadyget[tempget[0]-1] > 4) {
				alreadyget[tempget[0]-1] = tempget[1];
			}
			if ( isCollectionFinish(alreadyget) ) {
				break;
			}
			System.out.println(""+cards[0]);
		}
		
		printResult(alreadyget, count);
		
		return;
	}
	
	
	public static int[] mapPoker2Num(int num){
		/*
		 * num:��Ҫ����ӳ������֣���Χ��[1,52]
		 * return:����һ��һά�ĳ���Ϊ2����������info[2]:
		 * 	info[0]:�������ڻ�ɫ������
		 *  info[1]:���ֶ�Ӧ������ֵ
		 *  ��num����[1,52]��Χ���򷵻�{-1,-1}
		 */
		if( num < 1 || num > 52) {
			return new int[] {-1, -1};
		}
		
		int kind = getPokerKind(num);
		int result = getPokerPoint(num);
		
		return new int[] {kind, result};
	}
	
	
	public static int getPokerPoint(int num) {
		/*
		 * num:��Ҫת��������ֵ�����֣���Χ��[1,52]
		 * return:���ظ����ֶ�Ӧ������ֵ,���㹫ʽΪ value = (num-1) % 13 + 1
		 */
		return (num >=1 && num <= 52) ? (num-1) % 13 + 1 : -1;
	}
	
	public static int getPokerKind(int num) {
		/*
		 * num:��Ҫת��������ֵ�����֣���Χ��[1,52]
		 * return:���ظ����ֶ�Ӧ�Ļ�ɫ���࣬���㹫ʽΪ kind = (num-1) / 13
		 */
		return (num >=1 && num <= 52) ? (num-1) / 13 + 1 : -1;
	}
	
	public static int getRandomCard(int[] cards) {
		/*
		 * cards:�������飬�����˿���
		 * return:����ϴ�ƺ������õ����Ƶı��
		 */
		shuffleCards(cards);
		int pos = getRandomNum();
		return cards[pos-1];
	}
	
	public static void shuffleCards(int[] cards) {
		/*
		 * cards:�������飬����ӦΪ52�������˿���
		 */
		int temp = -1;
		int pos = -1;
		for(int i = 0; i < cards.length; i++) {
			pos = getRandomNum();
			temp = cards[i];cards[i] = cards[pos-1];cards[pos-1] = temp;
		}
		return;
	}
	
	public static int getRandomNum() {
		/*
		 * return:����һ����������Χ��[1,52]
		 */
		return ((int)( Math.random() * 52 + 1) );
	}
	
	public static boolean isCollectionFinish(int[] alreadyget) {
		/*
		 * alreadyget:�������飬����Ϊ4���±�+1 ��Ϊ��ɫ�����±�alreadyget[i] ��[1,4]��Χ�����ʾ�û�ɫ�Ѿ��õ���
		 * 			  ��already[i]��ֵΪ��һ�εõ��û�ɫʱ������ֵ
		 */
		for(int i = 0; i < alreadyget.length; i++) {
			if(alreadyget[i] < 1 || alreadyget[i] > 4) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void printResult(int[] alreadyget, int count) {
		/*
		 * alreadyget:��ʾ��һ�εõ��û�ɫʱ������ֵ
		 * count:��ʾ��ȡ�Ĵ���
		 */
		String[] kinds = new String[] {"Spades", "Hearts", "Diamond", "Clubs"};
		String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "Jack", "Queen", "King"};
		if(count > MAX_GET) {
			System.out.println("Failed to get the four kinds cards in " + MAX_GET + "times!");
			count = MAX_GET;
		}
		for(int i = 0; i < alreadyget.length; i++) {
			if(alreadyget[i] < 1 || alreadyget[i] > 4) {
				System.out.println("failed to get " + kinds[i]);
			}else {
				System.out.println(values[alreadyget[i]] + " of " + kinds[i]);
			}
		}
		
		System.out.println("Number of picks: " + count);
		
	}
	
}