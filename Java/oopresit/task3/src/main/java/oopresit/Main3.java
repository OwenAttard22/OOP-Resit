package oopresit;

import oopresit.proto.TestProto;

public class Main3 {
    public static void main(String[] args) {
        System.out.println("TASK 3!!!!!!!!!!");
        // CLI3.InitMenu();
        TestProto.SearchRequest.Builder builder = TestProto.SearchRequest.newBuilder();
        TestProto.SearchRequest request = builder
        .setQuery("test")
        .setPageNumber(1)
        .setResultPerPage(10)
        .build();
        System.out.println(request);
    }
}

// java -cp task3\target\classes oopresit.Main3