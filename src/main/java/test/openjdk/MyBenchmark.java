package test.openjdk;

import org.openjdk.jmh.annotations.*;

import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;
import java.io.Serializable;

public class MyBenchmark {

    @State(Scope.Thread)
    public static class MyState {
        Endpoint endpoint;
        private test.openjdk.client.FooService client;
        private test.openjdk.client.Foo foo;

        @Setup(Level.Trial)
        public void doSetup() {
            endpoint = Endpoint.publish("http://localhost:8888/", new FooServiceImpl());
            System.out.println("Endpoint started");
            client = new test.openjdk.client.FooServiceImplService().getFooServiceImplPort();
            foo = new test.openjdk.client.Foo();
            foo.setA("Aaaaaaaaabbbbbbbbbbbccccccccccddddddd");
        }

        @TearDown(Level.Trial)
        public void doTearDown() {
            endpoint.stop();
            System.out.println("Endpoint stopped");
        }
    }

    @Benchmark
    public void testMethod(MyState state) throws Exception {
        String result = state.client.foo(state.foo);
    }

    public static class Foo implements Serializable {

        private String a;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }

    @WebService(endpointInterface = "test.openjdk.FooService")
    public static class FooServiceImpl implements FooService {

        @Override
        public String foo(Foo foo) {
            return "Okay okay okay okay okay okay";
        }
    }

}
