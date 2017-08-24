import java.util.ArrayList;

/**
 * Created by Brendan on 21/08/2017.
 */
public class TaxEngine {
    private ArrayList<TaxRange> taxes = new ArrayList<>();

    public TaxEngine(){

    }

    public void add(String min, String max, String base, String perDollar){
        TaxRange newTax = new TaxRange(Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(base), Integer.parseInt(perDollar)); //declare new tax range
        //remove any ranges that get consumed or insert the new value if it is in the middle of another
        for (int i = 0; i < taxes.size(); i++) {
            TaxRange currentTax = taxes.get(i);
            if(currentTax.min<=newTax.min && currentTax.max>=newTax.max){ //if the new range is in the middle of a current range
                TaxRange rightCurrent = new TaxRange(newTax.max+1, currentTax.max, currentTax.base, currentTax.perDollar); //create new tax range
                currentTax.max = newTax.min-1; //change max of old tax
                taxes.add(i+1,newTax);//add new tax range
                taxes.add(i+2,rightCurrent);//add new tax range
                return;
            }if(newTax.min <= currentTax.min && newTax.max >= currentTax.max){//if the new tax consumes another
                taxes.remove(i);//remove it from the list
                i--;
            }
        }

        for (int i = 0; i < taxes.size(); i++) { //loop over ranges again now that any that would be consumed are gone
            TaxRange currentTax = taxes.get(i);
            //if new range intersects the start of another
            if(newTax.max>=currentTax.min && newTax.max <= currentTax.max){ //shorten left side of current
                currentTax.min = newTax.max+1;
                taxes.add(i,newTax);
                return;
            //if the new range intersects the end of another
            }else if(newTax.min>=currentTax.min && newTax.min<=currentTax.max && newTax.max > currentTax.max){//cuts off the right half
                currentTax.max = newTax.min-1;
                //if it also intersects the start of another
                if(i+1 < taxes.size() && taxes.get(i+1).min <= newTax.max){ //cuts off
                    taxes.get(i+1).min = newTax.max+1;
                }
                taxes.add(i+1, newTax);
                return;
            }
        }
        //if the range hasnt yet been added, add it in the correct position
        for (int i = 0; i < taxes.size(); i++) {
            TaxRange currentTax = taxes.get(i);
            if(newTax.min<currentTax.min){
                taxes.add(i,newTax);
                return;
            }

        }
        //if it still wasnt added or is the first
        taxes.add(newTax);

    }

    public String query(){ //return all ranges
        String output = "";
        for (TaxRange tax: taxes) { //loop over all ranges
            output+=tax.toString(); //get their information
        }
        output+="QUERY: OK"; //final message from the server
        return output; //return them
    }

    public String calculate(int value){
        String output = "I DON’T KNOW "+Integer.toString(value);
        //check all ranges
        for (int i = taxes.size()-1; i>=0; i--) {
            TaxRange tax = taxes.get(i);
            if(value>tax.min && value<tax.max){ //if the value is within the range
                double returnValue = tax.base+(((value-tax.min)*tax.perDollar)/100); //perform calculation
                output="Tax is: "+Integer.toString((int)returnValue); //override output
            }
        }
        return output; //return output
    }
}
