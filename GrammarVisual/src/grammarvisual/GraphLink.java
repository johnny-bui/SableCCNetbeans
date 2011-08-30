package grammarvisual;

/**
 *
 * @author phucluoi
 */
class GraphLink 
{
	private TYPE type ;
	public GraphLink() 
	{
		type = TYPE.NONE;
	}
	
	public void setType(TYPE type)
	{
		this.type = type;
	}
	
	public TYPE getType()
	{
		return type;
	}
	
	public static enum TYPE
	{
		TREE,
		FORWARD,
		BACKWARD,
		CROSS,
		NONE
	}
}
