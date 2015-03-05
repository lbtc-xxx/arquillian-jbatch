package myjob;

import org.jberet.runtime.JobExecutionImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import java.util.Properties;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MyJobIT {

    @Deployment
    public static Archive<?> createDeploymentPackage() {
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsResource("META-INF/batch-jobs/myjob.xml")
                .addClass(MyBatchlet.class);
        return webArchive;
    }

    @Test
    public void test() throws Exception {
        final JobOperator jobOperator = BatchRuntime.getJobOperator();
        final long executionId = jobOperator.start("myjob", new Properties());
        final JobExecutionImpl jobExecution = (JobExecutionImpl) jobOperator.getJobExecution(executionId);
        jobExecution.awaitTermination(0, null);
        assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
    }
}
