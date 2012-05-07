package de.htwsaarland.mathcoach.astVisual;

import de.htwsaarland.mathcoach.astVisual.AstVertex;


class DummyVertex implements AstVertex
{
	int d; 
	int f;
	String name;
	private AstVertex pred;

	public DummyVertex(String name)
	{
		this.name = name;
		d = 0;
		f = 0;
	}
	
	@Override
	public void setDetected(int d) {
		this.d = d;
	}

	@Override
	public int getDetected() {
		return d;
	}

	@Override
	public void setFinished(int f) {
		this.f = f;
	}

	@Override
	public int getFinished() {
		return f;
	}

	@Override
	public String toString()
	{
		if (pred != null)
		{
			return name + " d="+ d + " f=" + f + "(pred:"+ pred.getName() +")";
		}
		else
		{
			return name + " d="+ d + " f=" + f + "(pred: __null__)";
		}
	}

	@Override
	public void setPred(AstVertex v)
	{
		this.pred = v;
	}

	@Override
	public String getName()
	{
		return name;
	}
}

