package gestione.decorator;

public class SalameDecorator extends IngredienteDecorator
{

    public SalameDecorator(Pizza pizza) {
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