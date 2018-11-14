package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.SizePrioritisationStrategy;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DeployedColumnTest {
    @Test
    public void marksDeployedDayOnPull() {
        Card card = Cards.getCard("S10");
        Column backlog = new BacklogColumn();
        backlog.addCard(card);

        Column selected = new SelectedColumn(1, backlog);
        selected.doTheWork(new Context(new Board(), new Day(1)));

        Column deployed = new DeployedColumn(selected);
        deployed.doTheWork(new Context(new Board(), new Day(2)));

        assertThat(card.getDayDeployed(), is(2));
    }

    @Test
    public void canChangePriority() {
        Column deployed = new DeployedColumn(new NullColumn());
        deployed.addCard(Cards.getCard("S10"));
        deployed.addCard(Cards.getCard("S5"));

        assertThat(deployed.getCards().peek().getName(), is("S5"));

        deployed.orderBy(new BusinessValuePrioritisationStrategy());

        assertThat(deployed.getCards().peek().getName(), is("S10"));
    }
}
