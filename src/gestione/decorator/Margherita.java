package gestione.decorator;

public class Margherita implements Pizza
{

     @Override
    public String getDescrizione()
          {
            return "Margherita";
          }
           @Override
         public double getPrezzo()
         {
            return 3.00;
         }
    

}