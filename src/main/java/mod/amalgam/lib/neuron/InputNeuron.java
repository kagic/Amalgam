package mod.amalgam.lib.neuron;

import mod.amalgam.lib.Neuron;

public class InputNeuron extends Neuron {
	public void input(float output) {
        this.output = output;
    }
    public InputNeuron(int i) {
        super(i, true);
    }
    public InputNeuron() {
        super();
    }
}