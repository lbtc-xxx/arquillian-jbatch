package myjob;

import javax.batch.api.AbstractBatchlet;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named
@Dependent
public class MyBatchlet extends AbstractBatchlet {

    @Override
    public String process() throws Exception {
        System.out.println("Hello Arquillian and JBatch");
        return null;
    }
}
