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
    }

    public String query(){ //return all ranges
        String output = "";
        for (TaxRange tax: taxes) {
            output+=tax.toString();
        }
        return output;
    }

    public String calculate(int value){
        String output = "I DON’T KNOW "+Integer.toString(value);
        for (int i = taxes.size()-1; i>=0; i--) {
            TaxRange tax = taxes.get(i);
            if(value>tax.min && value<tax.max){
                output=tax.toString();
            }
        }
        return output;
    }
}
