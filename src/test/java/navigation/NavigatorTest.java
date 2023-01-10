package navigation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NavigatorTest {

    @Test
    public void prevTest() {
        Navigator navigator = new Navigator();

        Page page = new Page(0, Page.Type.PLAYLIST);
        navigator.setCurrent(page);
        navigator.save();

        assertEquals(true, navigator.hasPrev());
    }

}