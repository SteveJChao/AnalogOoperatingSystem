package ConstantMemory;

/***
 * 
 * 描述内存碎片
 *
 */
public class Pieces {

	public int start;
	public int size;
	public int end;
	Pieces next = null;
	Pieces prior = null;
	
	
	public Pieces(int start, int size) {
		this.start = start;
		this.size = size;
		end = start + size - 1;
	}
	
	
	
}
