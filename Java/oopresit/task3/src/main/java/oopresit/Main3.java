package oopresit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

        try (FileOutputStream output = new FileOutputStream("person.ser")) {
            request.writeTo(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream input = new FileInputStream("person.ser")) {
            TestProto.SearchRequest derequest = TestProto.SearchRequest.parseFrom(input);
            System.out.println(derequest.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }
}

// java -cp task3\target\classes oopresit.Main3