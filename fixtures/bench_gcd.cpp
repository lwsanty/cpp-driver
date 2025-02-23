static void Main(string[] args)
{
	Console.WriteLine("GCD of {0} and {1} is {2}", 1, 1, gcd(1, 1));
	Console.WriteLine("GCD of {0} and {1} is {2}", 1, 10, gcd(1, 10));
	Console.WriteLine("GCD of {0} and {1} is {2}", 10, 100, gcd(10, 100));
	Console.WriteLine("GCD of {0} and {1} is {2}", 5, 50, gcd(5, 50));
	Console.WriteLine("GCD of {0} and {1} is {2}", 8, 24, gcd(8, 24));
	Console.WriteLine("GCD of {0} and {1} is {2}", 36, 17, gcd(36, 17));
	Console.WriteLine("GCD of {0} and {1} is {2}", 36, 18, gcd(36, 18));
	Console.WriteLine("GCD of {0} and {1} is {2}", 36, 19, gcd(36, 19));
	for (int x = 1; x < 36; x++)
	{
		Console.WriteLine("GCD of {0} and {1} is {2}", 36, x, gcd(36, x));
	}
	Console.Read();
}
 
// Greatest Common Denominator using Euclidian Algorithm
// Gist: https://gist.github.com/SecretDeveloper/6c426f8993873f1a05f7
static int gcd(int a, int b)
{	
	return b==0 ? a : gcd(b, a % b);
}
 
