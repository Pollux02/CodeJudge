public class CodigoAlumno 
{
	private String nickName;
	private String rutaCodigo;
	private int calificacion;
	private String faltas;
	private int numVersiones;
	
	public CodigoAlumno() 
    {
        this.nickName = "";
        this.rutaCodigo = "";
        this.calificacion = 0;
        this.faltas = "";
        this.numVersiones = 0;
    }
	
	public CodigoAlumno(String nickName, String rutaCodigo, int calificacion, String faltas) 
    {
        this.nickName = nickName;
        this.rutaCodigo = rutaCodigo;
        this.calificacion = calificacion;
        this.faltas = faltas;
        this.numVersiones = 0;
    }
	
	public String getNickName()
	{
		return nickName;
	}
	
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	
	public String getRutaCodigo()
	{
		return rutaCodigo;
	}
	
	public void setRutaCodigo(String rutaCodigo)
	{
		this.rutaCodigo = rutaCodigo;
	}
	
	public int getCalificacion()
	{
		return calificacion;
	}
	
	public void setCalificacion(int calificacion)
	{
		this.calificacion = calificacion;
	}
	
	public String getFaltas()
	{
		return faltas;
	}
	
	public void setFaltas(String faltas)
	{
		this.faltas = faltas;
	}
	
	public int getNumVersiones()
	{
		return numVersiones;
	}
	
	public void setNumVersiones(int numVersiones)
	{
		this.numVersiones = numVersiones;
	}
}
