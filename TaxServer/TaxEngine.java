import java.util.ArrayList;

/**
 * Created by Brendan on 21/08/2017.
 */
public class TaxEngine {
    private ArrayList<TaxRange> taxes = new ArrayList<>();

    public TaxEngine(){

    }

    public void add(String min, String max, String base, String perDollar){
        TaxRange range = new TaxRange(Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(base), Integer.parseInt(perDollar));
        for (int i = 0; i < taxes.size(); i++) {
            if(taxes.get(i).min>range.min){
                taxes.add(i, range);
                return;
            }
        }
        taxes.add(taxes.size(),range);

        for (int i = 0; i < taxes.size() - 1; i++) {
            TaxRange currentT = taxes.get(i);
            TaxRange nextT = taxes.get(i+1);
            if(currentT.min>=nextT.min && currentT.max<=nextT.max){ //new range encompass old range
                System.out.println("Removing");
                taxes.remove(i);
                i--;
            }else if(currentT.max<nextT.min){ //new range cuts tail off old range
                System.out.println("changing tail");
                currentT.max=nextT.min-1;
            }else if(currentT.min<nextT.min && currentT.max>nextT.max){ //new range is inside old range
                System.out.println("splitting ranges");
                TaxRange newRange = new TaxRange(nextT.max+1, currentT.max, currentT.base,currentT.perDollar);
                currentT.max = nextT.min-1;
                taxes.add(i+2, newRange);
            }
        }
    }

    public String query(){ //return all ranges
        String output = "";
        for (TaxRange tax: taxes) {
            output+=tax.toString();
        }
        output+="QUERY: OK";
        return output;
    }

    public String calculate(int value){
        String output = "I DON’T KNOW "+Integer.toString(value);
        for (int i = taxes.size()-1; i>=0; i--) {
            TaxRange tax = taxes.get(i);
            if(value>tax.min && value<tax.max){
                double returnValue = tax.base+(((value-tax.min)*tax.perDollar)/100);
                output=Double.toString(returnValue);
            }
        }
        return output;
    }
}
