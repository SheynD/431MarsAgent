package com.cse431.marsmen.strategy;

import eis.iilang.Action;
import com.cse431.marsmen.MarsAgent;

public interface Strategy {
	public abstract Action execute(MarsAgent agent);
}
