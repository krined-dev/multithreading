import MyLinkedList.MyLinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMyLinkedList {
    MyLinkedList list;
    String testString = "This is a test";
    @BeforeEach
    void beforeEach() {
        list = new MyLinkedList();
        list.addFirst(testString);
        list.add("Second");
        list.add("Third");
        list.add("fourth");
        list.add("fifth");
        list.add("fourth");
    }

    @Test
    void canFindIndex(){
        Assertions.assertEquals(3, list.indexOf("fourth"));
    }

    @Test
    void setNodeAtIndexTest() {
        list.set(1, "notSecond");
        Assertions.assertEquals("notSecond", list.get(1));
    }
    @Test
    void canFindLastIndex() {
        Assertions.assertEquals(5, list.lastIndexOf("fourth"));
    }

    @Test
    void putAndGetFirst(){
        String firstString = (String) list.getFirst();
        Assertions.assertEquals(testString, firstString);
        Assertions.assertEquals(testString, list.get(0));
    }

    @Test
    void getSecondElement(){
        Assertions.assertEquals(list.get(1), "Second");
    }

}
