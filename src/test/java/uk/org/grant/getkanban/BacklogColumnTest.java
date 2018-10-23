package uk.org.grant.getkanban;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BacklogColumnTest {
    @Test
    public void testPull() {
        Card card = new Card(Card.Size.HIGH, 0, 0, 0, new SubscriberProfile(new int[] {}));
        Column backlog = new BacklogColumn();

        backlog.addCard(card);
        Optional<Card> actual = backlog.pullCard();

        assertThat(actual.get(), is(card));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAllocatingDiceIsUnsupported() {
        Column backlog = new BacklogColumn();
        backlog.allocateDice();
    }
}
