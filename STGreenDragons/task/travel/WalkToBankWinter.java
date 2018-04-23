package ShowtimeScripts.dead.STGreenDragons.task.travel;

import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import ShowtimeScripts.dead.STGreenDragons.misc.Walker;
import org.powerbot.script.Area;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class WalkToBankWinter extends Task{


	private Tile lowerLeft = new Tile(1622, 3934);
	private Tile upperRight = new Tile(1642, 3948);
	private Area winterArea = new Area(lowerLeft, upperRight);

	public static final Tile[] pathToBank = {new Tile(1626, 3939, 0), new Tile(1630, 3940, 0), new Tile(1634, 3941, 0), new Tile(1637, 3944, 0)};


	public WalkToBankWinter(ClientContext ctx, DataContainer data1){
		super(ctx, data1);
		this.data = data1;
	}

	@Override
	public boolean activate(){
		if(winterArea.contains(ctx.players.local().tile()) && ctx.inventory.select().id(data.getFoodID()).count() < data.getStartingFoodAmount())
			return true;
		if(winterArea.contains(ctx.players.local().tile())) return true;
		return false;
	}

	@Override
	public void execute(){

		if(!ctx.movement.running() && (ctx.client().getRunPercentage() > Random.nextInt(25, 50))){
			ctx.widgets.component(160, 27).click();
		}

		if(!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL)){
			Walker walker = new Walker(ctx);
			walker.walkPath(pathToBank);
		}
	}

	private DataContainer data;

	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}
}
