package Utils.Switch.Case;

import Utils.Switch.Structures.Range;

public class CaseStatement {

	public final Range value_range;
	public final boolean disable;
	public final String object_name;
	
	public CaseStatement(Range value_range, boolean disable, String object_name) {
		super();
		this.value_range = value_range;
		this.disable = disable;
		this.object_name = object_name;
	}
	
	public boolean is_disabled(int x) {
		return disable && value_range.isIn(x);
	}
	
	public String get_object_name(int x) {
		if (value_range.isIn(x) && !disable) {
			return object_name;
		}
		return null;
	}
	
}
