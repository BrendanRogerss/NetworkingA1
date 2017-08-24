import java.util.ArrayList;

/**
 * Created by Brendan on 21/08/2017.
 */
public class TaxEngine {
    private ArrayList<TaxRange> taxes = new ArrayList<>();

    public TaxEngine(){

    }

    public void add(String min, String max, String base, String perDollar){
        TaxRange newTax = new TaxRange(Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(base), Integer.parseInt(perDollar));
        //remove any ranges that get consumed or insert the new value if it is in the middle of another
        for (int i = 0; i < taxes.size(); i++) {
            TaxRange currentTax = taxes.get(i);
            if(currentTax.min<newTax.min && currentTax.max>newTax.max){ //split
                TaxRange rightCurrent = new TaxRange(newTax.max+1, currentTax.max, currentTax.base, currentTax.perDollar);
                currentTax.max = newTax.min-1;
                taxes.add(i+1,newTax);
                taxes.add(i+2,rightCurrent);
                return;
            }if(newTax.min < currentTax.min && newTax.max > currentTax.max){//remove
                taxes.remove(i);
                i--;
            }
        }

        for (int i = 0; i < taxes.size(); i++) {
            TaxRange currentTax = taxes.get(i);
            if(newTax.max>currentTax.min && newTax.max < currentTax.min){ //shorten left side of current
                currentTax.min = newTax.max+1;
                taxes.add(i,newTax);
                return;
            }else if(newTax.min>currentTax.min && newTax.min<currentTax.max && newTax.max > currentTax.max){//cuts off the right half
                currentTax.max = newTax.min-1;
                if(i+1 < taxes.size() && taxes.get(i+1).min < newTax.max){ //cuts off
                    taxes.get(i+1).min = newTax.max+1;
                }
                taxes.add(i+1, newTax);
                return;
            }
        }

        for (int i = 0; i < taxes.size(); i++) {
            TaxRange currentTax = taxes.get(i);
            if(newTax.min<currentTax.min){
                taxes.add(i,newTax);
                return;
            }

        }
        taxes.add(newTax);

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
                output=Integer.toString((int)returnValue);
            }
        }
        return output;
    }
}
