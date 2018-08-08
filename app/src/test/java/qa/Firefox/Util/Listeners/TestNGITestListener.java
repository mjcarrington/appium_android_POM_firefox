package qa.Firefox.Util.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import qa.Firefox.Util.TestSettings;


public class TestNGITestListener extends TestSettings implements ITestListener {

        @Override
        public synchronized void onStart(ITestContext context) { }

        @Override
        public synchronized void onFinish(ITestContext context) { }

        @Override
        public synchronized void onTestStart(ITestResult result) {
            System.out.println("Test Started: " + result.getMethod().getMethodName());
        }

        @Override
        public synchronized void onTestSuccess(ITestResult result) {
            System.out.println("Test Passed: " + result.getMethod().getMethodName());
        }

        @Override
        public synchronized void onTestFailure(ITestResult result) {
            System.out.println("Test Failed: " + result.getMethod().getMethodName());
            result.getThrowable();
        }

        @Override
        public synchronized void onTestSkipped(ITestResult result) {
            System.out.println("Test Skipped: " + result.getMethod().getMethodName());
            result.getThrowable();
        }

        @Override
        public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

        }

    }