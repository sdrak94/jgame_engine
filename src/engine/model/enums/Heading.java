package engine.model.enums;

public enum Heading
{
	UP(0, -1),
	DO(0, +1),
	LE(-1, 0),
	RI(+1, 0),
	
	UP_LE(UP, LE),
	UP_RI(UP, RI),
	DO_LE(DO, LE),
	DO_RI(DO, RI);
	
	private final int _xmul;
	private final int _ymul;
	
	private Heading(int xmul, int ymul)
	{
		_xmul = xmul;
		_ymul = ymul;
	}
	
	private Heading(Heading h1, Heading h2)
	{
		_xmul = h1._xmul + h2._xmul;
		_ymul = h1._ymul + h2._ymul;
	}
	
	public int getMuX()
	{
		return _xmul;
	}
	
	public int getMuY()
	{
		return _ymul;
	}
	
	public int getMuXR()
	{
		return -_xmul;
	}
	
	public int getMuYR()
	{
		return -_ymul;
	}
}
