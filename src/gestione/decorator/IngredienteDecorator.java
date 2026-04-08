package gestione.decorator;

//classe astratta
public abstract class IngredienteDecorator implements Pizza
{   
    protected Pizza pizza;

    public IngredienteDecorator(Pizza pizza) 
    {
        this.pizza=pizza;
    }
}