/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package test.openjdk;

import org.openjdk.jmh.annotations.Benchmark;
import test.openjdk.client.Foo;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.Serializable;

public class MyBenchmark {

    @Benchmark
    public void testMethod() throws Exception {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.

        try (AutoCloseable ignore = Endpoint.publish("http://localhost:8888/", new FooServiceImpl())::stop) {
            test.openjdk.client.FooService client = new test.openjdk.client.FooServiceImplService().getFooServiceImplPort();

            test.openjdk.client.Foo foo = new test.openjdk.client.Foo();
            foo.setA("Aaaaaaaaabbbbbbbbbbbccccccccccddddddd");

            for (int i = 1; i <= 10_000; i++) {
                String result = client.foo(foo);
                if (i % 1000 == 0) System.out.println(i + " processed");
            }
        }
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/", new FooServiceImpl());
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
