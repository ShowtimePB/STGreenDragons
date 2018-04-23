package ShowtimeScripts.dead.STGreenDragons.task.bank;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class DeathBank extends Task{

	public DeathBank(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.setData(data);
	}

	Tile topLeftLumbridgeBank = new Tile(3206, 3224, 2);
	Tile bottomRightLumbridgeBank = new Tile(3211, 3214, 2);
	Area lumbridgeBankArea = new Area(topLeftLumbridgeBank, bottomRightLumbridgeBank);

	private DataContainer data;

	@Override
	public boolean activate(){
		if(ctx.players.local().tile().floor() == 2 && (!data.correctBagEquip(ctx) && !data.correctEquip(ctx, data)))
			return true;
		return false;
	}

	@Override
	public void execute(){

		STGreenDragons.status = "Getting Items Back";

		if(!lumbridgeBankArea.contains(ctx.players.local().tile())){
			System.out.println("Trying to get to bank");

			Tile tile = new Tile(3209, 3219);
			ctx.movement.step(tile);
			Condition.wait(() -> lumbridgeBankArea.contains(ctx.players.local().tile()) && !ctx.players.local().inMotion());
		}

		if(ctx.bank.opened()){

			ctx.bank.depositAllExcept(data.getItemIDArray());

			if(!data.correctBagEquip(ctx)){

				if(data.hasGlory(ctx) && (!data.hasGloryEquipped(ctx, data) && !data.hasGloryInventory(ctx, data))){

					System.out.println("In glory area");
					int glory = data.gloryID(ctx, data);
					ctx.bank.withdraw(glory, 1);
					Condition.wait(() -> ctx.inventory.select().id(glory).count() > 0);

				}else if(!data.hasGlory(ctx)){
					ctx.controller.stop();
				}

				for(int i : data.getItemIDArray()){
					System.out.println("Getting item " + i);
					if(ctx.bank.select().id(i).count(true) > 0 && (ctx.inventory.select().id(i).count() == 0)){

						ctx.bank.withdraw(i, 1);
						Condition.wait(() -> ctx.inventory.select().id(i).count() > 0);

					}else if(i != -1 && ctx.inventory.select().id(i).count() == 0){
						System.out.println("Killing because no item " + i);
						ctx.controller.stop();
					}
				}
			}


		}else{
			if(ctx.bank.inViewport()){
				ctx.bank.open();
			}else{
				ctx.camera.turnTo(ctx.bank.nearest());
			}
		}
	}

	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}
}
