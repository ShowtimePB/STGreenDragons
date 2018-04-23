package ShowtimeScripts.dead.STGreenDragons.task.deathrun;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import ShowtimeScripts.dead.STGreenDragons.misc.Walker;
import org.powerbot.script.Area;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class WalkToLumbyBank extends Task{

	public WalkToLumbyBank(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.setData(data);
	}

	private DataContainer data;

	Tile topRightLumbridge = new Tile(3226, 3231, 0);
	Tile bottomLeftLumbridge = new Tile(3202, 3205, 0);
	Area lumbridgeArea = new Area(topRightLumbridge, bottomLeftLumbridge);

	public static final Tile[] pathToBank = {new Tile(3219, 3219, 0), new Tile(3215, 3222, 0), new Tile(3214, 3227, 0), new Tile(3209, 3227, 0), new Tile(3206, 3229, 1), new Tile(3206, 3229, 2)};


	@Override
	public boolean activate(){
		if((lumbridgeArea.contains(ctx.players.local().tile()) || ctx.players.local().tile().floor() == 1) && ctx.players.local().tile().floor() != 2)
			return true;
		return false;
	}

	@Override
	public void execute(){

		STGreenDragons.status = "Died :( traveling to bank to get items";

		if(!ctx.movement.running() && ctx.client().getRunPercentage() > Random.nextInt(25, 50)){
			ctx.widgets.component(160, 27).click();
		}

		Walker walker = new Walker(ctx);
		walker.walkPath(pathToBank);
	}

	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}
}
