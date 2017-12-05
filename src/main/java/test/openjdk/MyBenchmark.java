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

import org.openjdk.jmh.annotations.*;
import test.openjdk.client.Foo;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
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
