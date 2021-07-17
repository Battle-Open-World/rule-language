package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utils.Switch.SwitchStatement;

class BuildSuccessfulException extends RuntimeException {}

public class RuleManager {
	
	public static Rule import_rule(String filename) {
		File f = new File(filename);
		ArrayList<SwitchStatement> switches = new ArrayList<SwitchStatement>();
		String else_data = null;
		
		try {
			BufferedReader fr = new BufferedReader(new FileReader(f));
			
			String msg;
			while ((msg = fr.readLine()) != null) {
				String[] data_without_comments = msg.split("//");
				msg = data_without_comments[0];
				
				if (msg.equals("")) {
					continue;
				}
				
				if (msg.startsWith("ELSE ")) {
					else_data = msg.replace("ELSE ", "");
					break;
				}
				if (msg.startsWith("SWITCH ")) {
					switches.add(new SwitchStatement(msg));
				} else if (msg.startsWith(" CASE ")) {
					if (switches.size() != 0) {
						switches.get(switches.size() - 1).build_case(msg);
					} else {
						System.out.println("Warning, a case should always be after a switch");
					}
				} else {
					System.out.println("Warning, couldn't found the expression "+msg);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		for (SwitchStatement swst:switches) {
			if (!swst.get_build_successful()) {
				throw new BuildSuccessfulException();
			}
		}
		
		return new Rule(else_data, switches);
	}
	
	public static void main(String[] args) {
		Rule rule = RuleManager.import_rule("../BattleOpenWorld/config/Rules/windmill.rbf");
		
		String[] neighbors = new String[] {
			"","","","Field","","","","",""	
		};
		
		System.out.println(rule.get_object_name(neighbors));
	}

}
