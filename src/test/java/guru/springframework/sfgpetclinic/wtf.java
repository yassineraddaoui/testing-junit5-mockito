package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class wtf {

    @Test
    void testMock() {
        Map mapMock = mock(Map.class);

        assertThat(mapMock.size()).isEqualTo(0);

    }
}

class wtf2 {

    @Mock
    Map<String, Object> mapMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMockMap() {
        mapMock.put("key", "value");
    }
}

@ExtendWith(MockitoExtension.class)
class wtf3 {
    @Mock
    Map<String, Object> mapMock;

    @Test
    void testMockMap() {
        mapMock.put("key", "value");
    }
}