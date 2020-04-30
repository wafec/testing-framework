package robtest.stateinterfw.faults;

import robtest.stateinterfw.MutatorCatalog;
import robtest.stateinterfw.faults.operators.booleans.FalseBooleanMutator;
import robtest.stateinterfw.faults.operators.booleans.NegationBooleanMutator;
import robtest.stateinterfw.faults.operators.booleans.TrueBooleanMutator;
import robtest.stateinterfw.faults.operators.numeric.*;
import robtest.stateinterfw.faults.operators.text.*;

public class DefaultMutatorCatalog extends MutatorCatalog implements IDefaultMutatorCatalog {
    public DefaultMutatorCatalog() {
        this.initialize();
    }

    private void initialize() {
        addNumericMutators();
        addTextMutators();
        addBooleanMutators();
    }

    private void addNumericMutators() {
        add(new PlusOneNumericMutator());
        add(new AbsNumericMutator());
        add(new AbsMinusOneNumericMutator());
        add(new AbsPlusOneNumericMutator());
        add(new MaxNumericMutator());
        add(new MinNumericMutator());
        add(new MinusOneNumericMutator());
        add(new NaNNumericMutator());
        add(new NullNumericMutator());
        add(new OverflowNumericMutator());
        add(new PlusOneNumericMutator());
        add(new UnderflowNumericMutator());
    }

    private void addTextMutators() {
        add(new AlphanumericTextMutator());
        add(new EmptyTextMutator());
        add(new MinusOneTextMutator());
        add(new NullTextMutator());
        add(new OverflowTextMutator());
        add(new PlusOneTextMutator());
        add(new PlusPredefinedTextMutator());
        add(new PredefinedTextMutator());
    }

    private void addBooleanMutators() {
        add(new FalseBooleanMutator());
        add(new TrueBooleanMutator());
        add(new NegationBooleanMutator());
    }
}
