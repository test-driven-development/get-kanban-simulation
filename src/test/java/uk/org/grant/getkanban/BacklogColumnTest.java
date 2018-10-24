package uk.org.grant.getkanban;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BacklogColumnTest {
    @Test
    public void testPull() {
        Card card = CardFactory.getCard("S1");
        Column backlog = new BacklogColumn();

        backlog.addCard(card);
        Optional<Card> actual = backlog.pull();

        assertThat(actual.get(), is(card));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAllocatingDiceIsUnsupported() {
        Column backlog = new BacklogColumn();
        backlog.allocateDice();
    }
}
