import java.util.Random;

public class SkipListTest {

	public static void main(String[] args) {
		Random r = new Random();
		int i = 0;
		while(i < 11) {
			System.out.println(r.nextInt());
			i++;
		}
	}

}
