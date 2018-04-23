package ShowtimeScripts.dead.STGreenDragons.task;

import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import ShowtimeScripts.dead.STGreenDragons.task.travel.WorldHop;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;

public class StrangerDanger extends Task{

	public StrangerDanger(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.data = data;
	}


	Tile bottomLeftGreenDragon = new Tile(3134, 3690);
	Tile topRightGreenDragon = new Tile(3168, 3717);
	Area greenDragonArea = new Area(bottomLeftGreenDragon, topRightGreenDragon);

	private DataContainer data;

	@Override
	public boolean activate(){
		if(ctx.players.select().within(24).select(player -> player != ctx.players.local() && player.interacting().equals(ctx.players.local())).poll().valid())
			return true;
		return false;
	}

	@Override
	public void execute(){
		System.out.println("Stranger Danger");

		Condition.sleep(Random.nextInt(25, 200));
		System.out.println("Trying to use glory");
		ctx.game.tab(Game.Tab.EQUIPMENT);
		ctx.equipment.itemAt(Equipment.Slot.NECK).interact("Edgeville");
		Condition.wait(() -> !greenDragonArea.contains(ctx.players.local().tile()), 50, 40);
		ctx.game.tab(Game.Tab.INVENTORY);

		if(!greenDragonArea.contains(ctx.players.local().tile())){
			System.out.println("Trying to hop");
			WorldHop wh = new WorldHop(ctx, data);
			wh.execute();
		}


	}

}
