
import annotation.RpcServer;
import org.junit.Test;
import org.reflections.Reflections;
import java.util.Set;

public class testRegister {

    @Test
    public void testAnotations()
    {
        String packageName = "provider";
        Reflections reflections = new Reflections(packageName);

        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(RpcServer.class);
        System.out.println(annotatedClasses);
    }

}
