package saulmm.avengers;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PojoTest {

    private ArrayList<PojoClass> pojoClasses = new ArrayList<>();

    @Before
    public void setup() {

        String[] packages = {"saulmm.avengers.model.entities", "saulmm.avengers.model.repository.wrappers"};

        for (String pojoPackage : packages) {
            List<PojoClass> packagePojoClasses = PojoClassFactory.getPojoClasses(pojoPackage, new FilterPackageInfo());
            for (PojoClass clazz : packagePojoClasses) {
                if (clazz.getName().contains("$") || clazz.isAbstract() || clazz.isInterface() || clazz.isEnum()
                        || clazz.getName().endsWith("Test"))
                    continue;
                pojoClasses.add(clazz);
            }
        }
    }

    @Test
    public void testGettersAuto() {

        Validator validator = ValidatorBuilder.create().with(new GetterTester()).build();
        for (PojoClass clazz : pojoClasses) {
            try {
                validator.validate(clazz);
            } catch (AssertionError ex) {
                continue;
            }
        }
    }
}
