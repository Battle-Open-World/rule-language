package Utils.Switch.Structures;

public class Range {
	
	public final int min;
	public final int max;
	
	public Range(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	public Range(String s) {
		String[] data = s.split("-");
		if (data.length == 1) {
			s = (data[0]+"-"+data[0]);
			data = s.split("-");
		}
		
		if (data[0].equals("I")) {
			data[0] = String.valueOf(Integer.MIN_VALUE);
		}if (data[1].equals("I")) {
			data[1] = String.valueOf(Integer.MAX_VALUE);
		}
		
		this.min = Integer.parseInt(data[0]);
		this.max = Integer.parseInt(data[1]);
	}

	public boolean isIn(int x) {
		return min <= x && x <= max;
	}
	
}
