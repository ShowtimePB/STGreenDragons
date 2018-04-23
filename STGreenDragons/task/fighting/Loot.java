package ShowtimeScripts.dead.STGreenDragons.task.fighting;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

public class Loot extends Task{


	public Loot(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.setData(data);
	}

	private DataContainer data;

	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}

	@Override
	public boolean activate(){
		return ctx.groundItems.select().within(12).id(data.getLootablesListID()).poll().valid();
	}

	@Override
	public void execute(){

		GroundItem loot = ctx.groundItems.select().nearest().id(data.getLootablesListID()).poll();

		if(ctx.inventory.select().size() == 28){
			ctx.inventory.drop(ctx.inventory.select().id(data.getFoodID()).poll(), true);
		}

		//Pick ups loot
		if(loot.inViewport()){

			STGreenDragons.status = "Picking up loot...";

			int startingInv = ctx.inventory.select().size();
			loot.interact("Take", loot.name());
			Condition.wait(() -> startingInv != ctx.inventory.select().size(), 30, 45);

		}else{

			STGreenDragons.status = "Turning to loot...";

			if(loot.tile().distanceTo(ctx.players.local().tile()) < 6 && !loot.inViewport()){
				ctx.camera.turnTo(loot);
				Condition.wait(() -> loot.inViewport(), 30, 35);
			}else{

				STGreenDragons.status = "Walking to loot...";

				//Walking towards the tile if it's too far away
				int x = loot.tile().x() + Random.nextInt(-2, 2);
				int y = loot.tile().y() + Random.nextInt(-2, 2);
				Tile tempTile = new Tile(x, y);
				ctx.movement.step(tempTile);
				Condition.wait(() -> !ctx.players.local().inMotion() || !loot.valid() || ctx.players.local().interacting().valid() || loot.tile().distanceTo(ctx.players.local()) < 4 || loot.inViewport());
			}
		}

		//Activates run
		if(!ctx.movement.running() && ctx.client().getRunPercentage() > Random.nextInt(25, 50)){
			ctx.widgets.component(160, 27).click();
		}
	}
}
