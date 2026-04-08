package gestione.decorator;

public class FunghiDecorator extends IngredienteDecorator
{

    public FunghiDecorator(Pizza pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescrizione()
    {
        return pizza.getDescrizione()+" + "+"Mozzarella";
    }
    @Override

    public double getPrezzo()
    {
        return pizza.getPrezzo()+0.30;
    }
}