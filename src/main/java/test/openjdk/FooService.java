package test.openjdk;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FooService {

    @WebMethod
    String foo(MyBenchmark.Foo foo);
}
