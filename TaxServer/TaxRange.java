/**
 * Created by Brendan on 21/08/2017.
 *
 * Class used to store the tax range information
 *
 */
public class TaxRange {

    public int min;
    public int max;
    public int base;
    public int perDollar;


    public TaxRange(int min, int max, int base, int perDollar){
        this.min = min;
        this.max = max;
        this.base = base;
        this.perDollar = perDollar;
    }

    public String toString(){
        //return the information in the range
        return Integer.toString(min)+" "+(max==Integer.MAX_VALUE?"~":Integer.toString(max))+
                " "+Integer.toString(base)+" "+Integer.toString(perDollar)+"\n";
    }

}
