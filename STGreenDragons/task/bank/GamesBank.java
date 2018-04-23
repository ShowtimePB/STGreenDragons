package ShowtimeScripts.dead.STGreenDragons.task.bank;

import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class GamesBank extends Task{


	public GamesBank(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.data = data;
	}


	Tile upperLeftTile = new Tile(1637, 3947);
	Tile bottomRightTile = new Tile(1642, 3941);
	Area bankAreaWinter = new Area(upperLeftTile, bottomRightTile);

	@Override
	public boolean activate(){
		if(bankAreaWinter.contains(ctx.players.local().tile())) return true;
		if(bankAreaWinter.contains(ctx.players.local().tile()) && ctx.inventory.select().id(data.getFoodID()).count() < data.getStartingFoodAmount())
			return true;
		return false;
	}

	@Override
	public void execute(){

	}


	private DataContainer data;

	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}
}
