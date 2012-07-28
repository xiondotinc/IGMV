package main.com.igmv.search;

public interface StepCostFunction {
	Double calculateStepCost(Object fromState, Object toState, String action);
}