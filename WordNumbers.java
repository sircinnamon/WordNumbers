import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;
//import java.math.BigInteger;
public class WordNumbers
{
	public static void main(String[] args)
	{
		int input;
		if(args.length>0)
		{
			input = Integer.parseInt(args[0])%1000000000;
			System.out.println("wordify("+input+") -> " + wordify(input));
		}
		else
		{
			solveChallenge();
		}
	}

	
	public static String wordify(int num)
	{
		//Recursively pop off highest digit and label it, terminating anywhere below 19
		//X hundred XX million, X hundred XX thousand, X hundred XX
		if(num<20)
		{
			switch(num)
			{
				case 19: return "nineteen";
				case 18: return "eighteen";
				case 17: return "seventeen";
				case 16: return "sixteen";
				case 15: return "fifteen";
				case 14: return "fourteen";
				case 13: return "thirteen";
				case 12: return "twelve";
				case 11: return "eleven";
				case 10: return "ten";
				case 9: return "nine";
				case 8: return "eight";
				case 7: return "seven";
				case 6: return "six";
				case 5: return "five";
				case 4: return "four";
				case 3: return "three";
				case 2: return "two";
				case 1: return "one";
				default: return "";
			}
		}
		if(num<100)
		{
			if(num>=90){return "ninety"+wordify(num-90);}
			if(num>=80){return "eighty"+wordify(num-80);}
			if(num>=70){return "seventy"+wordify(num-70);}
			if(num>=60){return "sixty"+wordify(num-60);}
			if(num>=50){return "fifty"+wordify(num-50);}
			if(num>=40){return "forty"+wordify(num-40);}
			if(num>=30){return "thirty"+wordify(num-30);}
			if(num>=20){return "twenty"+wordify(num-20);}
		}
		if(num<1000)
		{
			//112
			//wordify(112-12)
			return wordify((num-(num%100))/100)+"hundred"+wordify(num%100);
		}
		if(num<1000000)
		{
			return wordify((num-(num%1000))/1000)+"thousand"+wordify(num%1000);
		}
		else
		{
			return wordify((num-(num%1000000))/1000000)+"million"+wordify(num%1000000);
		}


	}

	public static int mostSig(int num)
	{
		char[] numberChar = (""+num).toCharArray();
		int sig = Integer.parseInt(""+numberChar[0]);
		sig = sig * pow(10, numberChar.length);
		return sig;
	}

	public static int pow(int x, int y)
	{
		int exp = 1;
		for(int i = 0; i<y;i++)
		{
			exp = exp * x;
		}
		return exp;
	}
	
	public static void solveChallenge()
	{
		//make a List of first 999 word numbers
		ArrayList<Tuple<String, Integer>> tuples = new ArrayList<Tuple<String, Integer>>();
		for(int i = 1; i <= 999; i++)
		{
			tuples.add(new Tuple<String, Integer>(wordify(i),i));
		}
		for(int i = 0; i <= 998; i++)
		{
			tuples.add(new Tuple<String, Integer>((tuples.get(i).x)+"thousand",(i+1)*1000));
			tuples.add(new Tuple<String, Integer>((tuples.get(i).x)+"million",(i+1)*1000000));
		}
		//Credit to rodrigoap -- http://stackoverflow.com/questions/5690537/sorting-a-tuple-based-on-one-of-the-fields
		Comparator<Tuple<String, Integer>> comparator = new Comparator<Tuple<String, Integer>>()
    	{

	        public int compare(Tuple<String, Integer> tupleA,Tuple<String, Integer> tupleB)
	        {
	            return tupleA.x.compareTo(tupleB.x);
	        }

   		};

   		Collections.sort(tuples, comparator);

   		ArrayList<Tuple<Integer, Integer>> lengthval = new ArrayList<Tuple<Integer, Integer>>();
   		for(Tuple<String, Integer> t : tuples)
   		{

   			lengthval.add(new Tuple<Integer, Integer>(t.x.length(), t.y));
   		}
   		long[] solved = solve(51000000000L, 0, lengthval);
   		System.out.println("1: "+(char)solved[0]+"\n2: "+ wordify((int)solved[1]) + "\n3: "+ solved[2]);

		return;
	}

	public static long[] solve(long charsRemaining, long sum, ArrayList<Tuple<Integer, Integer>> tuples)
	{
		for(Tuple<Integer, Integer> t : tuples)
		{
			//System.out.println(wordify(t.y));
			//System.out.println(charsRemaining);
			charsRemaining = charsRemaining-t.x;
			sum = sum + t.y;
			if(charsRemaining<=0){return new long[]{wordify(t.y).charAt(wordify(t.y).length()-1), t.y, sum};}
			if(t.y >= 1000000)
			{
				for(Tuple<Integer, Integer> u : tuples)
				{
					if(u.y < 1000000)
					{
						//System.out.println("**"+t.x+u.x);
						charsRemaining = charsRemaining-(t.x+u.x);
						sum = sum + t.y + u.y;
						if(charsRemaining<=0){return new long[]{wordify(u.y).charAt(wordify(u.y).length()-1), t.y+u.y, sum};}
						if(u.y >= 1000)
						{
							for(Tuple<Integer, Integer> v : tuples)
							{
								if(v.y < 1000)
								{
									//System.out.println("****"+t.x+u.x+v.x);
									charsRemaining = charsRemaining-(t.x+u.x+v.x);
									
									sum = sum + t.y + u.y + v.y;
									if(charsRemaining<=0){return new long[]{wordify(v.y).charAt(wordify(v.y).length()-1), t.y+u.y+v.y, sum};}
								}
							}
						}
					}
				}
			}
			else if(t.y >= 1000)
			{
				for(Tuple<Integer, Integer> u : tuples)
				{
					if(u.y < 1000)
					{
						//System.out.println("**"+t.x+u.x);
						charsRemaining = charsRemaining-(t.x+u.x);
						
						sum = sum + t.y + u.y;
						if(charsRemaining<=0){return new long[]{wordify(u.y).charAt(wordify(u.y).length()-1), t.y+u.y, sum};}
					}
				}
			}
		}
		return new long[]{0,0,0};
	}
}