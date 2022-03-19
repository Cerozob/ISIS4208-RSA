import javax.management.monitor.GaugeMonitor;
import java.math.BigInteger;
import java.util.Random;
import java.math.*;

public class TareaRSA{

    public class RSAAlgorithm{
        private BigInteger p;
        private BigInteger q;
        private BigInteger n;
        private BigInteger m;
        private BigInteger e;
        private BigInteger d;

        public void pickRandomLargePrimes_CalculateNandM(){           
            //Find p
            BigInteger maxLimit = new BigInteger("1000000000");
            BigInteger minLimit = new BigInteger("1000000");
            BigInteger bigInteger = maxLimit.subtract(minLimit);
            int len = maxLimit.bitLength();

            Random randNum = new Random();           
            BigInteger generatedBigInteger = new BigInteger(len, randNum);
            if (generatedBigInteger.compareTo(minLimit) < 0)
                generatedBigInteger = generatedBigInteger.add(minLimit);
            if (generatedBigInteger.compareTo(bigInteger) >= 0)
                generatedBigInteger = generatedBigInteger.mod(bigInteger).add(minLimit);
                
            while(!isPrime(generatedBigInteger)){
               randNum = new Random();
               generatedBigInteger = new BigInteger(len, randNum);
               if (generatedBigInteger.compareTo(minLimit) < 0)
                generatedBigInteger = generatedBigInteger.add(minLimit);
               if (generatedBigInteger.compareTo(bigInteger) >= 0)
                generatedBigInteger = generatedBigInteger.mod(bigInteger).add(minLimit);
            }
            p = generatedBigInteger;      
            System.out.println("p is: " + p);

            //Find q
            randNum = new Random();
            generatedBigInteger = new BigInteger(len, randNum);
            if (generatedBigInteger.compareTo(minLimit) < 0)
             generatedBigInteger = generatedBigInteger.add(minLimit);
            if (generatedBigInteger.compareTo(bigInteger) >= 0)
             generatedBigInteger = generatedBigInteger.mod(bigInteger).add(minLimit);
            while(!isPrime(generatedBigInteger)){
                randNum = new Random();
                generatedBigInteger = new BigInteger(len, randNum);
                if (generatedBigInteger.compareTo(minLimit) < 0)
                 generatedBigInteger = generatedBigInteger.add(minLimit);
                if (generatedBigInteger.compareTo(bigInteger) >= 0)
                 generatedBigInteger = generatedBigInteger.mod(bigInteger).add(minLimit);
            }
            q = generatedBigInteger;      
            System.out.println("q is: " + q);
            
            //Find n
            n = p.multiply(q);
            System.out.println("n is: " + n);

            //Find m
            m = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
            System.out.println("m is: " + m);
        }

        public void pickE(){
            e = BigInteger.valueOf(0);
            boolean found = false;
            for(BigInteger i = BigInteger.valueOf(3); i.compareTo(m) == -1 && found ==false ;i=i.add(BigInteger.valueOf(2))){
                if(gcd1(m, i).compareTo(BigInteger.valueOf(1)) == 0){
                    e = i;
                    found = true;
                }
            }
            System.out.println("e is: " + e);
        }

        public void pickD(){
            BigInteger[] solvedLineanComb = solveModularEquation(e, BigInteger.valueOf(1), m);
            /*
            for(int i=0; i<solvedLineanComb.length;i++){
                System.out.println("linealCombPos"+i+": "+solvedLineanComb[i]);
            }*/
            
            d = solvedLineanComb[0];
            System.out.println("d is: " + d);
        }

        public void printKeys(){
            System.out.println("Las llaves para hacer la encripcion/ llave publica P(X) son: (" + e + "," + n +")");
            System.out.println("Las llaves para hacer la desencripcion/ llave privada S(X) son: (" + d + "," + n +")");
        }

        public boolean isPrime(BigInteger n){
            if(n==BigInteger.valueOf(2)){
                return true;
            }
            if(n.mod(BigInteger.valueOf(2))==BigInteger.valueOf(0)){
                return false;
            }
            for(BigInteger i= BigInteger.valueOf(3);i.compareTo(n.sqrt()) == -1 || i.compareTo(n.sqrt()) == 0 ;i = i.add(BigInteger.valueOf(2))){
                if(n.mod(i)==BigInteger.valueOf(0)){
                    return false;
                }
            }
            return true;
        }

        public BigInteger gcd1(BigInteger a, BigInteger b) {
            if(b== BigInteger.valueOf(0)) return a;
            return gcd1(b,a.mod(b));
        }
        public BigInteger [] gcd2(BigInteger a, BigInteger b) {
            if(b==BigInteger.valueOf(0)) {
            BigInteger [] answer = {a,BigInteger.valueOf(1),BigInteger.valueOf(0)};
            return answer;
            }
            BigInteger [] answerp = gcd2(b,a.mod(b));
            BigInteger q = a.divide(b);
            BigInteger [] answer = {answerp[0],answerp[2],answerp[1].subtract(q.multiply(answerp[2]))};
            return answer;
            }

        public BigInteger [] solveModularEquation(BigInteger a, BigInteger b, BigInteger n) {
            BigInteger [] extgcd = gcd2(a, n);
            BigInteger d = extgcd[0];
            BigInteger x1=extgcd[1];
            if((b.mod(d)).compareTo(BigInteger.valueOf(0)) == 1) {
            return new BigInteger [0];
            }
            BigInteger q = b.divide(d);
            BigInteger q2 = n.divide(d);
            BigInteger [] answer = new BigInteger [d.intValue()];
            answer[0] = (x1.multiply(q)).mod(n);
            for(BigInteger i= BigInteger.valueOf(1);i.compareTo(d)==-1;i=i.add(BigInteger.valueOf(1))) {
            answer[i.intValue()] = ((i.multiply(q2)).add(answer[0])).mod(n);
            }
            return answer;
            }
    }   

    public static void main(String[] args) { 
        TareaRSA instance = new TareaRSA();  
        RSAAlgorithm rsaAlgorithm = instance.new RSAAlgorithm();
        //PASO 1 Y 2
        rsaAlgorithm.pickRandomLargePrimes_CalculateNandM();  
        //PASO 3
        rsaAlgorithm.pickE();
        //PASO 4
        rsaAlgorithm.pickD();
        //PASO 5 Y 6
        rsaAlgorithm.printKeys();
    }
}