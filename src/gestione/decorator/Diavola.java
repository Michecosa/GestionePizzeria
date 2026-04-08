package gestione.decorator;

class Diavola implements Pizza
{
     @Override
    public String getDescrizione()
        {
            return "Diavola";
        }
           @Override
         public double getPrezzo()
        {
            return 4.50;
        }
}