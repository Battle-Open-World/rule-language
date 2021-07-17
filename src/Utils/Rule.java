package Utils;

import java.util.ArrayList;

import Utils.Switch.SwitchStatement;

public class Rule {

	private final ArrayList<SwitchStatement> switches;
	private final String default_data;
	
	public Rule(String default_data, ArrayList<SwitchStatement> switches) {
		this.switches = switches;
		this.default_data = default_data;
	}
	
	public String get_object_name(String[] neighbors) {
		for (SwitchStatement swtch:switches) {
			if (swtch.is_disabled(neighbors)) {
				return null;
			}
			String s;
			if ((s=swtch.get_name(neighbors)) != null) {
				return s;
			}
		}
		return default_data;
	}
	
}
