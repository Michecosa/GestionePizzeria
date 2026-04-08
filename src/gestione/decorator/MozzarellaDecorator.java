package gestione.decorator;

class MozzarellaDecorator extends IngredienteDecorator
{

    public MozzarellaDecorator(Pizza pizza) {
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