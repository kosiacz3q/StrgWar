package StrgWar.map.loader;

public class RawNode {
	public String name;
	public String occupant;
	public int startSize;
	public int unitsPerSecond;
	public int x;
	public int y;
	public int r;

	public RawNode(String name, String occupant, int startSize,
			int unitsPerSecond, int x, int y, int r) {
		this.name = name;
		this.occupant = occupant;
		this.startSize = startSize;
		this.unitsPerSecond = unitsPerSecond;
		this.x = x;
		this.y = y;
		this.r = r;
	}
}
