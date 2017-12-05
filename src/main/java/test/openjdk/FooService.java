package test.openjdk;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FooService {

    @WebMethod
    String foo(MyBenchmark.Foo foo);
}
