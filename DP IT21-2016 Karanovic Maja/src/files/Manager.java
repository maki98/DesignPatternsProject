package files;

import java.io.File;

public class Manager implements Files {
	
	private Files strategy;
	
	public Manager(Files strategy) {
		this.strategy = strategy;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		strategy.save();
	}

}
