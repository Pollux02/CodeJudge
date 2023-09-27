import java.util.List;
import java.util.ArrayList;

public class Prueba 
{
    private int id;
    private List<String> entradas;
    private List<String> salidas;
    private boolean buscaOrden;

    public Prueba(int id) 
    {
        this.id = id;
        this.entradas = new ArrayList<>();
        this.salidas = new ArrayList<>();
        this.buscaOrden = false;
    }
    
    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public List<String> getEntradas() 
    {
        return entradas;
    }

    public void setEntradas(List<String> entradas) 
    {
        this.entradas = entradas;
    }
    
    public List<String> getSalidas() 
    {
        return salidas;
    }

    public void setSalidas(List<String> salidas) 
    {
        this.salidas = salidas;
    }
    
    public boolean getBuscaOrden()
    {
    	return buscaOrden;
    }
    
    public void setBuscaOrden(boolean buscaOrden)
    {
    	this.buscaOrden = buscaOrden;
    }
}
