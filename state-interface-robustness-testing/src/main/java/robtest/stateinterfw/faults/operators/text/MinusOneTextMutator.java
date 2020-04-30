package robtest.stateinterfw.faults.operators.text;

import java.util.Random;

public class MinusOneTextMutator extends AbstractTextMutator {
    private static Random _random = new Random();

    public MinusOneTextMutator() {
        super("text-minus-one");
    }

    @Override
    protected String mutateInternal(String value) {
        if (value.length() == 1)
            return "";
        int i = _random.nextInt(value.length());
        String partOne = "";
        String partTwo = "";
        if (i > 0)
            partOne = value.substring(0, i);
        if (i < value.length() - 2)
            partTwo = value.substring(i + 1);
        return partOne + partTwo;
    }

    @Override
    protected boolean allowEmpty() {
        return false;
    }
}
