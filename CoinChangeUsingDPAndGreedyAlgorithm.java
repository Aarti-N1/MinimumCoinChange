import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/*coinsRequiredForChange[] - contains the change that needs to be computed next.
 *lastCoinUsed[] - contains the last coin used for computing change.
 *denominations[] - contains the possible denominations that can be used.
  * */


public class CoinChangeUsingDPAndGreedyAlgorithm {
	public static int changeAmount;
	public static int[] coinsRequiredForChange;
	public static int[] lastCoinUsed;
	public static int totalNumberOfDenominations;
	public static int[] denominations;

	public static void main(String[] args) {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Enter the amount in dollars for which change is required.");
			changeAmount = (int) (Float.parseFloat(bReader.readLine()) * 100);
			coinsRequiredForChange = new int[changeAmount+1];
			lastCoinUsed = new int[changeAmount+1];
			System.out.println("Enter the total number of denominations that can be used for computing change.");
			totalNumberOfDenominations = Integer.parseInt(bReader.readLine());
			denominations = new int[totalNumberOfDenominations]; 
			System.out.println("Enter the denominations(in cents) that can be used for computing change.");
			for(int i =0; i<totalNumberOfDenominations; i++) {
				denominations[i] = Integer.parseInt(bReader.readLine());
				if(i!= totalNumberOfDenominations-1 ) {
					System.out.println("Enter next denomination. ");
				}
			}
			System.out.println("Do you want to execute the Dynamic Programming Solution? Enter y-Yes and n-No");
			if(bReader.readLine().equalsIgnoreCase("y"))
				calculateMinimumCoinsUsingDP();
			System.out.println("Do you want to execute the Greedy Algorithm Solution? Enter y-Yes and n-No");
			if(bReader.readLine().equalsIgnoreCase("y"))
				calculateMinimumCoinsUsingGreedyAlgorithm();
		} catch (NumberFormatException e) {
			System.out.println("Number Format Exception...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException...");
			e.printStackTrace();
		}
	}
	
	public static void calculateMinimumCoinsUsingDP() {
		coinsRequiredForChange[0]=0;
		lastCoinUsed[0]=0;
		int minCount;
		int coinUsed=0;
		for(int i =1; i<=changeAmount; i++) {		//For each amount
			minCount= Integer.MAX_VALUE;
			for(int j=0;j<totalNumberOfDenominations;j++) {		
				//Calculating amount for each denomination, such that denomination < change required
				if(denominations[j]<= i) {
					int currentCount = 1+coinsRequiredForChange[i-denominations[j]];
					if(minCount>currentCount) {
						minCount= currentCount;
						if(lastCoinUsed[i-denominations[j]]==0)
							coinUsed = denominations[j];
						else
							coinUsed = lastCoinUsed[i-denominations[j]];
					}
				}
			}
			coinsRequiredForChange[i]= minCount;
			lastCoinUsed[i]= coinUsed;
		}
			System.out.println("Minimum number of coins used for change of amount: "+ (float)changeAmount/100 + " dollar/s  minimum change = "+ coinsRequiredForChange[changeAmount] +" coin/s.");
		//Computing list of coins used
		System.out.println("Coins used are ");
		int i=changeAmount;
		while(i!=0) {
			System.out.print(lastCoinUsed[i]);
			i= i-lastCoinUsed[i];
			if(i!=0)
				System.out.print(", ");
		}
	}
	
	public static void calculateMinimumCoinsUsingGreedyAlgorithm() {
		ArrayList<Integer> sortedDenominations= new ArrayList<Integer>();
		ArrayList<Integer> coinsUsedList = new ArrayList<Integer>();
		for(int i =0; i < totalNumberOfDenominations; i++) {
			sortedDenominations.add(denominations[i]);
		}
		Collections.sort(sortedDenominations, Collections.reverseOrder());
		int j = changeAmount;
		for(int i=0; i<totalNumberOfDenominations; i++) {
			while(j >= sortedDenominations.get(i) && j>0) {
				j= j-sortedDenominations.get(i);
				coinsUsedList.add(sortedDenominations.get(i));
			}
		}
		System.out.println("Minimum number of coins used for change of amount: "+ (float)changeAmount/100 + " dollar/s  minimum change = "+ coinsUsedList.size() +" coin/s.");
		System.out.println("List of coins given as change is : ");
		for(int i =0; i<coinsUsedList.size(); i++) {
			System.out.print(coinsUsedList.get(i));
			if(i< coinsUsedList.size()-1) {
				System.out.print(", ");
			}
		}
	}

}
