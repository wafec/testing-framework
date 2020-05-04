package robtest.stateinterfw.data;

import robtest.stateinterfw.IMutantTestCase;
import robtest.stateinterfw.IMutantTestCaseFactory;
import robtest.stateinterfw.ITestCase;

public class MutantTestCaseFactory implements IMutantTestCaseFactory {
    @Override
    public IMutantTestCase create(ITestCase testCase) {
        MutantTestCase mutantTestCase = new MutantTestCase();
        mutantTestCase.setOriginalTestCase((TestCase) testCase);
        return mutantTestCase;
    }
}
