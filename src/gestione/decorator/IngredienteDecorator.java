package gestione.decorator;

//classe astratta
abstract class IngredienteDecorator implements Pizza
{   
    protected Pizza pizza;

    public IngredienteDecorator(Pizza pizza) 
    {
        this.pizza=pizza;
    }
}