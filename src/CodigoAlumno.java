public class CodigoAlumno 
{
	private String nickName;
	private String rutaCodigo;
	private float calificacion;
	
	public CodigoAlumno() 
    {
        this.nickName = "";
        this.rutaCodigo = "";
        this.calificacion = 0;
    }
	
	public CodigoAlumno(String nickName, String rutaCodigo, float calificacion) 
    {
        this.nickName = nickName;
        this.rutaCodigo = rutaCodigo;
        this.calificacion = calificacion;
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
	
	public float getCalificacion()
	{
		return calificacion;
	}
	
	public void setCalificacion(float calificacion)
	{
		this.calificacion = calificacion;
	}
}
