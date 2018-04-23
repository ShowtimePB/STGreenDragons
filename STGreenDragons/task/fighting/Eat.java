package ShowtimeScripts.dead.STGreenDragons.task.fighting;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;

public class Eat extends Task{

	public Eat(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.setData(data);
	}

	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}

	private DataContainer data;

	@Override
	public boolean activate(){
		if(ctx.inventory.select().id(data.getFoodID()).count() > 0 && ctx.combat.healthPercent() < data.getHealthPctEat())
			return true;
		return false;
	}

	@Override
	public void execute(){

		STGreenDragons.status = "Eating";

		if(!ctx.game.tab().equals(Game.Tab.INVENTORY)) ctx.game.tab(Game.Tab.INVENTORY);

		final int startingHealth = ctx.combat.health();

		if(ctx.inventory.select().id(data.getFoodID()).count() > 0){
			ctx.inventory.select().id(data.getFoodID()).poll().interact("Eat");

			Condition.wait(() -> {
				final int currentHealth = ctx.combat.health();
				return currentHealth != startingHealth;
			});
		}

	}
}
