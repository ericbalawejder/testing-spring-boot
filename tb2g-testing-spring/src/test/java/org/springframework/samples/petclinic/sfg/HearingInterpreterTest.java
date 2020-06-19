package org.springframework.samples.petclinic.sfg;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {BaseConfig.class, LaurelConfig.class})
public class HearingInterpreterTest {

    //@Autowired
    HearingInterpreter hearingInterpreter;

    @Before
    public void setUp() throws Exception {
        hearingInterpreter = new HearingInterpreter(new LaurelWordProducer());
    }

    @Test
    public void whatIHeard() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }
}