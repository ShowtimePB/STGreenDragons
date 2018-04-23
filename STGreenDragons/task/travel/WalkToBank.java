package ShowtimeScripts.dead.STGreenDragons.task.travel;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;


public class WalkToBank extends Task{
	public WalkToBank(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.data = data;
	}
	
	private DataContainer data;
	
	Tile bottomLeftGreenDragon = new Tile(3134, 3690);
	Tile topRightGreenDragon = new Tile(3168, 3717);
	Area greenDragonArea = new Area(bottomLeftGreenDragon, topRightGreenDragon);
	
	@Override
	public boolean activate(){
		if(greenDragonArea.contains(ctx.players.local().tile()) && ctx.inventory.select().id(data.getFoodID()).count() < 1)
			return true;
		return false;
	}
	
	@Override
	public void execute(){
		
		STGreenDragons.status = "Traveling to Bank";
		System.out.println("Trying to use glory");
		ctx.game.tab(Game.Tab.EQUIPMENT);
		ctx.equipment.itemAt(Equipment.Slot.NECK).interact("Edgeville");
		Condition.wait(() -> !greenDragonArea.contains(ctx.players.local().tile()), 25, 100);
		ctx.game.tab(Game.Tab.INVENTORY);
		
	}
}
