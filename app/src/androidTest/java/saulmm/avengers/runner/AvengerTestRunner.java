package saulmm.avengers.runner;

import android.app.Application;
import android.content.Context;
import android.support.test.internal.util.AndroidRunnerParams;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import org.junit.runners.model.InitializationError;

import saulmm.avengers.tests.TestAvengersApplication;

/**
 * Created by saulmm on 04/01/16.
 */
public class AvengerTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String newApplicationClassName = TestAvengersApplication.class.getCanonicalName();
        return super.newApplication(cl, newApplicationClassName, context);
    }
}
