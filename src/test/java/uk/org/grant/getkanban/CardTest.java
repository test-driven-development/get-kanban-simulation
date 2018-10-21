package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CardTest {
    @Test
    public void testSize() {
        Card card = createCard();
        assertThat(card.getSize(), is(Card.Size.SMALL));
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetCycleTimeForUnstartedCard() {
        Card card = createCard();
        card.getCycleTime();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetCycleTimeForUnfinishedCard() {
        Card card = createCard();
        card.setStartDay(1);
        card.getCycleTime();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotSetFinishBeforeSettingStart() {
        Card card = createCard();
        card.setFinishDay(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testFinishCannotBeBeforeStart() {
        Card card = createCard();
        card.setStartDay(3);
        card.setFinishDay(1);
    }

    @Test
    public void testCycleTimeIsEndMinusStart() {
        Card card = createCard();
        card.setStartDay(1);
        card.setFinishDay(3);
        assertThat(2, is(card.getCycleTime()));
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetSubscribersForUnstartedCard() {
        Card card = createCard();
        card.getSubscribers();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetSubscribersForUnfinishedCard() {
        Card card = createCard();
        card.setStartDay(1);
        card.getSubscribers();
    }

    @Test
    public void testSubscribersDecreaseWithLongerCycleTimes() {
        Card firstCard = createCard();
        firstCard.setStartDay(1);
        firstCard.setFinishDay(3);
        assertThat(10, is(firstCard.getSubscribers()));

        Card secondCard = createCard();
        secondCard.setStartDay(1);
        secondCard.setFinishDay(2);
        assertThat(20, is(secondCard.getSubscribers()));
    }

    private Card createCard() {
        return new Card(Card.Size.SMALL, new SubscriberProfile(new int[]{30, 20, 10}));
    }
}