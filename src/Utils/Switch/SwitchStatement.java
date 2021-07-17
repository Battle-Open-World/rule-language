package Utils.Switch;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import Utils.Switch.Case.CaseStatement;
import Utils.Switch.Structures.Range;
import Utils.Switch.Structures.WeightedName;

public class SwitchStatement {

	private boolean build_successful;
	public boolean get_build_successful() {
		return build_successful;
	}
	
	private Range[] neighbor_ranges;
	private boolean build_ranges(String msg) {
		msg = msg.replace("N", "");
		
		String[] datas = msg.split("\\|");
		neighbor_ranges = new Range[datas.length];
		
		int i = 0;
		for (String data:datas) {
			if ((data.length() != 3 || data.charAt(1) != '-') && data.length() != 1) {
				System.out.println("Warning the rule that you are trying to use has issues with the neighbor range data, you should have 0 or 1 '-' in the message and neighbirs should be between 0 and 9");
				return false;
			}
			if (data.length() == 3) {
				neighbor_ranges[i] = new Range(
							Integer.parseInt(String.valueOf(data.charAt(0))),
							Integer.parseInt(String.valueOf(data.charAt(2)))
						);
			} else {
				int x = Integer.parseInt(String.valueOf(data.charAt(0)));
				neighbor_ranges[i] = new Range(
						x,
						x
					);
			}
			i++;
		}
		
		return true;
	}
	
	private WeightedName[] weighted_names;
	private boolean build_weights(String msg) {
		String[] datas = msg.split("\\|");
		weighted_names = new WeightedName[datas.length];
		
		int i = 0;
		for (String data:datas) {
			String[] name_and_weight = data.split("-");
			if (name_and_weight.length != 2) {
				System.out.println("Warning, there should be 1 '-' in the config message separated xith |, found "+(name_and_weight.length - 1));
				return false;
			}
			weighted_names[i] = new WeightedName(name_and_weight[0], Integer.parseInt(name_and_weight[1]));
			
			i++;
		}
		
		return true;
	}
	
	private List<CaseStatement> case_statements = new ArrayList<CaseStatement>();
	public void build_case(String msg) {
		msg = msg.replace(" CASE ", "");
		
		CaseStatement c = null;
		
		if (msg.contains(" : ")) {
			String[] data = msg.split(" : ");
			c = new CaseStatement(new Range(data[0]), false, data[1]);
		} else {
			String[] data = msg.split(" ");
			if (data.length >= 2 && data[1].equals("DISABLE")) {
				c = new CaseStatement(new Range(data[0]), true, "");
			}
		}
		
		if (c == null) {
			System.out.println("Warning could not recognize the following case statement \" CASE "+msg+"\"");
			build_successful = false;
		} else {
			case_statements.add(c);
		}
	}
	
	public SwitchStatement(String msg) {
		String[] switch_data = msg.replace("SWITCH ", "").split(":");
		if (switch_data.length != 2) {
			System.out.println("Expected one ':' in switch expression, got "+(switch_data.length));
			build_successful = false;
			return;
		}
		
		if (!build_ranges(switch_data[0])) {
			build_successful = false;
			return;
		}
		if (!build_weights(switch_data[1])) {
			build_successful = false;
			return;
		}
		
		build_successful = true;
	}
	
	public int calculate_weight(String[] neighbors) {
		int data = 0;
		
		for (Range r:neighbor_ranges) {
			for (int i = r.min; i <= r.max; i++) {
				for (WeightedName name:weighted_names) {
					if (name.name.equals(neighbors[i])) {
						data += name.weight;
					}
				}
			}
		}
		
		return data;
	}
	
	public boolean is_disabled(String[] neighbors) {
		int weight = calculate_weight(neighbors);
		
		for (CaseStatement c_statement:case_statements) {
			if (c_statement.is_disabled(weight)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String get_name(String[] neighbors) {
		int weight = calculate_weight(neighbors);
		
		for (CaseStatement c_statement:case_statements) {
			String s = c_statement.get_object_name(weight);
			if (s != null) {
				return s;
			}
		}
		
		return null;
	}

}
